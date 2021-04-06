package assist;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Win1251Converter {
    default String convert(String text) {
        if(text == null)
            return null;
        Charset charset = Charset.forName("windows-1251");
        text = charset.decode(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8))).toString();
        return text;
    }
}
