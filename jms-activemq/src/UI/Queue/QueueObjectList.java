package UI.Queue;

import java.util.ArrayList;
import Operations.ListQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author ulric
 */
public class QueueObjectList {
    
   private ArrayList<QueueObject> queueList;
   
   public QueueObjectList() {
      queueList = new ArrayList<QueueObject>();
   }
   
   public void add(QueueObject qo) {
      queueList.add(qo);
   }
   
   public ArrayList<QueueObject> getQueues() {
      return queueList;
   }
   
   public void readFromBroker() {
      
        ListQueue listQueue = new ListQueue();
        try {
           queueList = listQueue.execute();
        } catch (JMSException ex) {
            Logger.getLogger(QueueScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
   }
}