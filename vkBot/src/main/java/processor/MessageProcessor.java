package processor;

import com.petersamokhin.bots.sdk.objects.Message;

public interface MessageProcessor {
    Message getMessage(Message message);
}
