package org.nelis.securechat.demo.backendspring.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.nelis.securechat.demo.backendspring.data.RoomRepository;
import org.nelis.securechat.demo.backendspring.data.UserRepository;
import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Data
    @NoArgsConstructor
    static class NameDto {
        private String name;
    }

    @PostMapping("/createuser")
    public void createUser(@RequestBody NameDto nameDto){
        User user = new User(nameDto.getName());
        userRepository.save(user);
    }

    @PostMapping("/createroom")
    public void createRoom(@RequestBody NameDto nameDto){
        ChatRoom chatRoom = new ChatRoom(nameDto.getName());
        roomRepository.save(chatRoom);
    }
}
