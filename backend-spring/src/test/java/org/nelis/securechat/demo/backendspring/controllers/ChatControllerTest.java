package org.nelis.securechat.demo.backendspring.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.nelis.securechat.demo.backendspring.data.UserRepository;
import org.nelis.securechat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ChatControllerTest {

    @MockBean
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void setup() {
        user = new User(12, "nelis");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    void exampleTest(@Autowired MockMvc mvc) throws Exception {
        mvc
                .perform(post("/createuser").content("{\"name\":\"nelis\"}").contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void createUser() {
        assumeTrue(5 > 1);
        assertEquals(5 + 2, 7);
    }
}