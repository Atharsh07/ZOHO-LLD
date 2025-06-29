package Mail_management_System;
import java.util.*;


public class Mail {

    private String sender;
    private String receiver;
    private String subject;
    private String content;
    private List<String> tags;
    private boolean isSpam;

    public Mail(String sender, String receiver, String subject, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.tags =  new ArrayList<>();
        this.isSpam = false;
    }

    public String getSender(){return sender;};
    public String getReceiver(){return  receiver;};
    public String getSubject(){return subject;};
    public String getContent(){return  content;};
    public List<String> getTags(){return tags;};
    public boolean isSpam(){return isSpam;};
    public void setSpam(boolean spam){
        isSpam = spam;
    }

    public void addTag(String tag){
        if(!tags.contains(tag)){
            tags.add(tag);
        }
    }
    public void removeTag(String tag){
        tags.remove(tag);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "From='" + sender + '\'' +
                ", To='" + receiver + '\'' +
                ", Subject='" + subject + '\'' +
                ", Content='" + content + '\'' +
                ", Tags=" + tags +
                ", Spam=" + isSpam +
                '}';
    }
}
