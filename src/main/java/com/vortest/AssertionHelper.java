package com.vortest;

import org.junit.Assert;

import java.util.ArrayList;

/**
 * Acts as a wrapper for the JUnit Assertions in order to log the errors and
 * successes.
 *
 * Created by csears on 2/28/17.
 */
public class AssertionHelper {
    private static String ERROR_MESSAGE = "Assertion Failure: %s\nExpected: %s\nBut received: %s";
    private static String NOT_EQUAL_ERROR_MESSAGE = "Assertion Failure: %s\nExpected to be not equal but were equal: %s";
    private static String SUCCESS_MESSAGE = "Successful Assertion";

    /**
     * Asserts that given value is true and throws AssertionError if this is not the case.
     * @param isTrue value of expression expected to be true.
     */
    public static void assertTrue(boolean isTrue) {
        assertTrue("", isTrue);
    }

    /**
     * Asserts that given value is false and throws AssertionError if this is not the case.
     * @param isFalse value of expression expected to be false.
     */
    public static void assertFalse(boolean isFalse) {
        assertFalse("", isFalse);
    }

    /**
     * Asserts that the two values are the same and throws AssertionError if this is not the case.
     * @param expected the expected value for the assertion.
     * @param actual the actual value that is being assertion against the expected.
     * @param <E> the type of the values for the assertion.
     */
    public static <E> void assertEquals(E expected, E actual) {
        assertEquals("", expected, actual);
    }

    /**
     * Asserts that given value is true and throws AssertionError if this is not the case.
     * @param errorMessage the message to be logged in the case of failure.
     * @param isTrue value of expression expected to be true.
     */
    public static void assertTrue(String errorMessage, boolean isTrue) {
        assertEquals(errorMessage, true, isTrue);
    }

    /**
     * Asserts that given value is false and throws AssertionError if this is not the case.
     * @param errorMessage the message to be logged in the case of failure.
     * @param isFalse value of expression expected to be false.
     */
    public static void assertFalse(String errorMessage, boolean isFalse) {
        assertEquals(errorMessage, false, isFalse);
    }

    /**
     * Asserts that the two values are the same and throws AssertionError if this is not the case.
     * @param errorMessage the message to be logged in the case of failure.
     * @param expected the expected value for the assertion.
     * @param actual the actual value that is being assertion against the expected.
     * @param <E> the type of the values for the assertion.
     */
    public static <E> void assertEquals(String errorMessage, E expected, E actual) {
        try {
            Assert.assertEquals(errorMessage, expected, actual);
        } catch (AssertionError exc) {
            //Assertion failure
            //System.out.println(String.format(ERROR_MESSAGE, errorMessage, expected, actual));
            throw exc;
        }

        //Assertion Success
        System.out.println(SUCCESS_MESSAGE);
    }

    /**
     * Asserts that the two values are not the same and throws AssertionError if this is not the case.
     * @param notExpected the value that is not expected for the assertion.
     * @param actual the actual value that is being asserted against.
     * @param <E> the type of the values for the assertion.
     */
    public static <E> void assertNotEquals(E notExpected, E actual) {
        assertNotEquals("", notExpected, actual);
    }

    /**
     * Asserts that the two values are not the same and throws AssertionError if this is not the case.
     * @param errorMessage the message to be logged in the case of failure.
     * @param notExpected the value that is not expected for the assertion.
     * @param actual the actual value that is being asserted against.
     * @param <E> the type of the values for the assertion.
     */
    public static <E> void assertNotEquals(String errorMessage, E notExpected, E actual) {
        try {
            Assert.assertNotEquals(errorMessage, notExpected, actual);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format(NOT_EQUAL_ERROR_MESSAGE, errorMessage, notExpected));
            throw exc;
        }

        //Assertion Success
        System.out.println(SUCCESS_MESSAGE);
    }

    /**
     * Asserts that the value is object provided is not null.
     * @param notNull the value to be tested that it is not null.
     */
    public static void assertNotNull(Object notNull) {
        try {
            Assert.assertNotNull(notNull);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: Object was null, but expected to contain a valid value."));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: Object %s was not null as expected.", notNull));
    }

    /**
     * Asserts that a string is not null and not empty.
     * @param notNullOrEmpty the value to be tested that it is not null or empty.
     */
    public static void assertStringNotNullOrEmpty(String notNullOrEmpty) {
        try {
            Assert.assertNotNull(notNullOrEmpty);
            Assert.assertNotEquals("", notNullOrEmpty);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: String was null or empty, but expected to contain a valid value."));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: String %s was not null or empty.", notNullOrEmpty));
    }

    /**
     * Asserts that a string is empty.
     * @param emptyStr the value to be tested that it is empty.
     */
    public static void assertStringIsEmpty(String emptyStr) {
        try {
            Assert.assertNotNull(emptyStr);
            Assert.assertTrue(emptyStr.isEmpty());
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: String: %s was not empty, but expected to be empty.", emptyStr));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: String was empty as expected."));
    }

    /**
     * Asserts that a string contains a valid substring.
     * @param value the value to be tested that it contains the substring.
     * @param substring the substring to check that it is in the full string.
     */
    public static void assertContainsString(String value, String substring) {
        try {
            Assert.assertNotNull(value);
            Assert.assertNotNull(substring);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: Either string: %s or substring: %s were null.", value, substring));
            throw exc;
        }

        try {
            Assert.assertTrue(value.contains(substring));
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: String: %s did not contain: %s.", value, substring));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: String: %s contained: %s.", value, substring));
    }

    /**
     * Asserts that a given array has at least one element and is not null.
     * @param array the array to check that it contains at least one value.
     * @param <E> the type of the array.
     */
    public static <E> void assertArrayLengthGreaterThanZero(E[] array) {
        try {
            Assert.assertNotNull(array);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: array was null."));
            throw exc;
        }

        try {
            Assert.assertTrue(array.length > 0);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: Array of type: %s did not contain at least one element.", array.getClass().getName()));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: Array of type: %s contained at least one item.", array.getClass().getName()));
    }

    /**
     * Asserts that a two strings are equal and not null.
     * @param expected the expected value to be asserted for.
     * @param actual the actual value that should be equal to the expected.
     */
    public static void assertStringsEqual(String expected, String actual) {
        try {
            Assert.assertNotNull(expected);
            Assert.assertNotNull(actual);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: string expected: <%s> or actual: <%s> is null.", expected, actual));
            throw exc;
        }

        try {
            Assert.assertEquals(expected, actual);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: two strings are not equal (expected: <%s> and actual: <%s>.)", expected, actual));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: The two strings are equal (expected: <%s> and actual: <%s>).", expected, actual));
    }

    /**
     * Asserts that the array list size is greater than a given count.
     * @param array the array list to check the size.
     * @param count the count that the array list size should be greater than.
     */
    public static void assertArrayListGreaterThanCount(ArrayList array, int count) {
        try {
            Assert.assertNotNull(array);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: array list is null"));
            throw exc;
        }

        try {
            Assert.assertTrue(array.size() > count);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: the size of the array: <%s> is not greater than the expected count: <%s>.)",
                    array.size(), count));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: The size of the array and expected size are as expected (expected: <%s> and actual: <%s>).",
                count, array.size()));
    }

    /**
     * Asserts that the array list size is equal to the given count.
     * @param array the array list to check the size.
     * @param count the count that the array list size should be equal to.
     */
    public static void assertArrayListEqualToCount(ArrayList array, int count) {
        try {
            Assert.assertNotNull(array);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: array list is null"));
            throw exc;
        }

        try {
            Assert.assertTrue(array.size() == count);
        } catch (AssertionError exc) {
            //Assertion failure
            System.out.println(String.format("Failure: the size of the array: <%s> is not equal to the expected count: <%s>.)",
                    array.size(), count));
            throw exc;
        }

        //Assertion Success
        System.out.println(String.format("Success: The size of the array and expected size are equal (expected: <%s> and actual: <%s>).",
                count, array.size()));
    }
}
