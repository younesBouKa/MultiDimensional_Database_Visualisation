package OLAP_App;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class database_menager extends JPanel {
	
    ArrayList<String> saved_requete=new ArrayList<String>();
    
	JPanel connection_pan=new JPanel();
	String requete_type="select";
	String database;
	connect conn;
	
	/*********************************************************************************************************/
	   public  database_menager(main_window main){
	    	
		   
		   //this.setBackground(Color.GRAY);
		   
		   this.setLayout(null);
		  // this.setBackground(Color.BLUE);
		   //this.setBounds(0, 0, 0, 0);
		   /*******************************************************************/
		   
		   connection_pan.setLayout(null);
           connection_pan.setBounds(15, 10, 850, 100);
           connection_pan.setBorder(BorderFactory.createTitledBorder("Connection"));
		   
           JLabel host_label=new JLabel("Host");
           host_label.setBounds(10, 20, 100, 25);
           connection_pan.add(host_label);
           JTextField host=new JTextField("LocalHost");
           host.setBounds(90, 20, 200, 30);
           connection_pan.add(host);
           
           JLabel db_label=new JLabel("Data Base ");
           db_label.setBounds(10, 55, 100, 25);
           connection_pan.add(db_label);
           JTextField db=new JTextField("mydatabase");
           db.setBounds(90, 55, 200, 30);
           connection_pan.add(db);
           
           JLabel username_label=new JLabel("Username ");
           username_label.setBounds(320, 20, 100, 25);
           //username_label.setSize(100,25);
           connection_pan.add(username_label);
           JTextField username=new JTextField("root");
           username.setBounds(400, 20, 200, 30);
          // username.setSize(200, 30);
           connection_pan.add(username);
           
           JLabel password_label=new JLabel("Password ");
          password_label.setBounds(320, 55, 100, 25);
          // password_label.setSize(100,25);
           connection_pan.add(password_label);
           JPasswordField password=new JPasswordField("");
           password.setBounds(400, 55, 200, 30);
          // password.setSize(200,30);
           connection_pan.add(password);
           
           JLabel port_label=new JLabel("Port ");
           port_label.setBounds(610, 35, 60, 25);
           connection_pan.add(port_label);
           JTextField port=new JTextField("3356");
           port.setBounds(650, 35, 50, 30);
           connection_pan.add(port);
           
           JButton connecter=new JButton("Connect");
           connecter.setBounds(730, 25, 110, 50);
           //connecter.setBackground(Color.LIGHT_GRAY);
           connection_pan.add(connecter);
           
           JButton deconnecter=new JButton("Deconnect");
           deconnecter.setBounds(1110, 25,0,0);
           //deconnecter.setBackground(Color.LIGHT_GRAY);
           connection_pan.add(deconnecter);
           
           
           this.add(connection_pan);
           /********************************************************************************** la partie des requetes SQL *****/
           JPanel requete_pan=new JPanel();
           requete_pan.setLayout(null);
           requete_pan.setBounds(15, 120, 850, 150);
           requete_pan.setBorder(BorderFactory.createTitledBorder("SQL Request :"));
           
           JTextArea requete=new JTextArea();
           requete.setFont(new Font("Verdana",Font.BOLD,16));
           requete.setBackground(Color.LIGHT_GRAY);
           requete.setForeground(Color.WHITE);
           
           requete.setLineWrap(true);
           
           requete.setEditable(false);
           JScrollPane sc_requete=new JScrollPane(requete);
        		   sc_requete.setBounds(5, 15, 840, 130);
           requete_pan.add(sc_requete);
           
           this.add(requete_pan);
           
           
           JPanel button_pan=new JPanel();
           //button_pan.setLayout(null);
           button_pan.setBounds(15, 270, 850, 35);
          // button_pan.setBorder(BorderFactory.createTitledBorder("Requete SQL :"));
          JButton execut_butt=new JButton("Execut");
          button_pan.add(execut_butt);
          execut_butt.setBackground(Color.GREEN);
          JButton select_butt=new JButton("Select");
          button_pan.add(select_butt);
          JButton update_butt=new JButton("Update");
          button_pan.add(update_butt);
          JButton insert_butt=new JButton("Insert");
          button_pan.add(insert_butt);
          JButton delete_butt=new JButton("Delete");
          button_pan.add(delete_butt);
          JButton alter_butt=new JButton("Alter");
          button_pan.add(alter_butt);
          JButton vider_butt=new JButton("Clear");
          button_pan.add(vider_butt);
          vider_butt.setBackground(Color.GREEN);
          JButton save_butt=new JButton("Save");
          button_pan.add(save_butt);
          save_butt.setBackground(Color.GREEN);
          
           this.add(button_pan);
           /********************************************************************************************************************/
           JPanel resultat=new JPanel();
           resultat.setLayout(null);
           resultat.setBounds(15,310,850,240);
           resultat.setBorder(BorderFactory.createTitledBorder("Result of request "));
    	   
           this.add(resultat);
           /************************************************************************************** la partie d'erreures ********/
           JTextArea erreur=new JTextArea("");
           
           erreur.setEditable(false);
           erreur.setLineWrap(true);
           erreur.setBounds(10,15, 845, 80);
           erreur.setEditable(false);
           erreur.setBackground(Color.LIGHT_GRAY);
           //erreur.setBorder(BorderFactory.createTitledBorder("Messages "));
  		 
           JScrollPane s2=new JScrollPane(erreur);
           s2.setBounds(10,550,870,80);
           
           this.add(s2);
           /******************************************************************************************** les evenements *******/
           s2.addMouseListener(new MouseAdapter(){
   			public void mouseClicked(MouseEvent arg0) {
   				if(s2.getHeight()==100)
   					s2.setBounds(10,630,870,20); // minimiser le jpannel des erreures
   				else
   					s2.setBounds(10,550,870,100); // maximiser le jpannel des erreures
   			}
              });
           
           // l'evenement de boutton connecter
           connecter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String user=username.getText();
				String pass=password.getText();
				String dbase=db.getText();
				String server=host.getText();
               if(dbase.length()>1){
									main.conn=new connect(user,pass,dbase);
									
									erreur.append("\n"+main.conn.message);
									erreur.setForeground(main.conn.message_color);
									if(main.conn!=null && main.conn.no_error){  // desactiver les fields text
										requete.setEditable(true);
									    username.setEditable(false);
									    password.setEditable(false);
									    db.setEditable(false);
									    host.setEditable(false);
									    port.setEditable(false);
									    main.onglet.setEnabledAt(2,true); // pour lacher le pannel  de cube
									    
									    connecter.setBounds(5000, 0,0, 0);
									    deconnecter.setBounds(730, 25, 110, 50);
									    
									    conn=main.conn;
									    database=dbase;
									    
									    main.cube.table_list.removeAll();
									    main.cube.table_list.setListData(main.conn.les_tables.toArray());
									}
				}else {
					JOptionPane.showMessageDialog(null,"You dont have entred the database name " ,"Connection Error  ",JOptionPane.ERROR_MESSAGE);
				}
               
			}
           });
           
           // l'evenement de boutton deconnecter
           deconnecter.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {

   				if(main.conn!=null){
   					requete.setEditable(true);
   				    username.setEditable(true);
   				    password.setEditable(true);
   				    db.setEditable(true);
   				    host.setEditable(true);
   				    port.setEditable(true);
   				    
   				    requete.setText("");
   				    resultat.removeAll();
   				    deconnecter.setBounds(5000, 0,0, 0);
   				    connecter.setBounds(730, 25, 110, 50);
   				    
   				    main.conn.deconnect();
   				 erreur.append("\n"+main.conn.message);
 				 erreur.setForeground(main.conn.message_color);
 				 main.conn=null;
 				 conn=null;
 				main.onglet.setEnabledAt(2,false); // pour blocker le pannel  de cube
   				}
   			}
              });
// les evenements des boutton de requete
           
           select_butt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				requete.setText("  Select * From [table] Where [condition] ; ");
				requete_type="select";
			}
           });
           
           update_butt.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				requete.setText("  Update [table]  set [attribute1]=[value1],[attribute2]=[value2],[attribute3]=[value3] Where [condition] ; ");
   				requete_type="update";
   			}
              });
           
           insert_butt.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				requete.setText("  Insert into  [table]([col1],[col2],[col3],[col4]) values ([att1],[att2],[att3],[att4]) ; ");
   				requete_type="update";
   			}
              });
           
           delete_butt.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				requete.setText(" Delete  From [table] Where [condition] ; ");
   				requete_type="update";
   			}
              });
           
           alter_butt.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				requete.setText(" Alter [table] ");
   				requete_type="update";
   			}
              });
           
           vider_butt.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				requete.setText("");
   			}
              });
           
           save_butt.addActionListener(new ActionListener(){
      			public void actionPerformed(ActionEvent arg0) {
      				if(requete.getText()!=null)
      					saved_requete.add(requete.getText());
      			}
                 });
           
           execut_butt.addActionListener(new ActionListener(){ 
   			public void actionPerformed(ActionEvent arg0) {
   				 if(main.conn!=null){
   					 if(requete_type.equalsIgnoreCase("select")){
   						 main.conn.alert_error=true;
   						 main.conn.Select(requete.getText());
   					     erreur.append("\n"+main.conn.message);
   					     erreur.setForeground(main.conn.message_color);
   					     if(main.conn.data!=null && main.conn.titre_colonne!=null){
	   					     JScrollPane jsp=new JScrollPane(new JTable(main.conn.data,main.conn.titre_colonne));
	   					     jsp.setBounds(10, 20, 830, 210);
	   					     resultat.removeAll();
	   			             resultat.add(jsp);
   					     }
   					 }
   					 else if(requete_type.equalsIgnoreCase("update")){
   						 main.conn.Update(requete.getText());
   						 erreur.append("\n"+main.conn.message);
  					     erreur.setForeground(main.conn.message_color);
   					 }
   					/* else {
   						 
  						 erreur.setText("Veuillez choisir le type de requete, ou bien clicker 'Select' ");
 					     erreur.setForeground(Color.ORANGE);
   					 }*/
   				 }
   			}
              });
           
           
           /***************************************************************************************************/
	    }
	 /********************************************************************************************************/
}
