package UsersList;

import ChatHistory.ChatHistory;
import User.User;
import java.util.ArrayList;
import java.util.HashSet;

public class UsersList {
    private HashSet<User> usersList;

    private static UsersList ourInstance = new UsersList();

    public static UsersList getInstance() {
        return ourInstance;
    }

    private UsersList() {
        this.usersList = new HashSet<User>();
    }

    public void addUser(User usr) {
        UsersList.getInstance().usersList.add(usr);
        ChatHistory.getInstance().broadcastServerMessage("User " + usr.getUserName() + " connected");
    }

    public void removeUser(User usr) {
        UsersList.getInstance().usersList.remove(usr);
        ChatHistory.getInstance().broadcastServerMessage("User " + usr.getUserName() + " disconnected");
    }

    public boolean checkIfUserNameExists(String userName) {
        boolean exists = false;

        for(User user : getAllUsersInList()) {
            if(user.getUserName().equals(userName)) {
                exists = true;
            }
        }

        return exists;
    }

    public ArrayList<User> getAllUsersInList() {
        return new ArrayList<User>(UsersList.getInstance().usersList);
    }
}
