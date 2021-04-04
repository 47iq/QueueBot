package vk_processor;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import commands.Command;

import java.util.Map;

public class MessageProcessorImpl implements MessageProcessor{

    private final Map<String, Command> commandMap;

    private final Group group;

    public MessageProcessorImpl(Map<String, Command> commandMap, Group group) {
        this.commandMap = commandMap;
        this.group = group;
    }

    @Override
    public Message getMessage(Message message) {
        String[] messageText = message.getText().trim().split("\\s+");
        String argument = (messageText.length == 1 ? "" : messageText[1]);
        /*String answer = getCommand(messageText[0]).execute(argument);
        new Message()
                    .from(group)
                    .to(message.authorId())
                    .text(answer);
        return message;*/
        return null;
    }

    private Command getCommand(String commandStr) {
        Command command = commandMap.get(commandStr);
        if(command == null)
            throw new RuntimeException("Ой... Не знаю такую команду.");
        else
            return command;
    }
}
