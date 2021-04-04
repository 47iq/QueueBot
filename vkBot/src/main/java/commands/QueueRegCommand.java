package commands;

import data.QueueDBManager;
import data.Subject;
import data.UsersDB;

import java.sql.SQLException;

public class QueueRegCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueRegCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(String username, String subject) {
        try {
            manager.add(username, Subject.forName(subject));
            return "�� ������� ������������������ � �������.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "���-�� ����� ������ �� ���. �������� @true47iq ��.";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "���������, �������� �� �������� �������� ����� �� {OPD, Programming}.";
        }
    }
}
