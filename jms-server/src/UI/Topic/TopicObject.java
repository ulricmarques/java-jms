package UI.Topic;

import java.io.Serializable;

/**
 *
 * @author ulric
 */
public class TopicObject  implements Serializable{
    
    private String name;
    private String actionText;
    
    public TopicObject(String name) {
       this.name = name;
       this.actionText = "excluir";
    }
    
    public String getName() {
       return this.name;
    }
    
    
    public String getDeleteText() {
       return this.actionText;
    }
    
    public void setName(String value){
        this.name = value;
    }
    
    public void setDeleteText(String value){
        this.actionText = value;
    }
}
