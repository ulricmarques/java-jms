package UI.Queue;

import Operations.QueueCreator;
import Operations.RemoveQueue;
import UI.ButtonColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.jms.JMSException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JScrollPane;

/**
 *
 * @author Ulric
 */
public class QueueScreen implements ActionListener {
    
    public final JPanel panelQueue;
    
    private final JLabel labelQueueName;
    
    private final JButton runCreateQueue;

    protected JTextField inputQueueName;
    
    public JTable table;
    
    private final JButton runUpdateList;
    
    
    public QueueScreen () {
           
        panelQueue = new JPanel();
        panelQueue.setLayout(null); 
        panelQueue.setSize(800, 600);

        labelQueueName = new JLabel("Nome da fila:");
        labelQueueName.setBounds(20, 20, 100, 30);
        panelQueue.add(labelQueueName);

        inputQueueName = new JTextField();
        inputQueueName.setBounds(120, 20, 150, 30);
        inputQueueName.addActionListener(this);
        panelQueue.add(inputQueueName);
        
        runCreateQueue = new JButton("Criar");
        runCreateQueue.setBounds(280, 20, 80, 30);
        runCreateQueue.addActionListener(this);
                 
        panelQueue.add(runCreateQueue);
        
        runUpdateList = new JButton("Atualizar");
        runUpdateList.setBounds(500, 20, 120, 30);
        runUpdateList.addActionListener(this);
                 
        panelQueue.add(runUpdateList);
        
        table = new JTable(new QueueTableModel(new QueueObjectList()));
        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.setFillsViewportHeight(true);
        
        
        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 600, 400);

        //Add the scroll pane to this panel.
        panelQueue.add(scrollPane);
        
    }
    
    public void updateTableModel(){
        table.setModel(new QueueTableModel(new QueueObjectList()));
        ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
    }
    
    Action delete = new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                String queueName = ((QueueTableModel) table.getModel()).queueList.get(modelRow).getName();
                RemoveQueue removeQueue = new RemoveQueue(queueName);
                try {
                    removeQueue.execute();
                    updateTableModel();
                } catch (JMSException ex) {
                    System.out.println("Erro: " + ex);
                }

            }

        };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runCreateQueue && (inputQueueName.getText().length() < 1)) {
            JOptionPane.showMessageDialog(null, "Nome vazio.");
        }

        if (e.getSource() == runCreateQueue && inputQueueName.getText().length() > 0){
            String newQueueName = inputQueueName.getText();
            QueueCreator queueCreator = new QueueCreator(newQueueName);
            try {
                queueCreator.execute();
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
