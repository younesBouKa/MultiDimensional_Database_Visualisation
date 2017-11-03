package OLAP_App;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import JtreeTable.*;

public class first_child {
	
	     String name;
	     ArrayList<dimension> alldims=new ArrayList<dimension>();
	     
	     ArrayList<String> tables=new ArrayList<String>(); // la table de chaque dimension dans cette face

         dimension dim_colonne=new dimension();
         String function="sum";                            // fonction par default
         
         TreeTableMain alldata;
         
         
         String table_globale;                            // l'ensemble de tous les table
         connect conn;                                    // la connection
         Object mesure;                                  // la mesure a calculer
         
     
         String close_where="";
         String colonne_name[];

         
         
      /********************************************************************************************/
         public first_child(connect c,ArrayList<dimension> alldims,dimension dcolonne,Object mesure,String function){
        	// System.out.println("vous etes dans la classe face ");
        	 
        	 this.function=function;
        	 
        	 if(!tables.contains(dcolonne.table)){
        		 tables.add(dcolonne.table);
        	 }
        	 
        	 for(dimension dim:alldims){
        		  if(!tables.contains(dim.table))
        		       tables.add(dim.table);
        	 }
        	
        	 if(!tables.contains(mesure.toString().substring(0,mesure.toString().indexOf(".")))){
        		 tables.add(mesure.toString().substring(0,mesure.toString().indexOf(".")));
        	 }
        	 conn=c;
        	 
        	 this.alldims=alldims;

        	 dim_colonne=dcolonne;
        	 this.mesure=mesure;
        	 
        	 table_globale=tables.get(0);
        	 for(int i=1;i<tables.size();i++)
        		 table_globale+=","+tables.get(i);
        	 
        	 fill();
         }
      /*********************************************************************************************/   
         public void fill(){
        	 
        	 ////////////////////////////////////////////
        	 if(the_where_close(alldims,dim_colonne,mesure.toString())!=null)
        		 close_where=" and "+the_where_close(alldims,dim_colonne,mesure.toString());
        	 System.out.println("this is the where close : "+close_where);
        	 /////////////////////////////////////////////

             ///////////////////////////////////////////
        	 colonne_name=new String[dim_colonne.values.size()+1];  // nommer les colonnes
        	 colonne_name[0]="line_dimensions";
        	 for(int i=0;i<dim_colonne.values.size();i++)
        			 colonne_name[i+1]=dim_colonne.values.get(i).toString();
      
             //////////////////////////////////////////
        	 alldata=new TreeTableMain(colonne_name);         // construire l'arbre generale
        	 
        	 if(alldims.size()>1){ // plus que deux dimensions
		        	 secondary_child_thread dim1_child[]=new secondary_child_thread[alldims.get(0).values.size()];
		        	 
		            System.out.println("you are in the main child  "+alldims.get(0).name);
		            
		        	 for(int i=0;i<alldims.get(0).values.size();i++){
		        		 synchronized(alldata){
			        		 List<MyDataNode> children1= new ArrayList<MyDataNode>();
			        		 String close=close_where+" and "+alldims.get(0).name+alldims.get(0).op+alldims.get(0).d+alldims.get(0).values.get(i)+alldims.get(0).d+" ";
			        		  dim1_child[i]=new secondary_child_thread(children1,this.conn,this.alldims,this.dim_colonne,this.mesure,table_globale,function,close,1,i);
			        		  dim1_child[i].start();
			        		     alldata.addLigne(alldims.get(0).values.get(i).toString(),null,children1);
			        		     
		        		  }
		        	 }
        	 }else if(alldims.size()==1){            // seulement deux dimensions
        		 
        		 Object value_ligne1,value_colonne;
        	     dimension mydimension=alldims.get(0); 
        		 Object data[][];
        		 List<Object> line_data=new ArrayList<Object>(); // une ligne de donnees
        		 List<MyDataNode> children1= new ArrayList<MyDataNode>();
        		 String requete="";
        		 int nbr_value_null=0,nbr_colonne=dim_colonne.values.size();
        		 
        	     synchronized(conn){
    		   		 for(int k=0;k<mydimension.values.size();k++){
    				   			 
    				   			 line_data=new ArrayList<Object>();
    				   			 line_data.add(null);
    				   			 value_ligne1=mydimension.d+mydimension.values.get(k)+mydimension.d;
    				   			nbr_value_null=0;
    				       		 for(int j=0;j<dim_colonne.values.size();j++){
    				       			 
    						       				 value_colonne=dim_colonne.d+dim_colonne.values.get(j)+dim_colonne.d;
    							       			 requete="Select "+this.function+"("+this.mesure+") from "+this.table_globale
    							       			 		+ " where ( "
    							       			 				  +mydimension.name +mydimension.op+ value_ligne1+" and "
    							       			 		          +dim_colonne.name+dim_colonne.op+value_colonne+" "
    							       			 		          +this.close_where+ ");";
    							       			 
    						       			 //System.out.println(requete);
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
    				       		        children1.add(new MyDataNode(mydimension.values.get(k).toString(),line_data, null));
    		       		 }
    		   		
    		   		 alldata.addLigne(mydimension.name,null,children1);
               }
        		 
        	 }
        	 
         }
         
         /**********************************************************************************************************/
         public JPanel toTable(){
        	 return alldata;
     	}
         /**************************************************************************/
         public String commun_field(dimension dim1,dimension dim2){
        	 String str="";
        	   for(String sdim1:dim1.fields)
        		   for(String sdim2:dim2.fields)
        			   if(sdim1.equalsIgnoreCase(sdim2)){
        				   str+=" "+dim1.table+"."+sdim1+" = "+dim2.table+"."+sdim2;
        				   return str ;
        			   }
        	   
        	 return null;
         }
     	 /***************************************************************************/

    /********************************************************************************/
       public String the_where_close(ArrayList<dimension> all_dims,dimension dim_colonne,String mesure){
  	    
  	     ArrayList<dimension> dims=new ArrayList<dimension>();
  	     
  	     for(dimension di:all_dims)
  	    	 dims.add(di);
  	     
  	     dimension dim4=new dimension(conn,mesure);
  	     dims.add(dim4); // la mesure
  	     dims.add(dim_colonne); // la dimension de colonne
  	     
  	     ArrayList<String> all_str=new ArrayList<String>();
  	     
  	     
  	     for(int i=0;i<dims.size()-1;i++){
  	    	 for(int j=i+1;j<dims.size();j++){
  	    		if(commun_field(dims.get(i),dims.get(j))!=null)
  	   	    	     all_str.add(commun_field(dims.get(i),dims.get(j)));
  	    	 }
  	     }
  	     
  	     if(!all_str.isEmpty()){
  	    	 String str=all_str.get(0);
	  	     for(int i=1;i<all_str.size();i++)
	  	    	 str+=" and "+all_str.get(i);
	  	     
	  	     return str;
  	     }
  	     return null;
     }
    
       /********************************************************************************/
          



}
