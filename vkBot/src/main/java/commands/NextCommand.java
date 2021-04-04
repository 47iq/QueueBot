package commands;

import data.QueueDBManager;
import data.UsersDB;

public class NextCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public NextCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username) {
        try {
            return queueDBManager.nextStudent(username);
        } catch (Exception e) {
            return "��.. ���-�� ����� ������ �� ���. �������� @the47iq";
        }
    }
}
