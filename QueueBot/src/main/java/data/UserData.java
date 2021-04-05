package data;

public interface UserData {

    String getRole();

    int getGroup();

    int getSubGroup();

    String getName();

    String getSurname();

    Subject getSubject();

    long getChat_id();

    void setName(String name);

    void setSurname(String surname);

    void setGroup(int group);

    void setSubGroup(int subGroup);

    void setSubject(Subject subject);

    void setChat_id(long chat_id);

    void setRole(String role);
}
