package OLAP_App;

import java.util.ArrayList;

public class dimension {
	
         String name;
         String table;
         connect conn;
         
         String type;
         String klef_primaire;
         String fields[]=null; // les freres
         
         String op=" = ";
         String d=" ";
         
         ArrayList<Object> values=new ArrayList<Object>();
         
         public dimension(){
        	 
         }
         
         public dimension(connect c,String name){
        	 //System.out.println("vous etes dans la classe dimension ");
        	 this.name=name;
        	 conn=c;
        	 this.table=c.db+"."+name.substring(0, name.indexOf("."));
        	 fill();
         }
        
         public void fill(){
        	 System.out.println("you are in class dimension , remplir methode "+this.name);
           	 String requete="Select distinct("+name+") from "+table+"";
           	 System.out.println(requete);
           	 conn.Select(requete);
           	 
           	 type=conn.colonne_types[0];
           	 for(int i=0;i<conn.nbr_lignes;i++)
           	     values.add(conn.data[i][0]);
           	 
           	 System.out.println(this.values);
           	 
           	 /***********************************/
           	 conn.Select("select * from "+table);
           	 fields=conn.titre_colonne;
           	 
           	/************************************/
           	 if(this.type.equalsIgnoreCase("VARCHAR") || this.type.equalsIgnoreCase("Date")){
		           	  op=" like ";
		           	  d="'";
            }
           	 
         }
        
         
}
