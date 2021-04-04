package tg_processor;

import commands.*;
import data.QueueDBManager;
import data.UsersDB;
import org.eclipse.jetty.server.Authentication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TGMessageProcessorImpl implements TGMessageProcessor{

    private final Map<String, Command> commandMap;

    private final UsersDB usersDB;

    public TGMessageProcessorImpl(Map<String, Command> commands, UsersDB usersDB) {
        this.commandMap = commands;
        this.usersDB = usersDB;
    }

    @Override
    public String getAnswer(Update update, TelegramLongPollingBot bot) {
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = charset.decode(ByteBuffer.wrap(update.getMessage().getText().getBytes(StandardCharsets.UTF_8))).toString();
            //String message = update.getMessage().getText();
            String username = charset.decode(ByteBuffer.wrap(update.getMessage().getFrom().getUserName().getBytes(StandardCharsets.UTF_8))).toString();
            //String username = update.getMessage().getFrom().getUserName();
            //todo
            System.out.println(message);
            String[] inText = message.trim().split("\\s+");
            Command command = commandMap.get(inText[0]);
            long chat_id = update.getMessage().getChatId();
            if(command != null)
                switch (inText[0]) {
                    case "/register": {
                        if(inText.length == 6)
                            return ((AuthCommand)command).execute(bot, username, inText[1], inText[2], inText[3],
                                    Integer.parseInt(inText[4]), Integer.parseInt(inText[5]), null, chat_id);
                        else
                            return ((AuthCommand)command).execute(bot,username, inText[1], inText[2], inText[3],
                                Integer.parseInt(inText[4]), Integer.parseInt(inText[5]), inText[6], chat_id);
                    }
                    case "/finish", "/begin", "/skip", "/next": {
                        if(usersDB.isTeacher(username))
                            return ((TeacherCommand)command).execute(username);
                        else
                            return "Ой.. Кажется вы не преподаватель.";
                    }
                    case "/start", "/help": {
                        return ((HelpCommand)command).execute();
                    }
                    case "/queue", "/getqueue": {
                        if(checkAccess(username))
                            return ((QueueCommand)command).execute(username, inText[1]);
                        else
                            return "Вы не зарегистрированы. Возможно, вы еще находитесь в пуле ожидания.";
                    }
                    case "/accept": {
                        if(usersDB.isAdmin(username))
                            return ((AdminCommand)command).execute(inText[1], bot);
                        else
                            return "Это админская команда, сорри.";
                    }
                }
            return "Я не умею на такое отвечать:(";
        } catch (Exception e) {
            e.printStackTrace();
            return  "Ой.. Что-то пошло не так.";
        }
    }

    private boolean checkAccess(String username) {
        return usersDB.isPresent(username);
    }
}
