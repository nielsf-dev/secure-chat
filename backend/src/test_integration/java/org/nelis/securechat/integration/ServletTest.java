package org.nelis.securechat.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.nelis.securechat.App;
import org.nelis.securechat.TomcatHelper;
import org.nelis.securechat.service.blocking.ChatRoomManager;
import org.nelis.securechat.service.blocking.dao.DaoManager;
import org.nelis.securechat.service.blocking.servlet.ChatServlet;
import org.nelis.securechat.service.blocking.servlet.CreateRoom;
import org.nelis.securechat.service.blocking.servlet.ChatServletCommand;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.nelis.securechat.TomcatHelper.startTomcat;

public class ServletTest {
    @Test
    public void testServlet() throws IOException, InterruptedException {
        ChatServlet chatServlet = App.createChatServlet();

        Thread t = new Thread(() ->{
            startTomcat(chatServlet,8082);
        });
        t.start();

        Thread.sleep(3000);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/createroom"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"conjomang\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Room aangemaakt response: " + response.body());
    }

}
