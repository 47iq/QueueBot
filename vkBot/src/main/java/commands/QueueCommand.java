package commands;

public interface QueueCommand extends Command{
    String execute(String username, String subject);
}
