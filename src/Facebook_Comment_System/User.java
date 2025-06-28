package Facebook_Comment_System;

public class User {

    private static int idCounter = 1;

    private int userId;
    private String name;

    public User(String name) {
        this.userId = idCounter++;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", name='" + name + "'}";
    }


}
