package exceptions;

public class FatalError extends Exception{
    public FatalError() {
        super("Ой, что-то пошло сильно не так. Напишите, пожалуйста @" + System.getenv("ADMIN_USERNAME"));
    }
}
