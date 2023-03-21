package demo.swagger.api.common;

/**
 * ParamType
 * <p>
 * 方便在 @ApiImplicitParam 的 paramType 属性使用
 *
 * @author Wenzhou
 * @since 2023/3/21 11:18
 */
public final class ParamType {
    /**
     * QUERY
     */
    public static final String QUERY = "query";
    /**
     * HEADER
     */
    public static final String HEADER = "header";
    /**
     * PATH
     */
    public static final String PATH = "path";
    /**
     * BODY
     */
    public static final String BODY = "body";
    /**
     * FORM
     */
    public static final String FORM = "form";
}
