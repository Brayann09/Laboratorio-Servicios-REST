package com.example.gestioncombustible.network.model;

public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    // Constructor vacío
    public Post() {
    }

    // Constructor completo
    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    // Getter userId
    public int getUserId() {
        return userId;
    }

    // Setter userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter id
    public int getId() {
        return id;
    }

    // Setter id
    public void setId(int id) {
        this.id = id;
    }

    // Getter title
    public String getTitle() {
        return title;
    }

    // Setter title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter body
    public String getBody() {
        return body;
    }

    // Setter body
    public void setBody(String body) {
        this.body = body;
    }
}
