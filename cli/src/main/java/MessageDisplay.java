import org.fusesource.jansi.Ansi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;

import static org.fusesource.jansi.Ansi.ansi;

public class MessageDisplay {

    private static Logger logger = LoggerFactory.getLogger(MessageDisplay.class);

    int height;

    public MessageDisplay(int height) {
        this.height = height;
    }

    public void render(List<ChatMessage> messages){
        logger.debug("{} total messages", messages.size());

        System.out.println(ansi().cursor(0,0).render("Chatroom met zoveel messages " + messages.size()));

        // telkens alleen de laatste height messages printen
        List<ChatMessage> lastMessages = messages.stream()
                .collect(lastN(height));

        for (int i = 0; i < lastMessages.size(); i++) {
            ChatMessage chatMessage = lastMessages.get(i);
            String message = chatMessage.user + ": " + chatMessage.getMessage();
            logger.debug("message = " + message);
            Ansi display = ansi().cursor(i+5, 5).render(message);
            System.out.println(display);
        }
    }

    public static <T> Collector<T, ?, List<T>> lastN(int n) {
        return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
            if(acc.size() == n)
                acc.pollFirst();
            acc.add(t);
        }, (acc1, acc2) -> {
            while(acc2.size() < n && !acc1.isEmpty()) {
                acc2.addFirst(acc1.pollLast());
            }
            return acc2;
        }, ArrayList::new);
    }
}
