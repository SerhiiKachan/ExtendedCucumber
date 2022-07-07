package io.easy.cucumber.expression;

import org.apache.commons.lang.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomNumberGenerator extends Expression {
    @Override
    protected String regexp() {
        return "G\\{random\\(\\d\\)}";
    }

    @Override
    protected String generateNextPlaceholder(Matcher matcher) {
        String placeholder = matcher.group(0);
        Matcher digitsMatcher = Pattern.compile("\\d").matcher(placeholder);
        digitsMatcher.find();
        return RandomStringUtils.randomNumeric(Integer.parseInt(digitsMatcher.group(0)));
    }
}
