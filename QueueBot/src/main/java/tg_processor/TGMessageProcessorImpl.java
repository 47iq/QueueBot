package tg_processor;

import commands.*;
import data.UsersDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    public SendMessage getAnswer(Update update, TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage();
        try {
            Charset charset = Charset.forName("windows-1251");
            String message, username = "";
            long chat_id;
            if (update.hasCallbackQuery()) {
                message = charset.decode(ByteBuffer.wrap(update.getCallbackQuery().getData().getBytes(StandardCharsets.UTF_8))).toString();
                username = charset.decode(ByteBuffer.wrap(update.getCallbackQuery().getFrom().getUserName().getBytes(StandardCharsets.UTF_8))).toString();
                chat_id = update.getCallbackQuery().getMessage().getChatId();
            } else {
                message = charset.decode(ByteBuffer.wrap(update.getMessage().getText().getBytes(StandardCharsets.UTF_8))).toString();
                username = charset.decode(ByteBuffer.wrap(update.getMessage().getFrom().getUserName().getBytes(StandardCharsets.UTF_8))).toString();
                chat_id = update.getMessage().getChatId();
            }

            //String message = update.getMessage().getText();
            //String username = update.getMessage().getFrom().getUserName();
            //todo
            System.out.println(message);
            String[] inText = message.trim().split("\\s+");
            Command command = commandMap.get(inText[0]);
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
                            return ((TeacherCommand)command).execute(username, bot);
                        else {
                            sendMessage.setText("Ой.. Кажется вы не преподаватель.");
                            return sendMessage;
                        }
                    }
                    case "/start", "/help": {
                        return ((HelpCommand)command).execute();
                    }
                    case "/queue", "/leave": {
                        if(checkAccess(username))
                            if(!usersDB.isTeacher(username))
                                return ((QueueCommand)command).execute(username, inText[1]);
                            else {
                                sendMessage.setText("Вы же препод... Зачем вам записываться в очередь...");
                                return sendMessage;
                            }
                        else {
                            sendMessage.setText("Вы не зарегистрированы. Возможно, вы еще находитесь в пуле ожидания.");
                            return sendMessage;
                        }
                    }
                    case "/getqueue": {
                        if(checkAccess(username))
                            if (usersDB.isTeacher(username)) {
                                sendMessage.setText("\"К сожалению преподавателям здесь нельзя смотреть на полную очередь. Пользуйтесь, \" +\n" +
                                        "                                        \"пожалуйста, командами /begin, /next, /skip, /finish,\" +\n" +
                                        "                                        \" чтобы студенты могли следить за продвижением очереди.\"");
                                return sendMessage;
                            }
                            else {
                                return ((QueueCommand) command).execute(username, inText[1]);
                            }
                        else {
                            sendMessage.setText("Вы не зарегистрированы. Возможно, вы еще находитесь в пуле ожидания.");
                            return sendMessage;
                        }
                    }
                    case "/accept", "/reject": {
                        if(usersDB.isAdmin(username))
                            return ((AdminCommand)command).execute(inText[1], bot);
                        else {
                            sendMessage.setText("Это админская команда.");
                            return sendMessage;
                        }
                    }
                }
            sendMessage.setText("Я не умею на такое отвечать:( Посмотрите справку по командам: /help.");
            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage.setText("Ой.. Что-то пошло не так.");
            return sendMessage;
        }
    }

    private boolean checkAccess(String username) {
        return usersDB.isPresent(username);
    }
}
