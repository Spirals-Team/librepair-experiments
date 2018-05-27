package com.may.apimanagementsystem.controller;


import com.may.apimanagementsystem.dto.ResponseEntity;
import com.may.apimanagementsystem.po.Message;
import com.may.apimanagementsystem.service.MessageServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.may.apimanagementsystem.constant.ExceptionMessage.SUCCESS;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageServise messageServise;

    @PostMapping("invite")
    public ResponseEntity<Message> inviteAddmessage(Message message) {
        messageServise.inviteAddMessage(message);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("join")
    public ResponseEntity<Message> joinAddmessage(Message message) {
        messageServise.addMessage(message);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @DeleteMapping("{messageId}")
    public ResponseEntity<Message> deleteMessage(@PathVariable int messageId) {
        messageServise.removeMessage(messageId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("{messageId}")
    public ResponseEntity<Message> readMessage(@PathVariable int messageId) {
        Message message = messageServise.rdMyMessage(messageId);
        return new ResponseEntity<>(200, SUCCESS, message);
    }

    @GetMapping("userMessage")
    public ResponseEntity<List<Message>> showMyMessages(int userId) {
        List<Message> messages = messageServise.selectMyMessages(userId);
        return new ResponseEntity<>(200, SUCCESS, messages);
    }
}
