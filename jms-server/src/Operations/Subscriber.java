package Operations;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

 
public class Subscriber implements MessageListener {
    
    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
    */	
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String topicName;
    
    public Subscriber(String topicName){
        this.topicName = topicName;
       
        //this.execute();
    }
    
    public void execute(){

        try {
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
             * Criando Topic
             */ 
            Destination dest = session.createTopic(this.topicName);

            /*
             * Criando Consumidor
             */
            MessageConsumer subscriber = session.createConsumer(dest);

            /*
             * Setando Listener
             */
            subscriber.setMessageListener(this);

            } 
            catch(JMSException e){
                System.out.println("Erro: " + e);
                
            }       
         }   

    @Override
    public void onMessage(Message message){
        if(message instanceof TextMessage){
            try{
                System.out.println( ((TextMessage)message).getText());
            }
            catch(JMSException e){
                System.out.println("Erro: " + e);
            }
        }
    }  
 }
