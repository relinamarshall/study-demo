package demo.dingtalk;

import com.dingtalk.api.request.OapiRobotSendRequest;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import demo.dingtalk.config.DingTalkRobotClient;

/**
 * DingTalkApplicationTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/11/11
 */
@SpringBootTest(classes = DingTalkApplication.class)
@RunWith(SpringRunner.class)
public class DingTalkApplicationTest {
    /**
     * client
     */
    @Autowired
    private DingTalkRobotClient client;

    /**
     * TEST_AT_MOBILE
     */
    private static final String TEST_AT_MOBILE = "18768509725";

    @Test
    public void sendText() {
        String text = "test";

        client.sendText(text, true);
        client.sendText(text, false, Lists.newArrayList(TEST_AT_MOBILE));
    }

    @Test
    public void sendLink() {
        String title = "时代的火车向前开";
        String text = "这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林";
        String msgUrl = "https://www.dingtalk.com/";
        String picUrl = "https://img0.baidu.com/it/u=2020518972,2077284106&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500";

        client.sendLink(title, text, msgUrl);
        client.sendLink(title, text, msgUrl, picUrl);
    }

    @Test
    public void sendMarkdown() {
        String title = "杭州天气";
        String markdown = "#### 杭州天气\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n" +
                "> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n" +
                "> ###### 10点20分发布 [天气](https://www.dingtalk.com) \n";

        client.sendMarkdown(title, markdown);
    }

    @Test
    public void sendActionCard() {
        String title = "我 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身";
        String markdown = "![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png) \n\n" +
                "#### 乔布斯 20 年前想打造的苹果咖啡厅 \n\n" +
                "Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划";
        OapiRobotSendRequest.Btns btns1 = new OapiRobotSendRequest.Btns();
        btns1.setTitle("内容不错");
        btns1.setActionURL("https://www.dingtalk.com/");
        OapiRobotSendRequest.Btns btns2 = new OapiRobotSendRequest.Btns();
        btns2.setTitle("不感兴趣");
        btns2.setActionURL("https://www.dingtalk.com/");
        List<OapiRobotSendRequest.Btns> btns = Lists.newArrayList(btns1, btns2);

        client.sendActionCard(title, markdown, btns, false);
        client.sendActionCard(title, markdown, btns, true);
    }
}
