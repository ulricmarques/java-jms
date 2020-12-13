
package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Communication.Message.Action;
import UI.GUI;
import UI.Topic.TopicObject;
import java.util.ArrayList;


/**
 *
 * @author Ulric
 */
public class Client {
    
    private Socket socket;
    public ObjectOutputStream output;
    public ObjectInputStream input;
    private String ip;
    private int portNumber;
    public String clientName;
    
    public GUI parentGUI;
    
    public Client(String host, int port, String name, GUI parentGUI ){
        this.parentGUI = parentGUI;
        this.ip = host;
        this.portNumber = port;
        this.clientName = name;
    }
    
    public boolean connect() {
        try {
            this.socket = new Socket(this.ip, this.portNumber);
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {          
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public void send(Message message) {
        try {
            output.writeObject(message);
            System.out.println("Enviou a mensagem: ");
        } catch (IOException ex) {
            System.out.println("Erro no send: " + ex);
        }
    }
    
    public void listTopics(Message message) {
        parentGUI.topicScreen.topicList = (ArrayList<TopicObject>) message.getContent();
    }
    
    public void startListener(){
       new Thread(new ListenerSocket(socket)).start();
    }
    
    private class ListenerSocket implements Runnable {

        public ListenerSocket(Socket socket) {
            try {
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            Message message = null;
            try {
                while ((message = (Message) input.readObject()) != null) {
                    Message.Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        System.out.println("Conectado.");
                    } else if (action.equals(Action.DISCONNECT)) {
//                        disconnected();
//                        socket.close();
                    } else if (action.equals(Action.SEND_ONLINE)) {
//                        System.out.println("::: " + message.getText() + " :::");
//                        receive(message);
                    } else if (action.equals(Action.SEND_OFFLINE)) {
                        //TODO
                    } else if (action.equals(Action.SEND_TOPIC)) {
                        //TODO
                    } else if (action.equals(Action.USERS_ONLINE)) {
//                        refreshOnlines(message);
                    } else if (action.equals(Action.LIST_QUEUES)) {
                        //TODO
                    } else if (action.equals(Action.LIST_TOPICS)) {
                        System.out.println("Recebeu lista do servidor: " + message.getContent().toString()); 
                        listTopics(message);
                        parentGUI.topicScreen.updateTableModel();
                        //TODO
                    } else if (action.equals(Action.SUBSCRIBE)) {
                        System.out.println("Client subscribed to topic");
                        ///TODO
                    } else if (action.equals(Action.UNSUBSCRIBE)) {
                        //TODO
                    } 
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}