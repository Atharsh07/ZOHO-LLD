package Mail_management_System;
import java.util.*;
import java.util.stream.Collectors;

public class MailManagamentSystem {

    private List<Mail> mails;
    private Set<String> spamWords;
    private Scanner scanner;

    public MailManagamentSystem(){
        mails = new ArrayList<>();
        spamWords = new HashSet<>(Arrays.asList("lottery", "winner", "prize", "free"));
        scanner = new Scanner(System.in);
    }

    public void StoreMail(){
        System.out.print("Enter the sender Mail : ");
        String sender = scanner.nextLine();
        System.out.print("Enter the receiver Mail : ");
        String receiver = scanner.nextLine();
        System.out.print("Enter the subject : ");
        String subject = scanner.nextLine();
        System.out.print("Enter the content : ");
        String content = scanner.nextLine();

        Mail mail = new Mail(sender, receiver, subject, content);
        checkMail(mail);
        mails.add(mail);
        System.out.println("Mail stored successfully ");
    }

    private void checkMail(Mail mail) {
        String content = mail.getContent().toLowerCase();
        for(String word : spamWords){
            if(content.contains(word)){
                mail.setSpam(true);
                break;
            }
        }
    }


    public void deleteMail(){
        System.out.print("Enter mail index to delete : ");
        int index = scanner.nextInt();
        if(index >= 0 && index < mails.size()){
            mails.remove(index);
            System.out.println("Mails deleted successfully");
        }else{
            System.out.println("Invalid mail index");
        }
    }

    public void addTag(){
        System.out.print("Enter mail index : ");
        int index = scanner.nextInt();
        if(index >= 0 && index < mails.size()){
            System.out.print("Enter the tag : ");
            String tag = scanner.nextLine();
            mails.get(index).addTag(tag);
            System.out.println("Tag added successfully.");
        }else{
            System.out.println("Invaild mail index");
        }
    }

    public void showStats(){
        System.out.println("\nMail Stats : ");
        System.out.println("\nTotal Mails " + mails.size());
        System.out.println("\nEnter the number of recent mails to display : ");
        int n = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nRecent " + n + " mails : ");
        mails.stream()
                .skip(Math.max(0, mails.size()-n))
                .forEach(System.out::println);
    }

    public void search() {
        System.out.println("Enter Search query : ");
        String query = scanner.nextLine().toLowerCase();

        List<Mail> results = mails.stream()
                .filter(mail -> mail.getSender().toLowerCase().contains(query) ||
                        mail.getReceiver().toLowerCase().contains(query) ||
                        mail.getSubject().toLowerCase().contains(query) ||
                        mail.getContent().toLowerCase().contains(query) ||
                        mail.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(query)))
                .collect(Collectors.toList());
        if(results.isEmpty()){
            System.out.println("No mails found matching the query");
        }else{
            System.out.println("\nSearch Results : ");
            results.forEach(System.out::println);
        }
    }

    public void wildCardSearch(){
        System.out.println("Enter wildCard pattern (use * for any characters) : ");
        final String pattern  = scanner.nextLine().toLowerCase().replace("*", ".*");

        List<Mail> results = mails.stream()
                .filter(mail -> mail.getSender().toLowerCase().matches(pattern) ||
                        mail.getReceiver().toLowerCase().matches(pattern) ||
                        mail.getSubject().toLowerCase().matches(pattern) ||
                        mail.getContent().toLowerCase().matches(pattern) ||
                        mail.getTags().stream().anyMatch(tag -> tag.toLowerCase().matches(pattern)))
                .collect(Collectors.toList());
        if(results.isEmpty()){
            System.out.println("No mails found matching the pattern");
        }else{
            System.out.println("\nSearch Results : ");
            results.forEach(System.out::println);
        }
    }


    public void displayMenu(){
        System.out.println("\nMail Management System");
        System.out.println("1. Store Mail");
        System.out.println("2. Delete Mail");
        System.out.println("3. Add tag");
        System.out.println("4 Show Stats");
        System.out.println("5. Search");
        System.out.println("6. Wildcard Search");
        System.out.println("7. Exit");
        System.out.print("Enter your choice : ");
    }

    public static void main(String[] args) {
        MailManagamentSystem system = new MailManagamentSystem();
        int choice;
        do{
            system.displayMenu();
            choice = system.scanner.nextInt();
            system.scanner.nextLine();

            switch (choice){
                case 1:
                    system.StoreMail();
                    break;
                case 2:
                    system.deleteMail();
                    break;
                case 3:
                    system.addTag();
                    break;
                case 4:
                    system.showStats();
                    break;
                case 5:
                    system.search();
                    break;
                case 6:
                    system.wildCardSearch();
                    break;
                case 7:
                    System.out.println("Exiting......");
            }
        }while (choice != 7);
        system.scanner.close();
    }
}
