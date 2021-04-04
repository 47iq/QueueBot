package commands;

import data.QueueDBManager;
import data.Subject;
import data.UsersDB;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QueueGetCommand implements QueueCommand{

    private final QueueDBManager manager;

    private final UsersDB usersDB;

    public QueueGetCommand(QueueDBManager manager, UsersDB usersDB) {
        this.manager = manager;
        this.usersDB = usersDB;
    }

    @Override
    public String execute(String username, String subject) {
        try {
            List<String> list = manager.getQueue(Subject.forName(subject), username);
            String result = list.stream().map(usersDB::getName).reduce((x, y)->(x + "\n" + y)).get();
            Charset charset = Charset.forName("windows-1251");
            result = charset.decode(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8))).toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
