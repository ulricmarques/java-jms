package UI;

import UI.Topic.TopicScreen;
import UI.Queue.QueueScreen;
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
    
    public QueueScreen queueScreen;
    public TopicScreen topicScreen;
    
    protected final JTabbedPane tabbedPane;
   
    public GUI(){
        window = new JFrame("JMS");
        
        tabbedPane = new JTabbedPane();
        
        queueScreen = new QueueScreen();
        topicScreen = new TopicScreen();
        
        tabbedPane.addTab("Filas", null, queueScreen.panelQueue,
                  "Operações com filas");
        tabbedPane.addTab("Tópicos", null, topicScreen.panelTopic,
                  "Operações com tópicos");
    
        window.add(tabbedPane);
        window.setResizable(false); 
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true); 
    }
  
}
