package OLAP_App;

import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class connect {
   
	boolean no_error=true;
	boolean alert_error=true;
	
   String url="jdbc:mysql://localhost:3306/";
   String user="root";
   String pass="";
   String db;
   Connection connection;
   
   ResultSetMetaData resultMeta;
   Object table_key;
   
   int nbr_lignes;
   int nbr_colonne;
   
   String titre_colonne[]; 
   String name_colonne[];
   String[] colonne_types;
   
   //ArrayList<Object> data=new ArrayList<Object>();
   Object data[][];
   ArrayList<String> les_tables;
   
   String message="";
   Color message_color=Color.BLACK;
   ArrayList<Object> numerique_colonne=new ArrayList<Object>();
   
   /****************************************************************************/
	public connect(String user,String pass,String db){
		          this.user=user;
		          this.pass=pass;
		          this.db=db;
		          les_tables=new ArrayList<String>();
		try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				try {
						
					url = "jdbc:mysql://localhost:3306/"+db;
				    connection =DriverManager.getConnection(url,user,pass);
				    //////////////////////////////////////////////////////////
				  //on r�cup�re les m�tadonn�es � partir de Connection 
					DatabaseMetaData dmd = connection.getMetaData(); 
					//r�cup�ration des informations 
					ResultSet tables = dmd.getTables(connection.getCatalog(),null,"%",null); 
					//affichage des informations 
					while(tables.next()){ 
					   for(int i=0; i<tables.getMetaData().getColumnCount();i++){ 
					      String nomColonne = tables.getMetaData().getColumnName(i+1); 
					      if(nomColonne.equalsIgnoreCase("TABLE_NAME")){
					      les_tables.add((String)tables.getObject(i+1)); 
					      //System.out.println(tables.getObject(i+1));
					      }
					   } 
					}
				    //////////////////////////////////////////////////////////
					no_error=true;
			        message=" Connection done to database : "+db+"\n";
			        message_color=Color.BLUE;
			        JOptionPane.showMessageDialog(null,message,"Connection ",JOptionPane.INFORMATION_MESSAGE, null);
			        
					} catch (SQLException ex) {
						// la connection a la base de donn�es n'a pas pu etre �tabli
						// voici les codes erreurs retourn�s
						message="SQLException: " + ex.getMessage()+"\n";
						message+="SQLState: " + ex.getSQLState()+"\n";
						message+="VendorError: " + ex.getErrorCode()+"\n";
						message_color=Color.RED;
						no_error=false;
						JOptionPane.showMessageDialog(null,message," Connection Error",JOptionPane.ERROR_MESSAGE);
					}
			} catch (Exception ex) {
				// Le driver n'a pas pu �tre charg�
				// v�rifier que la variable CLASSPATH est bien renseign�e
				message="Error while loading of driver \n ";
				message_color=Color.RED;
				no_error=false;
				JOptionPane.showMessageDialog(null,message ,"Driver Error",JOptionPane.ERROR_MESSAGE);
			   }
	}
	/********************************************************************************/
	public boolean deconnect(){
		if(connection!=null){
			try{
				connection.close();
				message="\n You are deconnected  ";
				message_color=Color.BLUE;
				no_error=true;
				return true;
			}catch(Exception e){
				message="\n"+e.getMessage();
				message_color=Color.RED;
				no_error=false;
				JOptionPane.showMessageDialog(null,message,"Connection Error ",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return false;
	}
	/********************************************************************************/
	public synchronized void Select(String requete){
		
			Statement st;
			data=null;
			titre_colonne=null;
			name_colonne=null;
			nbr_lignes=0;
			nbr_colonne=0;
			numerique_colonne=new ArrayList<Object>();
					
			try {
					st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet resultat=st.executeQuery(requete);
				
				 resultMeta = resultat.getMetaData();
				////////////////////////////////////////////
				
				/////////////////////////////////////////////
				// enregistrer les noms des colonnes
				nbr_colonne=resultMeta.getColumnCount();
				titre_colonne=new String[nbr_colonne];
				name_colonne=new String[nbr_colonne];
				
				for(int i=1;i<=resultMeta.getColumnCount();i++){
					titre_colonne[i-1]=resultMeta.getColumnName(i);
					name_colonne[i-1]=resultMeta.getTableName(i)+"."+resultMeta.getColumnName(i);
					if(resultat.getMetaData().isAutoIncrement(i)){
					    	 table_key=resultMeta.getColumnName(i);
					         System.out.println("la clef primaire de ce table "+resultMeta.getTableName(i)+" est "+table_key);    	 
					}
				}
				
				resultat.last(); 
				//on r�cup�re le num�ro de la ligne 
				 nbr_lignes = resultat.getRow(); 
				//on replace le curseur avant la premi�re ligne 
				resultat.beforeFirst(); 
				//System.out.println("Ce ResultSet contient "+nbr_lignes+"");
				
				
				// enregitsrer les types des colonnes
				colonne_types=new String[nbr_colonne];
				//System.out.println("/////////////////////////////");
				
				for(int j=1;j<=nbr_colonne;j++){
					 colonne_types[j-1]=resultMeta.getColumnTypeName(j);
					// System.out.println(resultMeta.getColumnName(j)+"  est de type "+resultMeta.getColumnTypeName(j));
					 if(resultMeta.getColumnTypeName(j).equalsIgnoreCase("BIGINT") || resultMeta.getColumnTypeName(j).equalsIgnoreCase("DOUBLE UNSIGNED") || resultMeta.getColumnTypeName(j).equalsIgnoreCase("INT") || resultMeta.getColumnTypeName(j).equalsIgnoreCase("FLOAT"))
						 numerique_colonne.add(resultMeta.getTableName(j)+"."+resultMeta.getColumnName(j));
					    
					}
				
				// enregistrer les donnees
				data=new Object[nbr_lignes][nbr_colonne];
				int i=0;
				while(resultat.next()){
					for(int j=1;j<=nbr_colonne;j++){
						//data.add(resultat.getObject(i));
						data[i][j-1]=resultat.getObject(j);
						
					}
					i++;
				}
				no_error=true;
				message="";
				message_color=Color.BLUE;
				
			} catch (SQLException e) {
				message="Error in the SQL request :"+e.getMessage()+"\n";
				message_color=Color.RED;
				no_error=false;
				if(requete.length()>0)
				  message+="\n This is your request : "+requete;
				if(alert_error)
				    JOptionPane.showMessageDialog(null,message,"Error in the SQL request ",JOptionPane.ERROR_MESSAGE);
			}
		
	}	
	/********************************************************************************/
	/********************************************************************************/
	public void Update(String requete){
			Statement st;
			int resultat=-55;
			try {
					st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					 resultat=st.executeUpdate(requete);
					 message="la requete SQL est bien effectuee "+resultat+" lignes etaient affectes \n";
				     message_color=Color.BLUE;
				     no_error=true;
				    
				     
			} catch (SQLException e) {
				message="Error in the SQL request :"+e.getMessage()+"\n";
				no_error=false;
				if(requete.length()>0)
					  message+="\n This is your request : "+requete;
				      message_color=Color.RED;
				JOptionPane.showMessageDialog(null,message,"Error in the SQL request ",JOptionPane.ERROR_MESSAGE);
			}
			
	}	
	/********************************************************************************/
	public  ArrayList getAllFunctions() throws SQLException{ 
		
		   ArrayList liste = new ArrayList(); 
		   DatabaseMetaData dmd = connection.getMetaData(); 
		   String[] tab = dmd.getStringFunctions().split(","); 
		   liste.addAll(Arrays.asList(tab)); 
		   tab = dmd.getNumericFunctions().split(","); 
		   liste.addAll(Arrays.asList(tab)); 
		   tab = dmd.getSystemFunctions().split(","); 
		   liste.addAll(Arrays.asList(tab)); 
		   tab = dmd.getTimeDateFunctions().split(","); 
		   liste.addAll(Arrays.asList(tab)); 
		   return liste; 
		   
		}
	/********************************************************************************/
	public JTable affiche_Resultat(){
		
		if(data!=null)
		   return new JTable(data,titre_colonne);
		else
		   return null;
	}
	/********************************************************************************/
	public JTable add_lines_names(Object lines_names[]){
		
		if(data!=null){
			String new_colonne_titre[]=new String[nbr_colonne+1];
			Object new_data[][]=new Object[nbr_lignes][nbr_colonne+1];
			int k=0;
			new_colonne_titre[0]="";
			for(int i=0;i<nbr_colonne;i++){
				new_colonne_titre[i+1]=titre_colonne[i];
			}
			
			for(int i=0;i<nbr_lignes;i++)
				new_data[i][0]=lines_names[i];
			
			System.out.println(nbr_lignes+"**********"+nbr_colonne);
			
			for(int i=0;i<nbr_lignes;i++)
				for(int j=0;j<nbr_colonne;j++){
					    new_data[i][j+1]=data[i][j];
				}
			return new JTable(new_data,new_colonne_titre);
		}
		else 
			return null;
		
	}
	
	
}
