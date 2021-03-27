import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonHelper {

    private static JsonFactory jsonFactory = new JsonFactory();;

    public static String objectNodeToString(ObjectMapper objectMapper, ObjectNode result) throws IOException {
        Writer writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);

        objectMapper.writeTree(jsonGenerator, result);
        return writer.toString();
    }
}
