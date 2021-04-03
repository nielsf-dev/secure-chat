package org.nelis.securechat.integration;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.nelis.securechat.App;
import org.nelis.securechat.service.blocking.DaoRegistryImp;
import org.nelis.securechat.service.blocking.SessionFactoryBuilder;
import org.nelis.securechat.service.blocking.servlet.ChatServlet;
import org.nelis.securechat.service.blocking.servlet.TxFilter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.nelis.securechat.service.blocking.TomcatHelper.startTomcat;

public class ServletTest {
    @Test
    public void testServlet() throws IOException, InterruptedException {

        SessionFactoryBuilder sessionFactoryBuilder = new SessionFactoryBuilder();
        SessionFactory sessionFactory = sessionFactoryBuilder.build();

        DaoRegistryImp daoRegistry = new DaoRegistryImp(sessionFactory);
        ChatServlet chatServlet = App.createChatServlet(daoRegistry);
        TxFilter txFilter = App.createChatFilter(sessionFactory);

        Thread t = new Thread(() ->{
            startTomcat(chatServlet, txFilter, 8082);
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
