package com.github.pankajyogi.spring.taxibooking;

import com.github.pankajyogi.spring.taxibooking.exception.MismatchArgumentException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class UtilsTest {


    @Test
    void ensureEquals_givenBothNull() {
        Object obj1 = null, obj2 = null;
        assertDoesNotThrow(() -> Utils.ensureEquals(obj1, obj2));
        assertDoesNotThrow(() -> Utils.ensureEquals(obj1, obj2, "should not throw exception"));
    }


    @Test
    void ensureEquals_givenObj1Null_shouldThrowException() {
        Object obj1 = null, obj2 = new Object();
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2));
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2, "should throw exception"));
    }


    @Test
    void ensureEquals_givenObj2Null_shouldThrowException() {
        String obj1 = "new string", obj2 = null;
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2));
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2, "should throw exception"));
    }

    @Test
    void ensureEquals_givenNotEqualObjects_shouldThrowException() {
        Object obj1 = new Object(), obj2 = new ArrayList();
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2));
        assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2, "should throw exception"));
    }

    @Test
    void ensureEquals_givenSameObject() {
        Object obj = new Object();
        assertDoesNotThrow(() -> Utils.ensureEquals(obj, obj));
        assertDoesNotThrow(() -> Utils.ensureEquals(obj, obj, "does not matter"));
    }


    @Test
    void ensureEquals_givenEqualObjects() {
        String str1 = "A string";
        String str2 = new String(str1);
        assertNotSame(str1, str2);
        assertDoesNotThrow(() -> Utils.ensureEquals(str1, str2));
        assertDoesNotThrow(() -> Utils.ensureEquals(str1, str2, "does not matter"));
    }

    @Test
    void ensureEquals_whenThrownException_shouldHaveCustomMessage() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        String errorMessage = "Objects are not same";
        MismatchArgumentException exception = assertThrows(MismatchArgumentException.class, () -> Utils.ensureEquals(obj1, obj2, errorMessage));
        assertEquals(errorMessage, exception.getMessage());
    }
}
