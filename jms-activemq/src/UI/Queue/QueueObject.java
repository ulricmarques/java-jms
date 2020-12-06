package UI.Queue;

/**
 *
 * @author ulric
 */
public class QueueObject {
    
    private String name;
    private int numMessages ;
    private String deleteText;
    
    public QueueObject(String name, int numMessages) {
       this.name = name;
       this.numMessages = numMessages;
       this.deleteText = "excluir";
    }
    
    public String getName() {
       return this.name;
    }
    
    public int getNumberOfMessages() {
       return this.numMessages;
    }
    
    public String getDeleteText() {
       return this.deleteText;
    }
    
    public void setName(String value){
        this.name = value;
    }
    
    public void setNumberOfMessages(int value){
        this.numMessages = value;
    }
    
    public void setDeleteText(String value){
        this.deleteText = value;
    }
}
