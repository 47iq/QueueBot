package data;

public class UserDataImpl implements UserData{
    final String role;
    final int group;
    final int subGroup;
    final String name;
    final String surname;
    final Subject subject;
    final long chat_id;

    public UserDataImpl(String name, String surname, String role, int group, int subGroup, String subject, long chat_id) {
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

    public String getRole() {
        return role;
    }

    public int getGroup() {
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
