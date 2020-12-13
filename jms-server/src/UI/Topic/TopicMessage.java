package UI.Topic;

import java.io.Serializable;

/**
 *
 * @author ulric
 */
public class TopicMessage implements Serializable {
    
    private String topicName;
    private String text;
    
    public TopicMessage(String destination, String messageText) {
       this.topicName = destination;
       this.text = messageText;
    }
    
    public String getTopicName() {
       return this.topicName;
    }
    
    
    public String getText() {
       return this.text;
    }
    
    public void setTopicName(String value){
        this.topicName = value;
    }
    
    public void setText(String value){
        this.text = value;
    }
    
}
