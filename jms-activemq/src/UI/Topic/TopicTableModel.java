package UI.Topic;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ulric
 */
class TopicTableModel extends AbstractTableModel {
    private String[] columnNames = {"Nome do Tópico",
                                    "Deletar"
                                    };
    
    public ArrayList<TopicObject> topicList;
    
    public TopicTableModel(TopicObjectList list){
        list.readFromBroker();
        this.topicList = list.getTopics();
    }
    
    public ArrayList<TopicObject> getTopicList(){
        return topicList;
    } 
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        int size;
        if (topicList == null) {
           size = 0;
        }
        else {
           size = topicList.size();
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
                temp = topicList.get(row).getName();
                break;
            case 1:
                temp = topicList.get(row).getDeleteText();
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
        if (col < 1) {
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
                topicList.get(row).setName((String) value);
                break;
            case 1:
                topicList.get(row).setDeleteText((String) value);
                break;
            default:
                break;
        }
        fireTableCellUpdated(row, col);
    }

}
