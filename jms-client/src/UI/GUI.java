package UI;

import Communication.Client;
import Communication.Message;
import UI.Topic.TopicScreen;
import UI.Queue.QueueScreen;
import java.awt.CardLayout;
import java.io.Serializable;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Ulric
 */
public class GUI implements Serializable{
        
    protected JFrame window;
    
    protected JPanel switchPanels; 
   
    public Socket socket;
    
    public Message message;
  
    public Client client;
    
    public QueueScreen queueScreen;
    public TopicScreen topicScreen;
    public SetupScreen setupScreen;
    
    protected final JTabbedPane tabbedPane;
   
    public GUI() {
        window = new JFrame("Cliente JMS");
       
        switchPanels = new JPanel(new CardLayout());
        
        setupScreen = new SetupScreen(this);
        switchPanels.add(this.setupScreen.panelJoin, "setup");
        
        tabbedPane = new JTabbedPane();
        
        queueScreen = new QueueScreen(this);
        
        topicScreen = new TopicScreen(this);
        
        
        tabbedPane.addTab("Tópicos", null, topicScreen.panelTopic,
                  "Operações com tópicos");
        tabbedPane.addTab("Filas", null, queueScreen.panelQueue,
                  "Operações com filas");
        
        
        switchPanels.add(this.tabbedPane, "main");
        
        window.add(switchPanels);
        window.setResizable(false); 
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true); 
    }

}
