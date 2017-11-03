package JtreeTable;
import java.util.Date;
import java.util.List;

public class MyDataModel extends MyAbstractTreeTableModel {
    // Spalten Name.
    static protected String[] columnNames;
 
    // Spalten Typen.
    static protected Class<?>[] columnTypes ;
   
   
    public MyDataModel(MyDataNode rootNode) {
        super(rootNode);
        root = rootNode;
    }
    
  public void setColumnNames(String[]columnNames2){
    	this.columnNames=columnNames2;
    }
    
    public void setColumnTypes(Class<?>[] types){
    	this.columnTypes=types;
    }
    
    public Object getChild(Object parent, int index) {
        return ((MyDataNode) parent).getChildren().get(index);
    }
 
 
    public int getChildCount(Object parent) {
        return ((MyDataNode) parent).getChildren().size();
    }
 
 
    public int getColumnCount() {
        return columnNames.length;
    }
 
 
    public String getColumnName(int column) {
        return columnNames[column];
    }
 
 
    public Class<?> getColumnClass(int column) {
        return columnTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
    	if(((MyDataNode) node).data_line!=null){
	        if( column<((MyDataNode) node).data_line.size()) {
	            return ((MyDataNode) node).getData(column);
	        }
	        return null;
        }
        return null;
    }
 
    public boolean isCellEditable(Object node, int column) {
        return true; // Important to activate TreeExpandListener
    }
 
    public void setValueAt(Object aValue, Object node, int column) {
    }


 
}