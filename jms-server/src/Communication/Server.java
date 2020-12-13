package Communication;

import Communication.Message.Action;
import Operations.ListQueue;
import Operations.ListTopics;
import Operations.Producer;
import Operations.Publisher;
import Operations.QueueCreator;
import Operations.Subscriber;
import UI.Queue.QueueMessage;
import UI.Queue.QueueObject;
import UI.Queue.QueueScreen;
import UI.Topic.TopicMessage;
import UI.Topic.TopicObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author ulric
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    private int portNumber;

    
    public Server(int port) {
        this.portNumber = port;      
    }
    
    public boolean initializeServer(){
        try {
            serverSocket = new ServerSocket(this.portNumber);
            System.out.println("Servidor criado!");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
    
    
    @Override
    public void run() {
        this.startListener();
    }
    
    public void startListener(){
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            new Thread(new ListenerSocket(socket)).start();
        }
    }

    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream (socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            Message message = null;
            try {
                while ((message = (Message) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnected = connect(message, output);
                        if (isConnected) {
                            mapOnlines.put(message.getName(), output);
                            System.out.println("Cliente conectado: " + mapOnlines.size());
                            sendOnlines();
                        }
                        String newQueueName = message.getName();
                        QueueCreator queueCreator = new QueueCreator(newQueueName);
                        try {
                            queueCreator.execute();
                            //updateTableModel();
                        } catch (JMSException ex) {
                            System.out.println("Erro: " + ex);
                        }
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnect(message, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(message);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                    } else if (action.equals(Action.SEND_ONLINE)) {
                        QueueMessage queueMessage = (QueueMessage) message.getContent();
                        Producer producer = new Producer(queueMessage.getQueueName(), queueMessage.getText());
                        try{
                            producer.execute();
                        } catch (JMSException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Message msg = new Message();
                        msg.setAction(Action.SEND_ONLINE);
                        msg.setContent("OK");       
                        send(msg, output);
                        //TODO
                    } else if (action.equals(Action.SEND_OFFLINE)) {
                        //TODO
                    } else if (action.equals(Action.SEND_TOPIC)) {
                        TopicMessage topicMessage = (TopicMessage) message.getContent();
                        Publisher publisher = new Publisher(topicMessage.getTopicName(), topicMessage.getText());
                        try{
                            publisher.execute();
                        } catch (JMSException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Message msg = new Message();
                        msg.setAction(Action.SEND_TOPIC);
                        msg.setContent("OK");       
                        send(msg, output);
                        //TODO
                    } else if (action.equals(Action.USERS_ONLINE)) {
                        //TODO
                    } else if (action.equals(Action.LIST_QUEUES)) {
                        ListQueue listQueue = new ListQueue();
                        try {
                           ArrayList<QueueObject> tempList = listQueue.execute();
                           Message msg = new Message();
                           msg.setAction(Action.LIST_QUEUES);
                           msg.setContent(tempList);       
                           send(msg, output);
                        } catch (JMSException ex) {
                            Logger.getLogger(QueueScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        //TODO
                    } else if (action.equals(Action.LIST_TOPICS)) {
                        System.out.println("Recebeu action de listar topico");
                        ListTopics listTopics = new ListTopics();                       
                        try {
                           ArrayList<TopicObject> tempList = listTopics.execute();                     

                           Message msg = new Message();
                           msg.setAction(Action.LIST_TOPICS);
                           msg.setContent(tempList);       
                           send(msg, output);
                        }catch (JMSException ex) {
                            System.out.println("Erro: " + ex);
                        }
                        //TODO
 
                        //TODO
                    } else if (action.equals(Action.SUBSCRIBE)) {
                        String topicName = (String) message.getContent();
                        Subscriber subscriber = new Subscriber(topicName);
                        subscriber.execute(); 
                        Message msg = new Message();
                        msg.setAction(Action.SUBSCRIBE);
                        send(msg, output);
                    } else if (action.equals(Action.UNSUBSCRIBE)) {
                        //TODO
                    }
                }
            } catch (IOException ex) {
                Message msg = new Message();
                msg.setName(message.getName());
                disconnect(msg, output);
                sendOnlines();
                System.out.println(message.getName() + " deixou o chat!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean connect(Message message, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            message.setContent("YES");
            send(message, output);
            System.out.println("map size: " + mapOnlines.size());
            return true; 
        }
        

        if (mapOnlines.containsKey(message.getName())) {
            message.setContent("NO");
            send(message, output);
            return false;
        } else {
            message.setContent("YES");
            send(message, output);
            return true;
        }
        
        
    }

    private void disconnect(Message message, ObjectOutputStream output) {
        mapOnlines.remove(message.getName());

        message.setContent("até logo!");

        message.setAction(Action.SEND_ONLINE);

        sendAll(message);

        System.out.println("User " + message.getName() + " desconectou");
    }

    private void send(Object message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendOne(Message message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(message.getNameReserved())) {
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sendAll(Message message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sendOnlines() {
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }

        Message message = new Message();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setName(kv.getKey());
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
