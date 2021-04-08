package exceptions;

public class InvalidRoleException extends Exception {
    public InvalidRoleException() {
        super("Ошибка. Роль должна быть одной из: {admin, student, teacher}. Пожалуйста, введите ее заново.");
    }
}
