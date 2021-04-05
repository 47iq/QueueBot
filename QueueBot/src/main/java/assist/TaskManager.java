package assist;

public interface TaskManager {
    boolean hasRunningTasks(String username);
    void clearTasks(String username);
    String executeNextTask(String username, String arg);
}
