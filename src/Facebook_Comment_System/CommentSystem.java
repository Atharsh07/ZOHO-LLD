package Facebook_Comment_System;

import java.util.*;

public class CommentSystem {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Post> posts = new HashMap<>();
    private Map<Integer, Comment> comments = new HashMap<>();

    // User management
    public User createUser(String name) {
        User user = new User(name);
        users.put(user.getUserId(), user);
        return user;
    }

    // Post management
    public Post createPost(String content, int userId) {
        User user = users.get(userId);
        if (user == null) throw new IllegalArgumentException("User not found.");
        Post post = new Post(content, user);
        posts.put(post.getPostId(), post);
        return post;
    }

    // Add top-level comment to a post
    public Comment addComment(int postId, int userId, String content) {
        Post post = posts.get(postId);
        User user = users.get(userId);
        if (post == null || user == null) throw new IllegalArgumentException("Post/User not found.");

        Comment comment = new Comment(content, user, null);
        post.addComment(comment);
        comments.put(comment.getCommentId(), comment);
        return comment;
    }

    // Reply to a comment (one level nesting enforced)
    public Comment replyToComment(int postId, int parentCommentId, int userId, String content) {
        Post post = posts.get(postId);
        Comment parent = comments.get(parentCommentId);
        User user = users.get(userId);
        if (post == null || parent == null || user == null) throw new IllegalArgumentException("Invalid data.");

        // Enforce 1-level nesting
        Comment topLevel = parent.getParent() == null ? parent : parent.getParent();

        Comment reply = new Comment(content, user, topLevel);
        topLevel.addReply(reply);
        comments.put(reply.getCommentId(), reply);
        return reply;
    }

    // Edit a comment
    public void editComment(int commentId, String newContent) {
        Comment comment = comments.get(commentId);
        if (comment != null) {
            comment.editContent(newContent);
        }
    }

    // Delete a comment
    public void deleteComment(int commentId) {
        Comment comment = comments.get(commentId);
        if (comment != null) {
            comment.delete();
        }
    }

    // Print all comments for a post
    public void displayComments(int postId) {
        Post post = posts.get(postId);
        if (post == null) {
            System.out.println("Post not found.");
            return;
        }

        System.out.println("\n📄 Post: " + post.getContent() + " (by " + post.getAuthor().getName() + ")");
        for (Comment c : post.getComments()) {
            printComment(c, 0);
            for (Comment reply : c.getReplies()) {
                printComment(reply, 1);
            }
        }
    }

    private void printComment(Comment comment, int indent) {
        String space = "  ".repeat(indent);
        System.out.println(space + "- " + comment);
    }
}

