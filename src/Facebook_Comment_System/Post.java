package Facebook_Comment_System;

import java.util.*;

public class Post {
    private static int idCounter = 1;

    private int postId;
    private String content;
    private User author;
    private List<Comment> comments;

    public Post(String content, User author) {
        this.postId = idCounter++;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
    }

    public int getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Post{id=" + postId + ", content='" + content + "', by=" + author.getName() + "}";
    }
}

