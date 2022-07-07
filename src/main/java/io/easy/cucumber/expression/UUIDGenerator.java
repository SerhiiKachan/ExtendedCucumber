package io.easy.cucumber.expression;

import java.util.UUID;
import java.util.regex.Matcher;

public class UUIDGenerator extends Expression {

    @Override
    protected String regexp() {
        return "G\\{(uuid)}";
    }

    @Override
    protected String generateNextPlaceholder(Matcher matcher) {
        return UUID.randomUUID().toString();
    }
}
