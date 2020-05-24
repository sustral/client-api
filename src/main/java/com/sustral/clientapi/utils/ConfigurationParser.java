package com.sustral.clientapi.utils;

/**
 * This class contains static methods that parse configuration values.
 *
 * @author Dilanka Dharmasena
 */
public class ConfigurationParser {

    private ConfigurationParser() {
        // This class should not be instantiated
    }

    /**
     * This method returns the length of time in milliseconds indicated by the string value from a configuration file.
     *
     * @param val   the string from configuration
     * @return      the long indicating a length of time in ms
     * @throws IllegalArgumentException if the given configuration value is not in a known format
     */
    public static long parseTime(String val) {

        long value = Long.parseLong(val.substring(0, val.length()-1));
        char unit = val.charAt(val.length()-1);

        switch (unit) {
            case 'W': // week
                value *= 7;
            case 'D': // day
                value *= 24;
            case 'H': // hour
                value *= 60;
            case 'M': // minute
                value *= 60;
            case 'S': // second
                value *= 1000;
                break;
            default:
                throw new IllegalArgumentException("The value passed to ConfigurationParser.parseTime contained an unsupported unit");
        }

        return value;
    }

}
