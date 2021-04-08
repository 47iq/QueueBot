package exceptions;

public class InvalidGroupException extends Exception{
    public InvalidGroupException() {
        super("Ошибка. Название группы не прошло валидацию. Проверьте, введена ли буква группы на латинице.");
    }
}
