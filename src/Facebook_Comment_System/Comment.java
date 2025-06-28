package Facebook_Comment_System;
import java.util.*;

public class Comment {
    private static int idCounter = 1;

    private int commentId;
    private String content;
    private User author;
    private Comment parent; // null for top-level
    private List<Comment> replies;
    private boolean isDeleted;

    public Comment(String content, User author, Comment parent) {
        this.commentId = idCounter++;
        this.content = content;
        this.author = author;
        this.parent = parent;
        this.replies = new ArrayList<>();
        this.isDeleted = false;
    }

    // Getters
    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return isDeleted ? "[Deleted]" : content;
    }

    public User getAuthor() {
        return author;
    }

    public Comment getParent() {
        return parent;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    // Operations
    public void addReply(Comment reply) {
        replies.add(reply);
    }

    public void editContent(String newContent) {
        if (!isDeleted) {
            this.content = newContent;
        }
    }

    public void delete() {
        this.isDeleted = true;
        this.content = "[Deleted]";
    }

    @Override
    public String toString() {
        return "Comment{id=" + commentId + ", content='" + getContent() + "', by=" + author.getName() + "}";
    }
}
