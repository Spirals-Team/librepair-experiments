package com.may.apimanagementsystem.user.dao;


import com.may.apimanagementsystem.dao.MessageMapper;
import com.may.apimanagementsystem.po.Message;
import com.may.apimanagementsystem.service.MessageServise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class MessageDaoTest {

    @Autowired
    private MessageMapper messageMapper;
    private Message message;
    private MessageServise messageServise;


    @Before
    public  void before()
    {
        message=new Message();
        message.setUserId(1001);
        message.setSendUserId(1000);
        message.setTeamId(1001);

    }

    @Test
    public void inviteInsertMessageTest()
    {
        message.setUserId(1000);
        message.setSendUserId(1001);
        message.setTeamId(1002);
        boolean flag = messageMapper.insertMessage(message);
        assertEquals(true,flag);
    }

    @Test
    public void insertMessageTest()
    {
        message.setUserId(1001);
        message.setTeamId(1002);
        boolean flag =messageMapper.insertMessage(message);
        assertEquals(true,flag);

    }

    @Test
    public void selectMessagesTset()
    {
        List<Message> messages=messageMapper.selectMessages(1001);
        System.out.println(messages.get(0).getTeamId());
        assertEquals(2,messages.size());
    }

    @Test
    public void deleteMessageTest()
    {
     boolean flag=messageMapper.deleteMessage(1003);
     assertEquals(true,flag);
    }

    @Test
    public void readMyMessageTest()
    {
        Message message=messageMapper.readMyMessage(1000);
        assertEquals(1000 ,message.getSendUserId());
    }

}
