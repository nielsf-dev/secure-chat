import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.fusesource.jansi.Ansi;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class CliApp {

    private static Logger logger = LoggerFactory.getLogger(CliApp.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    static String backendUrl = "http://localhost:8082/";
    private static MessageDisplay messageDisplay;
    private static HttpClient client;

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length != 2){
            System.out.println("Verwacht 2 parameters: gebruiker naam en chatroom naam");
        }

        client = HttpClient.newBuilder().build();

        Terminal terminal = TerminalBuilder.terminal();
        int height = terminal.getHeight();
        messageDisplay = new MessageDisplay(height -5);

        // user aanmaken
        String userName = args[0];
        long userId = createUser(userName);

        // room aanmaken
        String chatRoomName = args[1];
        long roomId = createRoom(chatRoomName);

        LineReader reader = LineReaderBuilder.builder()
                .build();

        boolean success = true;
        while(success){
            // room printen
            printMessages(roomId);

            // nieuwe chatregel verwerken
            String chatMessage = readChatMessage(height, userName, reader);
            success = sendChatMessage(userId, roomId, chatMessage);
        }
    }

    private static boolean sendChatMessage(long userId, long roomId, String chatMessage) throws IOException, InterruptedException {
        boolean success;
        ObjectNode sendMessage = objectMapper.createObjectNode();
        sendMessage.put("userid", userId);
        sendMessage.put("chatroomid", roomId);
        sendMessage.put("message", chatMessage);
        JsonNode successNode = getResponseNode("sendmessage", sendMessage);
        success = successNode.get("success").asBoolean();
        return success;
    }

    private static String readChatMessage(int height, String userName, LineReader reader) {
        System.out.print(ansi().cursor(height, 5).render(userName + ": "));
        String chatMessage = reader.readLine();
        return chatMessage;
    }

    private static void printMessages(long roomId) throws IOException, InterruptedException {
        ObjectNode showMessages = objectMapper.createObjectNode();
        showMessages.put("chatroomid", roomId);
        JsonNode showmessages = getResponseNode("showmessages", showMessages);

        JsonNode messages = showmessages.get("messages");
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        for(JsonNode message : messages){
            ChatMessage chatMessage = objectMapper.treeToValue(message, ChatMessage.class);
            chatMessages.add(chatMessage);
        }

        System.out.println(ansi().eraseScreen());
        messageDisplay.render(chatMessages);
    }

    private static long createRoom(String chatRoomName) throws IOException, InterruptedException {
        ObjectNode createRoom = objectMapper.createObjectNode();
        createRoom.put("name", chatRoomName);
        JsonNode createroomResponse = getResponseNode("createroom", createRoom);
        long roomId = createroomResponse.get("id").asLong();
        return roomId;
    }

    private static long createUser(String userName) throws IOException, InterruptedException {
        ObjectNode createUser = objectMapper.createObjectNode();
        createUser.put("name", userName);
        JsonNode createUserResponse = getResponseNode("createuser", createUser);
        return createUserResponse.get("id").asLong();
    }

    private static JsonNode getResponseNode(String command, ObjectNode requestNode) throws IOException, InterruptedException {
        String requestBody = JsonHelper.objectNodeToString(objectMapper, requestNode);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(backendUrl + command))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
}
