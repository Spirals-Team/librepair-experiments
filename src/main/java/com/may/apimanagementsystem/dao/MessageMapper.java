package com.may.apimanagementsystem.dao;

import com.may.apimanagementsystem.po.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {


    boolean insertMessage(Message message);

    List<Message> selectMessages(int userId);

    boolean deleteMessage(int messageId);

    Message readMyMessage(int messageId);
}
