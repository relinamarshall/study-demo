package demo.utils.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * ArgUtilTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/21
 */
public class ArgUtilTest {
    @Test
    public void ifBlankGet() {
        Assert.assertEquals("a", ArgUtil.ifBlankGet("", "a"));
        Assert.assertEquals("a", ArgUtil.ifBlankGet(null, "a"));
        Assert.assertEquals("a", ArgUtil.ifBlankGet("   ", "a"));
        Assert.assertEquals("x", ArgUtil.ifBlankGet("x", "a"));

        Assert.assertEquals("a", ArgUtil.ifBlankGet(" ", () -> "a"));
        Assert.assertEquals("x", ArgUtil.ifBlankGet("x", () -> "a"));
    }

    @Test
    public void ifEmptyGet() {
        Assert.assertEquals("a", ArgUtil.ifEmptyGet("", "a"));
        Assert.assertEquals("a", ArgUtil.ifEmptyGet(null, "a"));
        Assert.assertEquals(" ", ArgUtil.ifEmptyGet(" ", "a"));
        Assert.assertEquals("x", ArgUtil.ifEmptyGet("x", "a"));

        Assert.assertEquals(" ", ArgUtil.ifEmptyGet(" ", () -> "a"));
        Assert.assertEquals("x", ArgUtil.ifEmptyGet("x", () -> "a"));
    }

    @Test
    public void ifNullGet() {
        Assert.assertEquals("", ArgUtil.ifNullGet("", "a"));
        Assert.assertEquals("a", ArgUtil.ifNullGet(null, "a"));
        Assert.assertEquals(" ", ArgUtil.ifNullGet(" ", "a"));

        Assert.assertEquals((Long) 1L, ArgUtil.ifNullGet(1L, 0L));

        Assert.assertEquals(" ", ArgUtil.ifNullGet(" ", () -> "a"));
        Assert.assertEquals("a", ArgUtil.ifNullGet(null, () -> "a"));
    }

    @Test
    public void ifTrueDo() {
        int[] result = new int[]{0};
        ArgUtil.ifTrueDo(false, "a", arg -> result[0] = 1);
        Assert.assertEquals(0, result[0]);

        ArgUtil.ifTrueDo(true, 0, arg -> result[arg] = 1);
        Assert.assertEquals(1, result[0]);

        ArgUtil.ifTrueDo(true, () -> result[0] = 3);
        Assert.assertEquals(3, result[0]);

        ArgUtil.ifTrueDo(false, () -> result[0] = 1);
        Assert.assertEquals(3, result[0]);
    }

    @Test
    public void ifElse() {
        Assert.assertEquals("a", ArgUtil.ifElse(true, () -> "a", () -> "b"));
        Assert.assertEquals("b", ArgUtil.ifElse(false, () -> "a", () -> "b"));
    }

    @Test
    public void ifBlankDo() {
        int[] result = new int[]{0};
        ArgUtil.ifBlankDo("x",  arg -> result[0] = 1);
        Assert.assertEquals(0, result[0]);

        ArgUtil.ifBlankDo(" ",  arg -> result[0] = 1);
        Assert.assertEquals(1, result[0]);
    }

    @Test
    public void ifEmptyDo() {
        int[] result = new int[]{0};
        ArgUtil.ifEmptyDo("x",  arg -> result[0] = 1);
        Assert.assertEquals(0, result[0]);

        ArgUtil.ifEmptyDo("",  arg -> result[0] = 1);
        Assert.assertEquals(1, result[0]);
    }

    @Test
    public void ifNullDo() {
        int[] result = new int[]{0};
        ArgUtil.ifNullDo("x",  arg -> result[0] = 1);
        Assert.assertEquals(0, result[0]);

        ArgUtil.ifNullDo(null,  arg -> result[0] = 1);
        Assert.assertEquals(1, result[0]);
    }

    @Test
    public void ifNotNullDo() {
        int[] result = new int[]{0};
        ArgUtil.ifNotNullDo("x",  arg -> result[0] = 1);
        Assert.assertEquals(1, result[0]);

        ArgUtil.ifNotNullDo(null, arg -> result[0] = 2);
        Assert.assertEquals(1, result[0]);
    }
}
