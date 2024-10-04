package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message persistMessage(Message message) {
        Optional<Account> optionalAccount = accountRepository.findById(message.getPostedBy());
        if (optionalAccount.isPresent()) {
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public Message deleteMessage(int id) {
        Message message = getMessageById(id);
        if (message == null) {
            return null;
        }
        messageRepository.deleteById(id);
        return message;
    }

    public Message updateMessage(int id, String messageText){

        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();

            message.setMessageText(messageText);
            messageRepository.save(message);
            return message;
        }
        return null;

    }

    public List<Message> getUserMessages(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
