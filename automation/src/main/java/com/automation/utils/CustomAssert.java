package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.asserts.SoftAssert;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomAssert {

    private AssertUtils assertUtils= new AssertUtils();

    protected static Logger log = LogManager.getLogger(CustomAssert.class);

    SoftAssert softAssert = new SoftAssert();

    public void assertTrue(Boolean state, String msg) {
        try {
            Assert.assertTrue(state, msg);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            Assert.fail(state + " : " + e.getMessage());
        }
    }

    public void softAssertTrue(Boolean state, String msg) {
        try {
            softAssert.assertTrue(state, msg);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            softAssert.fail(state + " : " + e.getMessage());
        }
    }

    public void assertTrue(Boolean state) {
        try {
            Assert.assertTrue(state);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            Assert.fail(state + " : " + e.getMessage());
        }
    }

    public void assertFalse(Boolean state, String msg) {
        try {
            Assert.assertFalse(state, msg);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            Assert.fail(state + " : " + e.getMessage());
        }
    }

    public void assertFalse(Boolean state) {
        try {
            Assert.assertFalse(state);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            Assert.fail(state + " : " + e.getMessage());
        }
    }

    public void assertEquals(String actual, String expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertEquals(int actual, int expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertEquals(String actual, String expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertNotEquals(String actual, String expected, String msg) {
        try {
            Assert.assertNotEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertNotEquals(String actual, String expected) {
        try {
            Assert.assertNotEquals(actual, expected);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertEqualsIgnoreCase(String actual, String expected, String msg) {
        try {
            Assert.assertEquals(actual.toLowerCase(), expected.toLowerCase(), msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertStringContains(String actual, String expectedContains, String msg) {
        try {
            Assert.assertTrue(actual.contains(expectedContains), msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertEquals(Collection<?> actual, Collection<?> expected) {
        assertEquals(((Collection) actual).toString(), ((Collection) expected).toString(), (String) null);
    }

    public void assertMapEquals(Map<String, String> actual, Map<String, String> expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertObjectMapEquals(Map<String, Object> actual, Map<String, Object> expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertHashMapEquals(Map<String, Object> actual, Map<String, Object> expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public void assertLists(List<String> listExpected, List<String> listActual, String msg) {
        String compareRes = assertUtils.compareTwoLists(listExpected, listActual);
        assertEquals(compareRes, "EQUAL", msg + compareRes);
    }

    public boolean assertEqualsSoft(String actual, String expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
            return true;
        } catch (AssertionError e) {
            log.error(e.getMessage());
            log.info("Condition not matching");
            return false;
        }
    }

    public void assertFalseSoft(Boolean state, String msg) {
        try {
            Assert.assertFalse(state, msg);
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            log.info(msg);

            //Assert.fail(state + " : " + e.getMessage());
        }
    }

    public boolean assertTrueSoft(Boolean state, String msg) {
        try {
            Assert.assertTrue(state, msg);
            return true;
        } catch (AssertionError e) {
            log.error(state + " : " + e.getMessage());
            log.info(msg);
            return false;

            //Assert.fail(state + " : " + e.getMessage());
        }
    }
}
