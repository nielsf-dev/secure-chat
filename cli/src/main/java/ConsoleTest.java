import jline.AnsiWindowsTerminal;
import jline.Terminal;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleTest {
    public static void main(String[] args) throws IOException {

         AnsiConsole.systemInstall();

         System.out.println(ansi().eraseScreen());
         System.out.println(ansi().cursor(10, 50).render("nu dan"));

        int height = TerminalFactory.get().getHeight();
        int terminalWidth = TerminalFactory.get().getWidth();

        System.out.println(String.format("%d %d", height, terminalWidth));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
    }
}
