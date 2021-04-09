package exceptions;

public class InvalidSubGroupException extends Exception{
    public InvalidSubGroupException() {
        super("Ошибка. Номер подгруппы может быть 1 или 2 (как будто ты сами это не знал, тестировщик недоделанный).");
    }
}
