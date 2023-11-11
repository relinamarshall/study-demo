package demo.utils.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import demo.utils.exception.RestException;

import static demo.utils.exception.ErrorCode.SERVICE_INNER_ERROR;

/**
 * AssertUtilTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/21
 */
public class AssertUtilTest {
    @Test
    public void notEmpty() {
        AssertUtil.notEmpty("123", "");

        try {
            AssertUtil.notEmpty(null, "notEmpty");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("notEmpty", e.getMessage());
        }
        try {
            AssertUtil.notEmpty(null, SERVICE_INNER_ERROR);
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR, e.getError());
        }
        try {
            AssertUtil.notEmpty(null, SERVICE_INNER_ERROR, "notEmpty");
        } catch (RestException e) {
            Assert.assertEquals("notEmpty", e.getMessage());
        }
        try {
            AssertUtil.notEmptyList(new ArrayList<>(), "notEmpty");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("notEmpty", e.getMessage());
        }
    }

    @Test
    public void notBlank() {
        AssertUtil.notBlank("123", "");

        try {
            AssertUtil.notBlank(" ", "mock");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("mock", e.getMessage());
        }
        try {
            AssertUtil.notBlank(" ", SERVICE_INNER_ERROR);
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR, e.getError());
        }
        try {
            AssertUtil.notBlank(" ", SERVICE_INNER_ERROR, "mock");
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR.getCode(), e.getError().getCode());
            Assert.assertEquals("mock", e.getMessage());
        }
    }

    @Test
    public void notNull() {
        AssertUtil.notNull(new Object(), "");

        try {
            AssertUtil.notNull(null, "mock");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("mock", e.getMessage());
        }
        try {
            AssertUtil.notNull(null, SERVICE_INNER_ERROR);
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR, e.getError());
        }
        try {
            AssertUtil.notNull(null, SERVICE_INNER_ERROR, "mock");
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR.getCode(), e.getError().getCode());
            Assert.assertEquals("mock", e.getMessage());
        }
    }

    @Test
    public void asserTrue() {
        AssertUtil.assertTrue(true, "");

        try {
            AssertUtil.assertTrue(false, "assertTrue");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("assertTrue", e.getMessage());
        }
    }

    @Test
    public void check() {
        AssertUtil.check(true, () -> new RestException(SERVICE_INNER_ERROR));

        try {
            AssertUtil.check(false, () -> new RestException(SERVICE_INNER_ERROR));
        } catch (RestException e) {
            Assert.assertEquals(SERVICE_INNER_ERROR, e.getError());
        }
    }
}
