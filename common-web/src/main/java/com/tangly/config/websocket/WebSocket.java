package com.tangly.config.websocket;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangly.bean.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author tangly
 */
@Slf4j
@ServerEndpoint(value = "/endpoint/{token}")
@Component
@Scope("Prototype")
public class WebSocket {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet();

    private String token;

    public String getToken() {
        return token;
    }

    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) {
        this.session = session;
        this.token = token;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("有新连接加入 {} ！当前在线人数为 {}", token, getOnlineCount());
        try {
            broadcastMessage(JSON.toJSONString(ResponseBean.success("有新连接加入 " + token + " ！当前在线人数为 " + getOnlineCount() + "", null)));
            sendMessageToThis(JSON.toJSONString(ResponseBean.success("欢迎连接", null)));
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一连接关闭 {} ！当前在线人数为 {}", token, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);

        JSONObject json = (JSONObject) JSON.parse(message);
        String msg = JSON.toJSONString(ResponseBean.success(json.getString("msg"), null));
        String type = json.getString("type");
        String to = json.getString("to");
        try {
            if (StringUtils.isEmpty(to)) {
                broadcastMessage(msg);
            } else {
                sendMessageToSomeOne(to, msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessageToSomeOne(String token, String message) throws IOException {

        log.info("单独发送消息给客户端 {} : {} ", token, message);

        for (WebSocket item : webSocketSet) {
            try {
                if (token.equals(item.getToken())) {
                    item.sendMessageToThis(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给该WebSocket发送信息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToThis(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 广播消息
     */
    public static void broadcastMessage(String message) throws IOException {
        log.info(message);
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessageToThis(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
}  