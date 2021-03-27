import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    static String backendUrl = "http://localhost:8082/";

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length != 2){
            System.out.println("Verwacht 2 parameters: gebruiker naam en chatroom naam");
        }

        String userName = args[0];
        String chatRoomName = args[1];

        ObjectNode createUser = objectMapper.createObjectNode();
        createUser.put("name", userName);

        JsonNode response = getResponseNode("createUser", createUser);
        System.out.println("Room aangemaakt response: " + response.asText());
    }

    private static JsonNode getResponseNode(String command, ObjectNode requestNode) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(backendUrl + command))
                .POST(HttpRequest.BodyPublishers.ofString(JsonHelper.objectNodeToString(objectMapper, requestNode)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
}
