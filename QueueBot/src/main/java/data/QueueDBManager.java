package data;

import assist.AlertModule;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public interface QueueDBManager {
    void add(String username, Subject subject) throws SQLException;
    String nextStudent(String username, TelegramLongPollingBot bot) throws SQLException, UnsupportedEncodingException, TelegramApiException;
    String skipStudent(String username, TelegramLongPollingBot bot) throws SQLException, UnsupportedEncodingException, TelegramApiException;
    String startQueue(String username, TelegramLongPollingBot bot) throws SQLException, UnsupportedEncodingException, TelegramApiException;
    void finishQueue(String username) throws SQLException;
    void createAll(String group) throws SQLException;
    void create(Subject subject, String group, int subgroup) throws SQLException;
    void clear(Subject subject, String group, int subgroup) throws SQLException;
    List<String> getQueue(Subject subject, String username) throws SQLException;
    void remove(String username, Subject forName) throws SQLException;
    void setAlertModule(AlertModule alertModule);
}
