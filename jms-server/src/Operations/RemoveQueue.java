package Operations;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;


/**
 *
 * @author ulric
 */
public class RemoveQueue
{
    
    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
     * 
     */	
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String destinationName;
    
    public RemoveQueue(String destinationName) {
        this.destinationName = destinationName;
    }
    
    public void execute() throws JMSException{
        /*
         * Estabelecendo conexão com o Servidor JMS
         */		
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();

        /*
         * Criando Session 
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        /*
         * Criando Queue
         */
        Destination destination = session.createQueue(this.destinationName);
        
        connection.destroyDestination((ActiveMQDestination) destination);

        session.close();
        connection.close();
    }
        
}

