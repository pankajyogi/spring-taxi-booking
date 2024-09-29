package com.github.pankajyogi.spring.taxibooking;

import com.github.pankajyogi.spring.taxibooking.exception.MismatchArgumentException;

import java.util.Objects;

/**
 * Utility class for common validation operations.
 */
public final class Utils {

    private Utils() {
        // avoids instantiation
    }

    /**
     * Ensures that two objects are equal. If they are not, an exception is thrown with a default message.
     *
     * @param obj1 The first object to compare.
     * @param obj2 The second object to compare.
     * @throws MismatchArgumentException if the objects are not equal.
     */
    public static void ensureEquals(Object obj1, Object obj2) {
        ensureEquals(obj1, obj2, String.format("obj1: %s is not equal to obj2: %s", obj1, obj2));
    }

    /**
     * Ensures that two objects are equal. If they are not, an exception is thrown with the specified message.
     *
     * @param obj1 The first object to compare.
     * @param obj2 The second object to compare.
     * @param message The message for the exception if the objects are not equal.
     * @throws MismatchArgumentException if the objects are not equal.
     */
    public static void ensureEquals(Object obj1, Object obj2, String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new MismatchArgumentException(message);
        }
    }

}
