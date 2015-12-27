import java.util.HashMap;
import java.util.Map;


public class AccountService {
    private final Map<String, UsersDataSet> sessionIdToProfile;
    private final DBService dbService = new DBService();
    public AccountService() {
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UsersDataSet dataSet) {
        try {
            dbService.addUser(dataSet.getName(), dataSet.getPass());
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UsersDataSet getUserByLogin(String login) throws DBException {
        return dbService.getUser(login);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet dataSet) {
        sessionIdToProfile.put(sessionId, dataSet);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
