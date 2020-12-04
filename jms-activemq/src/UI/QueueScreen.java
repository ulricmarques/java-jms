package UI;

import Operations.QueueCreator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ulric
 */
public class QueueScreen implements ActionListener {
    
    protected final JPanel panelQueue;
    
    private final JLabel labelQueueName;
    
    private final JButton runCreateQueue;

    protected JTextField inputQueueName;
    
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

    }
    
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
            } catch (JMSException ex) {
                System.out.println("Erro: " + ex);
            }
            
        }
    }
}
