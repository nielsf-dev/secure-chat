package org.nelis.securechat.code;

import org.nelis.securechat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackgroundRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    public BackgroundRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = userRepository.findAll();
        System.out.println("Test = " + users.size());
    }
}
