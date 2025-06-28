package Facebook_Comment_System;
import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommentSystem system = new CommentSystem();

        // Create users
        User alice = system.createUser("Alice");
        User bob = system.createUser("Bob");
        User charlie = system.createUser("Charlie");

        // Create a post
        Post post = system.createPost("Welcome to the new Facebook!", alice.getUserId());

        while (true) {
            System.out.println("\n=== Facebook Comment System ===");
            System.out.println("1. Add Comment");
            System.out.println("2. Reply to Comment");
            System.out.println("3. Edit Comment");
            System.out.println("4. Delete Comment");
            System.out.println("5. Display All Comments");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter User ID: ");
                        int userId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Comment Content: ");
                        String content = scanner.nextLine();
                        system.addComment(post.getPostId(), userId, content);
                        break;

                    case 2:
                        System.out.print("Enter User ID: ");
                        int uid = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Parent Comment ID: ");
                        int parentId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Reply Content: ");
                        String replyContent = scanner.nextLine();
                        system.replyToComment(post.getPostId(), parentId, uid, replyContent);
                        break;

                    case 3:
                        System.out.print("Enter Comment ID to Edit: ");
                        int editId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter New Content: ");
                        String newContent = scanner.nextLine();
                        system.editComment(editId, newContent);
                        break;

                    case 4:
                        System.out.print("Enter Comment ID to Delete: ");
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        system.deleteComment(deleteId);
                        break;

                    case 5:
                        system.displayComments(post.getPostId());
                        break;

                    case 6:
                        System.out.println("Exiting... 👋");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }
}
