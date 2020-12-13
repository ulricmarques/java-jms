package UI.Queue;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ulric
 */
class QueueTableModel extends AbstractTableModel {
    private String[] columnNames = {"Nome da Fila",
                                    "Nº mensagens",
                                    "Deletar"
                                    };
    
    public ArrayList<QueueObject> queueList;
    
    public QueueTableModel(QueueObjectList list){
        //list.readFromBroker();
        this.queueList = list.getQueues();
    }
    
    public ArrayList<QueueObject> getQueueList(){
        return queueList;
    } 
    

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        int size;
        if (queueList == null) {
           size = 0;
        }
        else {
           size = queueList.size();
        }
        return size;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Object temp = null;
        switch (col) {
            case 0:
                temp = queueList.get(row).getName();
                break;
            case 1:
                temp = queueList.get(row).getNumberOfMessages();
                break;
            case 2:
                temp = queueList.get(row).getDeleteText();
                break;
            default:
                break;
        }
        return temp;
    }
    
    /*
    * Don't need to implement this method unless your table's
    * editable.
    */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        switch (col) {
            case 0:
                queueList.get(row).setName((String) value);
                break;
            case 1:
                queueList.get(row).setNumberOfMessages((int) value);
                break;
            case 2:
                queueList.get(row).setDeleteText((String) value);
                break;
            default:
                break;
        }
        fireTableCellUpdated(row, col);
    }

}
