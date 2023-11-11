package demo.dingtalk.config;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import demo.dingtalk.constant.DingTalkType;
import demo.dingtalk.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * DingTalkClient
 * <p>
 * <a href="https://open.dingtalk.com/document/robots/custom-robot-access#title-jfe-yo9-jl2">DingTalk接入文档</a>
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/11/11
 */
@Slf4j
@Component
public class DingTalkRobotClient {
    /**
     * client
     */
    private final DingTalkClient client;

    /**
     * DtClient
     */
    public DingTalkRobotClient(DingTalkConfig config) throws Exception {
        String webhook = config.getWebhook();
        AssertUtil.notBlank(webhook, "the dingtalk robot webhook is not blank");
        String secret = config.getSecret();
        AssertUtil.notBlank(secret, "the dingtalk robot secret is not blank");

        Long timestamp = System.currentTimeMillis();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal((timestamp + "\n" + secret).getBytes(StandardCharsets.UTF_8));
        String sign = String.format("&timestamp=%s&sign=%s", timestamp,
                URLEncoder.encode(new String(Base64.encodeBase64(signData)), String.valueOf(StandardCharsets.UTF_8)));
        this.client = new DefaultDingTalkClient(DingTalkType.WEBHOOK_PREFIX + webhook + sign);
    }

    /**
     * 发送普通文本消息
     *
     * @param content 内容
     * @param isAtAll 是否at全体人员
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendText(String content, boolean isAtAll) {
        return this.sendText(content, isAtAll, null);
    }

    /**
     * sendText
     *
     * @param content 内容
     * @param isAtAll 是否at全体人员
     * @param mobiles 指定at人员手机号
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendText(String content, boolean isAtAll, List<String> mobiles) {
        AssertUtil.notBlank(content, "the dingtalk robot send type is text, so context is not blank");

        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setText(text);
        request.setMsgtype(DingTalkType.TEXT);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(isAtAll);
        if (!isAtAll && !CollectionUtils.isEmpty(mobiles)) {
            at.setAtMobiles(mobiles);
        }
        request.setAt(at);

        return this.sendRobotTalk(request);
    }

    /**
     * 发送link 类型消息
     *
     * @param title  消息标题
     * @param text   消息内容
     * @param msgUrl 点击消息后跳转的url
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendLink(String title, String text, String msgUrl) {
        return this.sendLink(title, text, msgUrl, null);
    }

    /**
     * 发送link 类型消息
     *
     * @param title  消息标题
     * @param text   消息内容
     * @param msgUrl 点击消息后跳转的url
     * @param picUrl 插入图片的url
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendLink(String title, String text, String msgUrl, String picUrl) {
        AssertUtil.notBlank(title, "the dingtalk robot send type is link, so title is not blank");
        AssertUtil.notBlank(text, "the dingtalk robot send type is link, so text is not blank");
        AssertUtil.notBlank(msgUrl, "the dingtalk robot send type is link, so msgUrl is not blank");

        /***
         * 参数	        参数类型	必须	  说明
         * msgtype	    String	是	  消息类型,此时固定为：link
         * title	    String	是	  消息标题
         * text	        String	是	  消息内容,如果太长只会部分展示
         * messageUrl	String	是	  点击消息跳转的URL
         * picUrl	    String	否	  图片URL
         */
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setTitle(title);
        link.setText(text);
        link.setMessageUrl(msgUrl);
        if (StringUtils.isNotBlank(picUrl)) {
            link.setPicUrl(picUrl);
        }

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(DingTalkType.LINK);
        request.setLink(link);

        return this.sendRobotTalk(request);
    }

    /**
     * 发送Markdown 编辑格式的消息
     *
     * @param title  标题
     * @param mdText 内容
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendMarkdown(String title, String mdText) {
        AssertUtil.notBlank(title, "the dingtalk robot send type is markdown, so title is not blank");
        AssertUtil.notBlank(mdText, "the dingtalk robot send type is markdown, so mdText is not blank");

        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(mdText);


        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(DingTalkType.MARKDOWN);
        request.setMarkdown(markdown);

        return this.sendRobotTalk(request);
    }

    /**
     * 独立跳转ActionCard类型 消息发送
     *
     * @param title          标题
     * @param acText         文本 支持markdown
     * @param btns           按钮列表
     * @param btnOrientation 是否横向排列(true 横向排列, false 纵向排列)
     * @return OapiRobotSendResponse
     */
    public OapiRobotSendResponse sendActionCard(String title,
                                                String acText,
                                                List<OapiRobotSendRequest.Btns> btns,
                                                boolean btnOrientation) {
        AssertUtil.notBlank(title, "the dingtalk robot send type is actionCard, so title is not blank");
        AssertUtil.notBlank(acText, "the dingtalk robot send type is actionCard, so acText is not blank");
        AssertUtil.notEmptyList(btns, "the dingtalk robot send type is actionCard, so btns is not blank");

        OapiRobotSendRequest.Actioncard actionCard = new OapiRobotSendRequest.Actioncard();
        actionCard.setTitle(title);
        actionCard.setText(acText);
        actionCard.setBtnOrientation(btnOrientation ? "1" : "0");
        actionCard.setBtns(btns);

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(DingTalkType.ACTION_CARD);
        request.setActionCard(actionCard);

        return this.sendRobotTalk(request);
    }

    /**
     * 发送消息到钉钉机器人
     *
     * @param request OapiRobotSendRequest
     * @return OapiRobotSendResponse
     */
    private OapiRobotSendResponse sendRobotTalk(OapiRobotSendRequest request) {
        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = client.execute(request);
            log.info("[dingtalk-robot]-消息响应结果[{}]", JSON.toJSONString(response));
        } catch (ApiException e) {
            log.error("[dingtalk-robot]消息发送失败[{}]", e.getMessage());
        }
        return response;
    }
}
