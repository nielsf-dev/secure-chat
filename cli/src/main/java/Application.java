import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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

        // user aanmaken
        ObjectNode createUser = objectMapper.createObjectNode();
        createUser.put("name", userName);
        JsonNode createUserResponse = getResponseNode("createuser", createUser);
        long userId = createUserResponse.get("id").asLong();

        // room aanmaken
        ObjectNode createRoom = objectMapper.createObjectNode();
        createRoom.put("name", chatRoomName);
        JsonNode createroomResponse = getResponseNode("createroom", createRoom);
        long roomId = createroomResponse.get("id").asLong();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean success = true;
        while(success){
            // room printen
            printMessages(roomId);

            // nieuwe chatregel verwerken
            String inputLine = reader.readLine();
            ObjectNode sendMessage = objectMapper.createObjectNode();
            sendMessage.put("userid",userId);
            sendMessage.put("chatroomid",roomId);
            sendMessage.put("message",inputLine);
            JsonNode successNode = getResponseNode("sendmessage", sendMessage);
            success = successNode.get("success").asBoolean();
        }
    }

    private static void printMessages(long roomId) throws IOException, InterruptedException {

        ObjectNode showMessages = objectMapper.createObjectNode();
        showMessages.put("chatroomid", roomId);
        JsonNode showmessages = getResponseNode("showmessages", showMessages);

        System.out.println("\n\nChatroom: \n");
        JsonNode messages = showmessages.get("messages");
        for(JsonNode message : messages){
            System.out.println(message.asText());
        }
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
