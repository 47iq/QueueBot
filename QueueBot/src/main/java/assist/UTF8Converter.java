package assist;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public interface UTF8Converter {
    default String convert(String text) {
        try {
            text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
            return text;
        } catch (UnsupportedEncodingException ignored) {
            throw new RuntimeException();
        }
    }
}
