package commands;

public class HelpCommand implements Command{
    public String execute() {
        String message = "/register - подать заявку на регистрацию.\n" +
                "Синтаксис: /register имя фамилия роль(student, teacher, admin) группа подгруппа предмет(для teacher).\n" +
                "Пример: /register Павел Данилов student 3110 1\n" +
                "Пример 2: /register Петр Петров teacher 3110 1 OPD\n\n";
        message += "Команды для студентов:\n";
        message += "/queue - зарегистрироваться в очередь. \nСинтаксис: /queue ПРЕДМЕТ. Пример: /queue OPD\n";
        message += "/leave - покинуть очередь. \nСинтаксис: /leave ПРЕДМЕТ. Пример: /leave OPD\n";
        message += "/getqueue - получить текущее состояние очереди. \nСинтаксис /getqueue ПРЕДМЕТ. Пример: /getqueue OPD\n\n";
        message += "Команды для преподавателей:\n";
        message += "/begin - начать прием лабораторной.\n";
        message += "/next - получить следующего сдающего и оповестить его.\n";
        message += "/skip - отправить текущего сдеющего в конец очереди и получить следующего.\n";
        message += "/finish - завершить прием лабораторной.\n";
        return message;
    }
}