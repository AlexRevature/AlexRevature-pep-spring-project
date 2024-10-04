package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Account account) {

        if (account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        Account existingUser = accountService.retrieveByUsername(account.getUsername());
        if (existingUser == null) {
            Account persistedAccount = accountService.persistAccount(account);
            if (persistedAccount != null) {
                return ResponseEntity.status(200).body(existingUser);
            }
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(409).build();
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Account account) {
        Account authUser = accountService.authAccount(account.getUsername(), account.getPassword());
        if (authUser != null) {
            return ResponseEntity.status(200).body(authUser);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {

        if (message.getMessageText().length() > 255 || message.getMessageText().length() == 0) {
            return ResponseEntity.status(400).build();
        }

        Message persistedMessage = messageService.persistMessage(message);
        if (persistedMessage != null) {
            return ResponseEntity.status(200).body(persistedMessage);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity getMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessage(@PathVariable int messageId) {
        Message retrievedMessage = messageService.getMessageById(messageId);
        if (retrievedMessage != null) {
            return ResponseEntity.status(200).body(retrievedMessage);
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessages(@PathVariable int messageId) {
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity patchMessages(@PathVariable int messageId, @RequestBody Message message) {

        if (message.getMessageText().length() > 255 || message.getMessageText().length() == 0) {
            return ResponseEntity.status(400).build();
        }

        Message updatedMessage = messageService.updateMessage(messageId, message.getMessageText());
        if (updatedMessage != null) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getUserMessages(@PathVariable int accountId) {
        List<Message> allMessages =  messageService.getUserMessages(accountId);
        return ResponseEntity.status(200).body(allMessages);
    }

}
