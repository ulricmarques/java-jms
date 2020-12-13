package UI.Topic;

import UI.ButtonColumn;
import UI.GUI;
import Communication.Message;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Ulric
 */
public class TopicScreen implements ActionListener {
    
    public GUI parentGUI;
    
    public final JPanel panelTopic;
    
    private final JButton runUpdateList;
    
    public JTable table;
    
    public ArrayList<TopicObject> topicList;
    
    public TopicScreen (GUI parentGUI) {
        
        topicList = new ArrayList<TopicObject>();
           
        this.parentGUI = parentGUI;
        panelTopic = new JPanel();
        panelTopic.setLayout(null); 
        panelTopic.setSize(800, 600);
        
        runUpdateList = new JButton("Atualizar");
        runUpdateList.setBounds(500, 20, 120, 30);
        runUpdateList.addActionListener(this);
                 
        panelTopic.add(runUpdateList);
        
        
        table = new JTable(new TopicTableModel(new TopicObjectList(topicList)));
        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);
        
        
        ButtonColumn buttonColumn = new ButtonColumn(table, subscribe, 1);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 600, 400);

        //Add the scroll pane to this panel.
        panelTopic.add(scrollPane);
        
    }
    
    public void updateTableModel(){
        
        table.setModel(new TopicTableModel(new TopicObjectList(topicList)));
        ButtonColumn buttonColumn = new ButtonColumn(table, subscribe, 1);
    }
    
    Action subscribe = new AbstractAction(){
        public void actionPerformed(ActionEvent e)
        {
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            String topicName = ((TopicTableModel) table.getModel()).topicList.get(modelRow).getName();
            Message msg = new Message();
            msg.setAction(Message.Action.SUBSCRIBE);
            msg.setContent(topicName);
            parentGUI.client.send(msg);
        }
    };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runUpdateList){
            
            updateTableModel();
        }
    }
}
