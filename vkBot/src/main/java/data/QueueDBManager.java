package data;

import java.sql.SQLException;

public interface QueueDBManager {
    void add(String username, Subject subject) throws SQLException;
    String nextStudent(String username) throws SQLException;
    void createAll(int group) throws SQLException;
    void create(Subject subject, int group, int subgroup) throws SQLException;
    void delete(Subject subject, int group, int subgroup) throws SQLException;
}
