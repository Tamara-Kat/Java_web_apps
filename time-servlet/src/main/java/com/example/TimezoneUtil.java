package com.example;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

public class TimezoneUtil {
    private static final Set<String> ZONE_IDS = ZoneId.getAvailableZoneIds();

    public static String normalize(String timezone) {
        if (timezone == null || timezone.isBlank()) {
            return "UTC";
        }

        timezone = timezone.trim().replace(" ", "+");

        if (timezone.equals("UTC")) {
            return "UTC";
        }

        if (timezone.matches("UTC[+-]\\d{1,2}")) {
            int hours = Integer.parseInt(timezone.substring(3));
            return ZoneOffset.ofHours(hours).getId();
        }

        if (ZONE_IDS.contains(timezone)) {
            return timezone;
        }

        throw new IllegalArgumentException("Invalid timezone");
    }

    public static boolean isValid(String timezone) {
        try {
            normalize(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}