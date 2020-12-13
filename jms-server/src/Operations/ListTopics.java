package Operations;

import UI.Topic.TopicObject;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQTopic;

/**
 *
 * @author ulric
 */
public class ListTopics{

    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
    */	
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private ArrayList<TopicObject> topicList;
   
    public ListTopics(){
       
    }

    public ArrayList<TopicObject> execute() throws JMSException
    {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        
        connection.start();

        DestinationSource ds = connection.getDestinationSource();
        Set<ActiveMQTopic> topics = ds.getTopics();
        
       
        topicList = new ArrayList<>();
        
        for(ActiveMQTopic topic : topics){
            TopicObject topicObject = new TopicObject(topic.getTopicName());
            topicList.add(topicObject);
        }
        
       
        connection.close();
        
        return topicList;

    }
   
}

