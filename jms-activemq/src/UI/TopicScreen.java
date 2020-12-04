package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ulric
 */
public class TopicScreen implements ActionListener {
    
    protected final JPanel panelTopic;
    
    private final JLabel labelTopicName;
    
    private final JButton runCreateTopic;

    protected JTextField inputTopicName;
    
    public TopicScreen () {
           
        panelTopic = new JPanel();
        panelTopic.setLayout(null); 
        panelTopic.setSize(800, 600);

        labelTopicName = new JLabel("Nome do tópico:");
        labelTopicName.setBounds(20, 20, 1200, 30);
        panelTopic.add(labelTopicName);

        inputTopicName = new JTextField();
        inputTopicName.setBounds(140, 20, 150, 30);
        inputTopicName.addActionListener(this);
        panelTopic.add(inputTopicName);
        
        runCreateTopic = new JButton("Criar");
        runCreateTopic.setBounds(300, 20, 80, 30);
        runCreateTopic.addActionListener(this);
                 
        panelTopic.add(runCreateTopic);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
          
    }
}
