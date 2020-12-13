package UI.Topic;

import Operations.RemoveQueue;
import Operations.RemoveTopic;
import Operations.TopicCreator;
import UI.ButtonColumn;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.jms.JMSException;
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
    
    public final JPanel panelTopic;
    
    private final JLabel labelTopicName;
    
    private final JButton runCreateTopic;

    protected JTextField inputTopicName;
    
    private final JButton runUpdateList;
    
    public JTable table;
    
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
        
        runUpdateList = new JButton("Atualizar");
        runUpdateList.setBounds(500, 20, 120, 30);
        runUpdateList.addActionListener(this);
                 
        panelTopic.add(runUpdateList);
        
        table = new JTable(new TopicTableModel(new TopicObjectList()));
        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);
        
        
        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 1);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 600, 400);

        //Add the scroll pane to this panel.
        panelTopic.add(scrollPane);
        

    }
    
    public void updateTableModel(){
        table.setModel(new TopicTableModel(new TopicObjectList()));
        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 1);
    }
    
    Action delete = new AbstractAction(){
        public void actionPerformed(ActionEvent e)
        {
            
            JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf( e.getActionCommand() );
            String topicName = ((TopicTableModel) table.getModel()).topicList.get(modelRow).getName();
            RemoveTopic removeTopic = new RemoveTopic(topicName);
            try {
                removeTopic.execute();
                updateTableModel();
            } catch (JMSException ex) {
                System.out.println("Erro: " + ex);
            }
        }
    };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runCreateTopic && (inputTopicName.getText().length() < 1)) {
            JOptionPane.showMessageDialog(null, "Nome vazio.");
        }

        if (e.getSource() == runCreateTopic && inputTopicName.getText().length() > 0){
            String newTopicName = inputTopicName.getText();
            TopicCreator topicCreator = new TopicCreator(newTopicName);
            try {
                topicCreator.execute();
                updateTableModel();
            } catch (JMSException ex) {
                System.out.println("Erro: " + ex);
            }
            
        }
        
        if (e.getSource() == runUpdateList){
            updateTableModel();
        }
    }
}
