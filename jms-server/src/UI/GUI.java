package UI;

import Communication.Server;
import UI.Topic.TopicScreen;
import UI.Queue.QueueScreen;
import java.awt.CardLayout;
import java.io.Serializable;
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
   
    public Server server;
    
    public QueueScreen queueScreen;
    public TopicScreen topicScreen;
    public SetupScreen setupScreen;
    
    protected final JTabbedPane tabbedPane;
   
    public GUI() {
        window = new JFrame("Servidor JMS");
        
        switchPanels = new JPanel(new CardLayout());
        
        setupScreen = new SetupScreen(this);
        switchPanels.add(this.setupScreen.panelHost, "setup");
        
        tabbedPane = new JTabbedPane();
        
        queueScreen = new QueueScreen();
        topicScreen = new TopicScreen();
        
        tabbedPane.addTab("Filas", null, queueScreen.panelQueue,
                  "Operações com filas");
        tabbedPane.addTab("Tópicos", null, topicScreen.panelTopic,
                  "Operações com tópicos");
        
        switchPanels.add(this.tabbedPane, "main");
        
        window.add(switchPanels);
        window.setResizable(false); 
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true); 
    }

}
