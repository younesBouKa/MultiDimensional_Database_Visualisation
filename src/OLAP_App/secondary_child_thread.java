package OLAP_App;

import java.util.*;
import javax.swing.*;
import JtreeTable.*;

public class secondary_child_thread extends Thread {

   connect conn;
   List<MyDataNode> alldata;
   
   String close_where="";
   String table_globale;
   String function="sum";
   
   dimension mydimension;
   
   dimension father_dim;
   int father_dim_indice;
   
   ArrayList<dimension> all_dims=new ArrayList<dimension>();
   dimension dim_colonne;
   Object mesure;
   
   int dim_indice;
   
   String dim_name;
   
 /********************************************************************************************/
    public secondary_child_thread(List<MyDataNode> alldata,connect conn,ArrayList<dimension> all_dims,dimension dimcolonne,Object mesure,String table_globale,String function,String close_where,int dim_indice,int value_indice ){
     this.alldata=alldata;
     this.conn=conn;
     this.all_dims=all_dims;
     this.dim_colonne=dimcolonne;
     this.mesure=mesure;
   	 this.dim_indice=dim_indice;
   	 this.father_dim=all_dims.get(dim_indice-1);
     this.father_dim_indice=value_indice;
     this.function=function;
     this.table_globale=table_globale;
     this.close_where=close_where;
     
   	 if(dim_indice<all_dims.size())
   	     this.mydimension=all_dims.get(dim_indice);
   	 
    }
 /*********************************************************************************************/   
    public void run(){
   	 
       
       
       if(this.dim_indice+1<all_dims.size()){ // il exist d'autre dimensions
    	   
    	   secondary_child_thread dim1_child[]=new secondary_child_thread[mydimension.values.size()];
           System.out.println("you are in class secondary child with dimension  "+all_dims.get(dim_indice).name);
           
       	 for(int i=0;i< mydimension.values.size();i++){
	       		 synchronized(alldata){
	       			 List<MyDataNode> children1= new ArrayList<MyDataNode>();
	       			 String close=close_where+" and "+mydimension.name+mydimension.op+mydimension.d+mydimension.values.get(i)+mydimension.d+" ";
	       			 dim1_child[i]=new secondary_child_thread(children1,this.conn,this.all_dims,this.dim_colonne,this.mesure,table_globale,function,close,this.dim_indice+1,i);
	       		     dim1_child[i].start(); 
	       		  MyDataNode node=new MyDataNode(mydimension.values.get(i).toString(),null,children1);
	       		          alldata.add(node);
	       		}
	       		  
       		  }
    	 
       }else{                               // c'est le dernier dimension
    	    String requete="";
            List<Object> line_data=new ArrayList<Object>(); // une ligne de donnees
           Object value_ligne1,value_ligne2,value_colonne;
           List<MyDataNode> children1= new ArrayList<MyDataNode>();
    	   Object data[][];
    	   int nbr_value_null=0,nbr_colonne=dim_colonne.values.size();
    	   
    	     synchronized(conn){
		   		 for(int k=0;k<mydimension.values.size();k++){
				   			 
				   			 line_data=new ArrayList<Object>();
				   			 line_data.add(null);
				   			 value_ligne1=this.father_dim.d+this.father_dim.values.get(this.father_dim_indice)+this.father_dim.d;
				   			nbr_value_null=0;
				       		 for(int j=0;j<dim_colonne.values.size();j++){
						       				 value_ligne2=mydimension.d+mydimension.values.get(k)+mydimension.d;
						       				 value_colonne=dim_colonne.d+dim_colonne.values.get(j)+dim_colonne.d;
						       			
							       			 requete="Select "+this.function+"("+this.mesure+") from "+this.table_globale
							       			 		+ " where ("+this.father_dim.name +this.father_dim.op+ value_ligne1+" and "
							       			 				  +mydimension.name +mydimension.op+ value_ligne2+" and "
							       			 		          +dim_colonne.name+dim_colonne.op+value_colonne+""
							       			 		          +this.close_where+ ");";
							       			 
						       			 if(k==0 && j==0) System.out.println(requete); // exemple de requete
						       			 conn.Select(requete);
						       			 data=conn.data;
						       			 if(data!=null){
									       			     if(!(data[0][0]+"").equalsIgnoreCase("null")) {
									       			    	 line_data.add(" \t "+data[0][0]);
									       			    	 
									       			     }else{
									       			    	nbr_value_null++;
									       			    	line_data.add(" \t ** "); 
									       			     }
									       			    	
						       			     }
						
				       		     }
				       		 if(nbr_value_null!=nbr_colonne )
				       		        alldata.add(new MyDataNode(mydimension.values.get(k).toString(), line_data, null));
		       		 }
		   	
           }
       } // fin else
    	 
       }

  /********************************************************************************/
     
	
}
