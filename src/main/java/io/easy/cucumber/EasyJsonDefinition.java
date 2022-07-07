package io.easy.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.core.backend.DocStringTypeDefinition;
import io.cucumber.docstring.DocStringType;
import io.easy.cucumber.expression.Expression;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class EasyJsonDefinition implements DocStringTypeDefinition {

    private final DocStringType docStringType;
    private final Set<Expression> customExpressions;

    EasyJsonDefinition(Class<?> type) {
        this.customExpressions = registerExpressions();
        this.docStringType = new DocStringType(
                type,
                type.getSimpleName(),
                (String jsonSource) -> new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .readValue(generatePlaceholders(jsonSource), type)
        );
    }

    private String generatePlaceholders(String jsonSource) {
        for (Expression expression : customExpressions) {
            while (expression.replacementAvailable(jsonSource)) {
                jsonSource = expression.replaceNextPlaceholder(jsonSource);
            }
        }
        return jsonSource;
    }

    private <T extends Expression> Set<T> registerExpressions() {
        return new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()))
                .get(Scanners.SubTypes
                        .of(Expression.class)
                        .asClass()
                        .map(this::instantiateExpression));
    }

    @SuppressWarnings("unchecked")
    private <T extends Expression> T instantiateExpression(Class<?> expressionClass) {
        try {
            return (T) expressionClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DocStringType docStringType() {
        return docStringType;
    }

    @Override
    public boolean isDefinedAt(StackTraceElement stackTraceElement) {
        return false;
    }

    @Override
    public String getLocation() {
        return null;
    }
}
