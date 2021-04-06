package tg_processor;

import assist.TaskManager;
import assist.UTF8Converter;
import commands.*;
import data.UsersDB;
import inlinekeyboard.KeyboardCreator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TGMessageProcessorImpl implements TGMessageProcessor, UTF8Converter {

    private final Map<String, Command> commandMap;

    private final UsersDB usersDB;

    private final TaskManager taskManager;

    private final KeyboardCreator keyboardCreator;

    public TGMessageProcessorImpl(Map<String, Command> commands, UsersDB usersDB, TaskManager taskManager,
                                  KeyboardCreator keyboardCreator) {
        this.commandMap = commands;
        this.usersDB = usersDB;
        this.taskManager = taskManager;
        this.keyboardCreator = keyboardCreator;
    }

    @Override
    public void process(Update update, TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage();
        String message, username;
        long chat_id;
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
            username = update.getCallbackQuery().getFrom().getUserName();
            chat_id = update.getCallbackQuery().getMessage().getChatId();
        } else {
            message = update.getMessage().getText();
            username = update.getMessage().getFrom().getUserName();
            chat_id = update.getMessage().getChatId();
        }
        try {
            //todo
            System.out.println(message);
            String[] inText = message.trim().split("\\s+");
            Command command = commandMap.get(inText[0]);
            if (command != null) {
                switch (inText[0]) {
                    case "/register" -> {
                        sendMessage = ((AuthCommand) command).execute(username, taskManager, chat_id);
                    }
                    case "/finish", "/begin", "/skip", "/next" -> {
                        if (usersDB.isTeacher(username))
                            sendMessage = ((TeacherCommand) command).execute(username, bot);
                        else {
                            sendMessage.setText("Ой.. Кажется вы не преподаватель.");
                        }
                    }
                    case "/start", "/help" -> {
                        sendMessage = ((HelpCommand) command).execute();
                    }
                    case "/queue", "/leave" -> {
                        if (checkAccess(username))
                            if (!usersDB.isTeacher(username))
                                sendMessage = ((QueueCommand) command).execute(username, taskManager, chat_id);
                            else {
                                sendMessage.setText("Вы же препод... Зачем вам записываться в очередь...");
                            }
                        else {
                            sendMessage.setText("Вы не зарегистрированы. Возможно, вы еще находитесь в пуле ожидания.");
                        }
                    }
                    case "/getqueue" -> {
                        if (checkAccess(username))
                            if (usersDB.isTeacher(username)) {
                                sendMessage.setText("К сожалению преподавателям здесь нельзя смотреть на полную очередь. Пользуйтесь, " +
                                        "пожалуйста, командами /begin, /next, /skip, /finish, " +
                                        "чтобы студенты могли следить за продвижением очереди.");
                            } else {
                                try {
                                    sendMessage = ((QueueCommand) command).execute(username, taskManager, chat_id);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    sendMessage.setText("Очередь пустая");
                                }
                            }
                        else {
                            sendMessage.setText("Вы не зарегистрированы. Возможно, вы еще находитесь в пуле ожидания.");
                        }
                    }
                    case "/accept", "/reject" -> {
                        if (usersDB.isAdmin(username))
                            sendMessage = ((AdminCommand) command).execute(username, bot, taskManager, chat_id);
                        else {
                            sendMessage.setText("Это админская команда.");
                        }
                    }
                    case "/reset" -> {
                        taskManager.clearTasks(username);
                        sendMessage.setText("Ввод успешно прерван");
                    }
                }
                send(sendMessage, chat_id, bot);
                if(sendMessage.getReplyMarkup() == null && !inText[0].equals("/register")) {
                    SendMessage sendMessage2 = new SendMessage();
                    sendMessage2.setChatId(String.valueOf(chat_id));
                    sendMessage2.setText(convert("Выберите действие:"));
                    InlineKeyboardMarkup markup = keyboardCreator.getInlineKeyboardMarkup(username);
                    sendMessage2.setReplyMarkup(markup);
                    bot.execute(sendMessage2);
                }
            }
            else {
                sendMessage = checkForTasks(sendMessage, username, inText[0], bot, chat_id);
                send(sendMessage, chat_id, bot);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage.setText("Ой.. Что-то пошло не так.");
            send(sendMessage, chat_id, bot);
        }
    }

    private SendMessage checkForTasks(SendMessage sendMessage, String username, String argument, TelegramLongPollingBot bot, long chat_id) {
        if(taskManager.hasRunningTasks(username)) {
            sendMessage = taskManager.executeNextTask(username, argument, bot, chat_id);
        }
        else
            sendMessage.setText("Я не умею на такое отвечать:( Посмотрите справку по командам: /help.");
        return sendMessage;
    }

    private boolean checkAccess(String username) {
        return usersDB.isPresent(username);
    }

    private void send(SendMessage defaultMessage, long chatId, TelegramLongPollingBot bot) {
        try {
            defaultMessage.setChatId(String.valueOf(chatId));
            defaultMessage.setText(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(defaultMessage.getText().getBytes("windows-1251"))).toString());
            bot.execute(defaultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
