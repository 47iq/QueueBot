package data;

public class UserDataImpl implements UserData{
    private String role;
    private String group;
    private int subGroup;
    private String name;
    private String surname;
    private Subject subject;
    private long chat_id;

    public UserDataImpl(String name, String surname, String role, String group, int subGroup, String subject, long chat_id) {
        this.name = name;
        this.role = role;
        this.group = group;
        this.subGroup = subGroup;
        this.surname = surname;
        if(subject == null)
            this.subject = null;
        else
            this.subject = Subject.forName(subject);
        this.chat_id = chat_id;
    }

    public UserDataImpl() {
    }

    public String getRole() {
        return role;
    }

    public String getGroup() {
        return group;
    }

    public int getSubGroup() {
        return subGroup;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Subject getSubject() {
        return subject;
    }

    public long getChat_id() {
        return chat_id;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void setSubGroup(int subGroup) {
        this.subGroup = subGroup;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    @Override
    public String toString() {
        return "UserDataImpl{" +
                "role='" + role + '\'' +
                ", group=" + group +
                ", subGroup=" + subGroup +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", subject=" + subject +
                ", chat_id=" + chat_id +
                '}';
    }
}
