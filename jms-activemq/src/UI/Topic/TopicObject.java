package UI.Topic;

/**
 *
 * @author ulric
 */
public class TopicObject {
    
    private String name;
    private String deleteText;
    
    public TopicObject(String name) {
       this.name = name;
       this.deleteText = "excluir";
    }
    
    public String getName() {
       return this.name;
    }
    
    
    public String getDeleteText() {
       return this.deleteText;
    }
    
    public void setName(String value){
        this.name = value;
    }
    
    public void setDeleteText(String value){
        this.deleteText = value;
    }
}
