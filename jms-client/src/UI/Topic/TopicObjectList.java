package UI.Topic;

import java.util.ArrayList;

/**
 *
 * @author ulric
 */
public class TopicObjectList {
    
   private ArrayList<TopicObject> topicList;
   
   public TopicObjectList(ArrayList<TopicObject> list) {
      topicList = list;
   }
   
   public void add(TopicObject qo) {
      topicList.add(qo);
   }
   
   public ArrayList<TopicObject> getTopics() {
      return topicList;
   }

}