package io.easy.cucumber.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Expression {

    public boolean replacementAvailable(String source) {
        return Pattern.compile(regexp()).matcher(source).find();
    }

    public String replaceNextPlaceholder(String source) {
        Matcher matcher = Pattern.compile(regexp()).matcher(source);
        matcher.find();
        String generatedValue = generateNextPlaceholder(matcher);
        return matcher.replaceFirst(generatedValue);
    }

    protected abstract String regexp();

    protected abstract String generateNextPlaceholder(Matcher matcher);
}
