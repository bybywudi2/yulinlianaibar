package com.by.controller;

/**
 * Created by Administrator on 2016/8/7.
 */
import Utils.ServerEncoder;
import com.by.bean.WxUser;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.redis.JedisUtil;
import com.by.redis.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。
/**
 类似Servlet的注解mapping。无需在web.xml中配置。
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 */
@ServerEndpoint(value = "/websocket",configurator = SpringConfigurator.class,encoders = { ServerEncoder.class })
public class MyWebSocket {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。

    private static int onlineCount = 0;

    public MyWebSocket() {
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    private static ConcurrentHashMap webSocketMap = new ConcurrentHashMap();
    //与客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String openid;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        Map map = session.getRequestParameterMap();
        List openids = (List)map.get("openid");
        this.openid = (String) openids.get(0);
        webSocketMap.put(openid,this);
        addOnlineCount();           //在线数加1

        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount() + openid + "size" + webSocketMap.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketMap.remove(openid);  //从set中删除
        this.openid = null;
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount() + "size" + webSocketMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message + "openid" + openid);

        WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);
        long target = wxUser.getMatchingUserId();
        if(target != 0){
            WxUser targetUser = wxUserMapper.selectByPrimaryKey(target);
            String targetOpenId = targetUser.getOpenid();
            MyWebSocket targetSocket = (MyWebSocket) webSocketMap.get(targetOpenId);

            Map map = new HashMap();
            map.put("content",message);
            map.put("time",new Date().getTime());
            map.put("sender",openid);
            map.put("reciever",targetOpenId);
            map.put("senderName",wxUser.getNickname());

            if(targetSocket != null){
                try {
                    targetSocket.sendMessage(map);
                }catch (Exception e){
                    System.out.println("出错");
                }
            }else{
                String key = openid + "->" + targetOpenId;
                JedisUtil.getJedis().lpush(SerializeUtil.serialize(key), SerializeUtil.serialize(map));
            }

        }

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(Map message) throws IOException, EncodeException {
        //保存数据到数据库
        //this.session.getBasicRemote().sendText(message);

        this.session.getBasicRemote().sendObject(message);
        //this.session.getAsyncRemote().sendText(message);


    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

}
