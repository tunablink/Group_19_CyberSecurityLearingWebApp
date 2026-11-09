package com.example.blog_web.compete.support;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class CompeteUtcTime {

    private CompeteUtcTime() {
    }

    public static Instant startOfWeekMondayUtc(Instant now) {
        Instant day = now.truncatedTo(ChronoUnit.DAYS);
        int dow = day.atZone(java.time.ZoneOffset.UTC).getDayOfWeek().getValue();
        int daysFromMonday = (dow + 6) % 7;
        return day.minus(daysFromMonday, ChronoUnit.DAYS);
    }

    public static Instant endOfWeekSundayUtc(Instant weekStartMondayUtc) {
        return weekStartMondayUtc.plus(7, ChronoUnit.DAYS).minusMillis(1);
    }

    public static Instant startOfUtcDay(Instant now) {
        return now.truncatedTo(ChronoUnit.DAYS);
    }
}
