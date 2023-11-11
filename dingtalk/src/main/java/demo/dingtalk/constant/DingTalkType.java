package demo.dingtalk.constant;

import lombok.experimental.UtilityClass;

/**
 * DingTalkType
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/11/11
 */
@UtilityClass
public class DingTalkType {
    /**
     * WEBHOOK_PREFIX
     */
    public static final String WEBHOOK_PREFIX = "https://oapi.dingtalk.com/robot/send?access_token=";
    /**
     * TEXT
     */
    public static final String TEXT = "text";
    /**
     * LINK
     */
    public static final String LINK = "link";
    /**
     * MARKDOWN
     */
    public static final String MARKDOWN = "markdown";
    /**
     * ACTION_CARD
     */
    public static final String ACTION_CARD = "actionCard";
}
