package JtreeTable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.TreeNode;
 
public class TreeTableMain extends JPanel {
	
	 public MyDataModel treeTableModel ;
     public String[] ColumnNames={"Line Dimension"};
     
     public Class<?>[] ColumnTypes;
     public List<MyDataNode> rootNodes = new ArrayList<MyDataNode>();
     public MyDataNode root;
     
	 /*************************************************************/
    public TreeTableMain(String[] colonne_names) {
             
    	if(colonne_names!=null){
    		ColumnNames=colonne_names;	
    	}
    	
        setLayout(new GridLayout(0, 1));
        treeTableModel = new MyDataModel(createDataStructure());
        
        treeTableModel.setColumnNames(ColumnNames);
        this.setDefaultColumnTypes();
        
        MyTreeTable myTreeTable = new MyTreeTable(treeTableModel);
        
        this.add(new JScrollPane(myTreeTable));
     
    }
   /******************************************************************/
   public void setColumnNames(String[] names){
	   treeTableModel.setColumnNames(names);
   }
   /******************************************************************/
   public void setColumnTypes(Class<?>[] types){
	   treeTableModel.setColumnTypes(types);
   }
   /********************************************************************/
   public void setDefaultColumnTypes(){
	   ColumnTypes=new Class<?>[ColumnNames.length];
	   ColumnTypes[0]= MyTreeTableModel.class;
	   for(int i=1;i<ColumnNames.length;i++)
		   ColumnTypes[i]=Object.class;
	   
	   treeTableModel.columnTypes=this.ColumnTypes;
   }
   /*******************************************************************/
   public  void addLigne(String line_name,List<Object> Linedata,List<MyDataNode> fils){
	   MyDataNode node=new MyDataNode(line_name,Linedata,fils);
	   rootNodes.add(node);
   }
   /*******************************************************************/
    private  MyDataNode createDataStructure() {
       
         root = new MyDataNode("All data", null, rootNodes);
        return root;
    }
 /*********************************************************************/
    public static void ain(final String[] args) {
      
       String names[]={"col1","col2","col3","col4","col5"};
       TreeTableMain pan=new TreeTableMain(null);
     
       List<MyDataNode> children1 = new ArrayList<MyDataNode>();
       List<Object> data = new ArrayList<Object>();
       data.add("c12");
       data.add("ll");
       data.add(15);
       data.add("coco");
       data.add("ll");
       data.add(15);
       data.add("coco");
       List<MyDataNode> children2 = new ArrayList<MyDataNode>();
       children2.add(new MyDataNode("N12",data, null));
       children2.add(new MyDataNode("N13",data, null));
       children2.add(new MyDataNode("N14",data, null));
       
       children1.add(new MyDataNode("N12",data, null));
       children1.add(new MyDataNode("N13",data, null));
       children1.add(new MyDataNode("N14",data, null));
       children1.add(new MyDataNode("N15",data,children2));
       pan.addLigne("younes",data,children1); // pour ajouter une ligne
      
        JFrame fen=new JFrame("JTabelTree");
       fen.setContentPane(pan);
       fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fen.setSize(800,500);
        fen.setVisible(true);
         
    }
    /********************************************************************/
}