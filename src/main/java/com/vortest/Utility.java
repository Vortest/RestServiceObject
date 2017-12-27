package com.vortest;

import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class Utility {
    private static String DEFAULT_FILENAME_LENGTH = "40";
    public static final String SCENARIO_DEVICE_TAG = "@Device";
    private static final String TIME_FORMAT = "%sms";

    public static void isEmptyString(String value) throws AssertionError {
        Assert.assertTrue("The supplied value "+value+" is empty",!value.isEmpty());
    }

    public static void isNull(Object value) throws AssertionError {
        Assert.assertNotNull("The supplied value "+value+" is null", value);
    }

    public static void isEmptyOrNullString(String value) throws AssertionError {
        isNull(value);
        isEmptyString(value);
    }

    public static String formatFilename(String value) {
        ConfigReader configReader = new ConfigReader();
        int length;
        String lengthProp;

        lengthProp = configReader.getProperty("LOG_FILENAME_LENGTH", DEFAULT_FILENAME_LENGTH);
        length = Integer.parseInt(lengthProp);

        return StringUtils.abbreviate(value.replace(" ", "_"), length);
    }
//
//    /**
//     * @param configProperty - Property which is to be queried from the local config file
//     * @return - A string that represents the configuration property.
//     */
//    public static String getLocalConfigProperty(String configProperty) {
//        return new ContextObject<>().getConfig().getProperty(configProperty);
//    }
//
//    /**
//     * @param configProperty - Property which is to be queried from the local config file
//     * @param defaultProperty - Default property if none is set
//     * @return - A string that represents the configuration property.
//     */
//    public static String getLocalConfigProperty(String configProperty, String defaultProperty) {
//        return new ContextObject<>().getConfig().getProperty(configProperty, defaultProperty);
//    }

    /**
     * Populate a string template with any key value pairs in the form {{key}} where {{key}} will be replaced by value.
     * @param template the template as a string to populate with certain key value pairs.
     * @param valuesToAdd the values to add in the template given their keys in a Map.
     * @return the fully populated template with values added in place of the keys.
     */
    public static String fillInTemplate(String template, Map<String, String> valuesToAdd) {
        String output = template;

        //Add values from map to the json template
        for (String valueKey : valuesToAdd.keySet()) {
            output = output.replaceAll(String.format("\\{\\{%s\\}\\}", valueKey), valuesToAdd.get(valueKey));
        }

        return output;
    }

    /**
     * * Populate a json template with any key value pairs in the form {{key}} where {{key}} will be replaced by value.
     * @param jsonFile the template as a file to populate with certain key value pairs.
     * @param valuesToAdd the values to add in the template given their keys in a Map.
     * @return the fully populated template with values added in place of the keys.
     */
    public static String fillInJsonFileTemplate(File jsonFile, Map<String, String> valuesToAdd) {
        String jsonString;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(jsonFile));
        } catch (FileNotFoundException exc) {
            throw new RuntimeException(String.format("Json File at path: %s not found.", jsonFile.getAbsolutePath()));
        }

        //fill in template or get file as string
        try {
            if (valuesToAdd != null) {
                jsonString = fillInTemplate(IOUtils.toString(reader), valuesToAdd);
            } else {
                jsonString = IOUtils.toString(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to fill in template Json File at path: %s.", jsonFile.getAbsolutePath()));
        }

        //parse the filled in template into valid JSON format
        JsonParser parser = new JsonParser();
        jsonString = parser.parse(jsonString).getAsJsonObject().toString();

        return jsonString;
    }

    public static boolean checkOperatingSystem(String operatingSystem) {
        return System.getProperty("os.name").startsWith(operatingSystem) ? true : false;
    }

    /**
     * Return a random number with the specified length of digits.
     * @param numDigits the number of digits for the integer to return. numDigits must be 10 or less.
     * @return the random number generated.
     */
    public static int getRandomNumber(int numDigits) {
        StringBuilder min = new StringBuilder("1");
        StringBuilder max = new StringBuilder("9");
        int minNum, maxNum;

        //Max integer is 10 digits, so throw exception
        if (numDigits > 10) {
            throw new NumberFormatException("Exceeded max number of digits");
        }

        for (int digit = 1; digit < numDigits; digit++) {
            min.append("0");
            max.append("9");
        }

        minNum = Integer.parseInt(min.toString());

        //digit count at 10 will give random value between 1000000000 - Integer max value
        if (numDigits == 10) {
            maxNum = Integer.MAX_VALUE;
        }
        else {
            maxNum = Integer.parseInt(max.toString());
        }


        return ThreadLocalRandom.current().nextInt(minNum, maxNum);
    }

    /**
     * Return a random number of length 9.
     * @return the random number with digit length of 9.
     */
    public static int getRandomNumber() {
        return getRandomNumber(9);
    }

//    /**
//     * Returns a POJO as a string with the values returned as a String.
//     * @param obj the object to return as a string.
//     * @return a String that contains the values of the POJO passed in as a parameter.
//     */
//    public static String pojoToString(Object obj) {
//        List<Object> listWrapper = new ArrayList<>();
//        listWrapper.add(obj);
//
//        return pojoArrayToString(listWrapper);
//    }

//    /**
//     * Returns a String that represents the values contained in the list of POJOs in a HTML table.
//     * @param objList the list of pojos.
//     * @return the string representation of the list of pojos and their values in a HTML table.
//     */
//    public static String pojoArrayToString(List<Object> objList) {
//        return HTMLTableBuilder.makeHTMLTableString(objList);
//    }

    /**
     * Get the difference of two dates where it is done by end time - start time.
     * @param startTime the time that is before the endtime.
     * @param endTime the greater of the two times.
     * @return the difference between the two times in the format
     */
    public static String getTimeDifference(Instant startTime, Instant endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return String.format(TIME_FORMAT, duration.toMillis());
    }
}
