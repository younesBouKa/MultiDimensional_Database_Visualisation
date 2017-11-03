package JtreeTable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
 
public class MyDataNode {
 
    public String name;
 
    public List<MyDataNode> children;
    public  List<Object> data_line;
    
    public MyDataNode(String name,List<Object>data, List<MyDataNode> children) {
        this.name = name;
        this.data_line=data;
        this.children = children;
 
        if (this.children == null) {
            this.children = Collections.emptyList();
        }
    }
 
    public String getName() {
        return name;
    }
 
    public Object getData(int col) {
        return data_line.get(col);
    }
 
    public List<MyDataNode> getChildren() {
        return children;
    }
 
    /**
     * Knotentext vom JTree.
     */
    public String toString() {
        return name;
    }
}