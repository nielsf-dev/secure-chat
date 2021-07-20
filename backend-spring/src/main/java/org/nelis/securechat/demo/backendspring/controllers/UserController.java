package org.nelis.securechat.demo.backendspring.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.nelis.securechat.demo.backendspring.data.UserRepository;
import org.nelis.securechat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Data
    @NoArgsConstructor
    static class User{
        private String name;
    }

    @PostMapping("/createuser")
    public void createUser(@RequestBody User user){
        org.nelis.securechat.domain.User domainUser = new org.nelis.securechat.domain.User(user.getName());
        userRepository.save(domainUser);
    }
}
