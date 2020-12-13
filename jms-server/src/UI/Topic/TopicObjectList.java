package UI.Topic;

import java.util.ArrayList;
import Operations.ListTopics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author ulric
 */
public class TopicObjectList {
    
   private ArrayList<TopicObject> topicList;
   
   public TopicObjectList() {
      topicList = new ArrayList<TopicObject>();
   }
   
   public void add(TopicObject qo) {
      topicList.add(qo);
   }
   
   public ArrayList<TopicObject> getTopics() {
      return topicList;
   }
   
   public void readFromBroker() {
      
        ListTopics listTopics = new ListTopics();
        try {
           topicList = listTopics.execute();
        } catch (JMSException ex) {
            Logger.getLogger(TopicScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
   }
}