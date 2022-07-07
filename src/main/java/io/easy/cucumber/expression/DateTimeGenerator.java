package io.easy.cucumber.expression;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;

public class DateTimeGenerator extends Expression {

    @Override
    protected String regexp() {
        return "G\\{(currentDate)(\\(.+\\))( -| \\+)?( [0-9]{1,3} )?([A-Z]+)?}";
    }

    @Override
    protected String generateNextPlaceholder(Matcher matcher) {
        String datePatternRaw = matcher.group(2);
        String datePattern = datePatternRaw.substring(1, datePatternRaw.length() - 1);
        if (matcher.group(3) == null) {
            return Instant.now()
                    .atZone(ZoneId.of("UTC"))
                    .format(DateTimeFormatter.ofPattern(datePattern));
        }
        char sign = matcher.group(3).trim().toCharArray()[0];
        long amount = Long.parseLong(matcher.group(4).trim());
        if (sign == '-') {
            amount = -amount;
        }
        ChronoUnit timeUnit = ChronoUnit.valueOf(matcher.group(5));
        return Instant.now()
                .atZone(ZoneId.of("UTC"))
                .plus(amount, timeUnit)
                .format(DateTimeFormatter.ofPattern(datePattern));
    }
}
