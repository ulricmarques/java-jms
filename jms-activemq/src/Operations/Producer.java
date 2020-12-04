package Operations;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author ulric
 */
public class Producer
{

    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
     * 
     */	
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String queueName;
    
    private static String messageText;
    
    public Producer(String queueName, String messageText){
        this.queueName = queueName;
        this.messageText = messageText;
    }

    public void execute() throws JMSException
    {

        /*
         * Estabelecendo conexão com o Servidor JMS
         */		
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        /*
         * Criando Session 
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        /*
         * Criando Queue
         */
        Destination destination = session.createQueue(this.queueName);

        /*
         * Criando Produtor
         */		
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(this.messageText);

        /*
         * Enviando Mensagem
         */
        producer.send(message);

        System.out.println("Messagem: '" + message.getText() + ", Enviada para a Fila");

        producer.close();
        session.close();
        connection.close();
    }
}

