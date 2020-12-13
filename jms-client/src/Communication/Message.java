package Communication;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ulric
 */
public class Message implements Serializable {
    
    private String name;
    private Object content;
    private String nameReserved;
    private Set<String> setOnlines = new HashSet<String>();
    private Action action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
        
    public enum Action {
        CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, SEND_ONLINE, SEND_OFFLINE, SEND_TOPIC, 
        USERS_ONLINE, LIST_TOPICS, LIST_QUEUES, SUBSCRIBE, UNSUBSCRIBE
    }
}
