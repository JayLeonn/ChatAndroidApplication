package User;

public class User {

    private String userName;
    private String currentChannel;

    public User (String userId, String channel){

        this.userName = userId;
        this.currentChannel = channel;
    }
    @Override
    public boolean equals(Object object) {
        User thatUser = (User) object;
        return this.userName.equals(thatUser.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    public String getCurrentChannel() {
        return this.currentChannel;
    }

    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {

        this.userName = userName;
    }
}
