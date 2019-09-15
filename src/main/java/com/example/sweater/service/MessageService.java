package com.example.sweater.service;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;


    private MessageDto addAtributePath(MessageDto messageDto, String uploadPath) {
        messageDto.setUploadPath(uploadPath);
        return messageDto;
    }

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user, String uploadPath) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, user).map(item->addAtributePath(item, uploadPath));
        } else {
            return messageRepo.findAll(pageable, user).map(item->addAtributePath(item, uploadPath));
        }
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }
}
