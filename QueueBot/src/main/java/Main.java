import assist.*;
import commands.*;
import data.DBManager;
import data.DBManagerImpl;
import inlinekeyboard.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import tg_processor.QueueBot;
import tg_processor.TGMessageProcessorImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static DBManager manager;

    private static AlertModule alertModule;

    public static void main(String[] args) {
        String DB_URL = null, USER = null, PASS = null, BOT_KEY = null;
        try {
            DB_URL = args[0];
            USER = args[1];
            PASS = args[2];
            BOT_KEY = args[3];
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            ObjectFactory factory = new ObjectFactoryImpl();
            manager = new DBManagerImpl(factory);
            manager.start(DB_URL, USER, PASS);
            alertModule = new AlertModuleImpl(manager.getUsersDB(), manager.getWaitingPool());
            manager.getQueueDB().setAlertModule(alertModule);
            KeyboardCreator keyboardCreator = new KeyboardCreatorImpl(manager.getUsersDB());
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TaskManager taskManager = new TaskManagerImpl(manager.getWaitingPool(), alertModule, manager.getUsersDB(),
                    new RoleInlineKeyboardCreator(), new SubGroupInlineKeyboardCreator(), new SubjectInlineKeyBoardCreator(),
                    new ListedInlineKeyboardCreator(), manager.getQueueDB(), keyboardCreator);
            telegramBotsApi.registerBot(new QueueBot(new TGMessageProcessorImpl(getCommands(),
                    manager.getUsersDB(), taskManager, keyboardCreator), BOT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Command> getCommands() {
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("/register", new RegisterCommand(manager.getWaitingPool(), alertModule));
        commandMap.put("/help", new HelpCommand());
        commandMap.put("/start", new StartCommand());
        commandMap.put("/queue", new QueueRegCommand(manager.getQueueDB()));
        commandMap.put("/leave", new QueueLeaveCommand(manager.getQueueDB()));
        commandMap.put("/getqueue", new QueueGetCommand(manager.getQueueDB(), manager.getUsersDB()));
        commandMap.put("/skip", new SkipCommand(manager.getQueueDB()));
        commandMap.put("/begin", new BeginCommand(manager.getQueueDB()));
        commandMap.put("/finish", new FinishCommand(manager.getQueueDB()));
        commandMap.put("/next", new NextCommand(manager.getQueueDB()));
        commandMap.put("/accept", new AcceptCommand(manager.getUsersDB(), manager.getWaitingPool(), alertModule));
        commandMap.put("/reject", new RejectCommand(manager.getUsersDB(), manager.getWaitingPool(), alertModule));
        return commandMap;
    }
}
