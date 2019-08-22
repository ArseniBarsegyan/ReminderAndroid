package com.arsbars.reminderandroid.data.user;

public class User {
    private long id;
    private String username;
    private String password;
    private byte[] imageContent;

    User(long id, String username, String password, byte[] imageContent) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.imageContent = imageContent;
    }

    User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.imageContent = user.imageContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }
}
