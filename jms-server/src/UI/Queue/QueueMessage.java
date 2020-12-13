package UI.Queue;

import java.io.Serializable;

/**
 *
 * @author ulric
 */
public class QueueMessage implements Serializable {
    
    private String queueName;
    private String text;
    
    public QueueMessage(String destination, String messageText) {
       this.queueName = destination;
       this.text = messageText;
    }
    
    public String getQueueName() {
       return this.queueName;
    }
    
    
    public String getText() {
       return this.text;
    }
    
    public void setQueueName(String value){
        this.queueName = value;
    }
    
    public void setText(String value){
        this.text = value;
    }
    
}
