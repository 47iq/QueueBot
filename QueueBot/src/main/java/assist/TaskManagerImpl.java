package assist;

import assist.tasks.Task;
import data.WaitingPoolDB;

import java.util.Map;

public class TaskManagerImpl implements TaskManager{

    private Map<String, Task> taskMap;

    private final WaitingPoolDB waitingPoolDB;

    public TaskManagerImpl(WaitingPoolDB waitingPoolDB) {
        this.waitingPoolDB = waitingPoolDB;
    }

    @Override
    public boolean hasRunningTasks(String username) {
        return taskMap.containsKey(username);
    }

    @Override
    public void clearTasks(String username) {
        taskMap.remove(username);
    }

    @Override
    public String executeNextTask(String username, String arg) {
        Task task = taskMap.get(username);
        String ans = task.execute(username, arg, waitingPoolDB);
        taskMap.put(username, task.next());
        return ans;
    }
}
