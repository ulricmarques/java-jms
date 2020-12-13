package Operations;

import UI.Queue.QueueObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;

/**
 *
 * @author ulric
 */
public class ListQueue{

    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
    */	

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private ArrayList<QueueObject> queueList;
   
    public ListQueue(){
       
    }

    public ArrayList<QueueObject> execute() throws JMSException
    {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();

        DestinationSource ds = connection.getDestinationSource();
        Set<ActiveMQQueue> queues = ds.getQueues();
         
        queueList = new ArrayList<>();
        
        for(ActiveMQQueue queue : queues){
            QueueBrowser queueBrowser = session.createBrowser(queue);
            
            Enumeration e = queueBrowser.getEnumeration();
            int numMsgs = 0;

            // count number of messages
            while (e.hasMoreElements()) {
                Message message = (Message) e.nextElement();
                numMsgs++;
            }
            
            QueueObject queueObject = new QueueObject(queue.getQueueName(), numMsgs);
            queueList.add(queueObject);
        }
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(ListTopics.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.close();
        connection.close();
        
        return queueList;

    }
   
}

