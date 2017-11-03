package OLAP_App;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class cube_menager extends JPanel {
	
	   
       public cube_menager it_me=this;
       
	    ArrayList<Object> selected_ligne_dims=new ArrayList<Object>();
	    ArrayList<Object> selected_col_dims=new ArrayList<Object>();
	    ArrayList<Object> selected_mesures=new ArrayList<Object>();
	    
	    JTextField cube_name=new JTextField("Cube name");
	    JComboBox<Object> fait_mesure_list=new JComboBox<Object>();
	    JList<Object> fait_dimligne_list=new JList<Object>();
	    JList<Object> fait_dimcol_list=new JList<Object>();
	    
	    JList<Object> table_list=new JList<Object>(); // les posibles
	    JList<Object> dimension_list=new JList<Object>();// les posibles
	    JList<Object> mesure_list=new JList<Object>();// les posibles
	    
        
        String fonction_supportes[]={"max","min","sum","count","AVG"};
        JComboBox<String> function_list=new JComboBox<String>(fonction_supportes);
        
        //JPanel show_pan=new JPanel();
        JTextArea erreur=new JTextArea("");
        
	/**********************************************************************************************************/
	/*********************************************************************************************************/
	   public  cube_menager(main_window main){
		   
		   //this.setBackground(Color.GRAY);
	    	
		   erreur.setEditable(false);
		 /*  selected_dimensions.add("");
		   selected_mesures.add("");*/
		   
		   this.setLayout(null);
		   this.setFont(new Font("Verdana",Font.ITALIC,25));
		   
		   /*******************************************************************/
		   JPanel haut_pan=new JPanel();
		   haut_pan.setBounds(10, 10, 870, 440);
		   haut_pan.setLayout(null);
		   haut_pan.setBorder(BorderFactory.createTitledBorder("Cube Construction"));
		   this.add(haut_pan);
		   
		   JPanel fait_pan=new JPanel();
		   fait_pan.setLayout(null);
		   fait_pan.setBounds(5, 20, 250,410);
		   //fait_pan.setBackground(Color.CYAN);
           fait_pan.setBorder(BorderFactory.createTitledBorder("Cube"));
		   
           
           /*cube_name.setBounds(20, 15, 220, 25);
           fait_pan.add(cube_name);
           */
           int ligne=50;
           /************************ dimensions en ligne *****************/
           JLabel dimligne_label=new JLabel("Line dimensions");
           dimligne_label.setBounds(10, 25, 200, 25);
           fait_pan.add(dimligne_label);
           
           JScrollPane dimligne_scp=new JScrollPane(fait_dimligne_list);
           dimligne_scp.setBounds(20, 55, 200,100);
           fait_pan.add(dimligne_scp);

           /************************ dimension en colonne *****************/
           JLabel dimcol_label=new JLabel("Column dimensions");
           dimcol_label.setBounds(10, 160, 200, 25);
           fait_pan.add(dimcol_label);
           
           JScrollPane dimcol_scp=new JScrollPane(fait_dimcol_list);
           dimcol_scp.setBounds(20, 185, 200, 100);
           fait_pan.add(dimcol_scp);
           
           /************************ les mesures *****************/
            JLabel mesure_label=new JLabel("Mesures ");
           mesure_label.setBounds(10, 290, 200, 25);
           fait_pan.add(mesure_label);
           
           fait_mesure_list.setBounds(20, 310, 200, 30);
           fait_pan.add(fait_mesure_list);
           
           /************************ les fonctions *****************/
           JLabel function_label=new JLabel("Functions");
           function_label.setBounds(10, 350, 120, 20);
           fait_pan.add(function_label);
           
           function_list.setBounds(20, 370, 200, 30);
           fait_pan.add(function_list);
           function_list.setSelectedIndex(0);
           
           haut_pan.add(fait_pan);
           /*******************************************************************/
           JPanel table_pan=new JPanel();
		   table_pan.setLayout(null);
           table_pan.setBounds(260, 20,210, 255);
           table_pan.setBorder(BorderFactory.createTitledBorder("Database Tables"));
		   
           JScrollPane tablescp=new JScrollPane(table_list);
           tablescp.setBounds(5, 17, 200, 235);
           table_pan.add(tablescp);
            
           haut_pan.add(table_pan);
           
		   /*******************************************************************/
		   JPanel dimension_pan=new JPanel();
		   dimension_pan.setLayout(null);
		   dimension_pan.setBounds(480, 20, 180, 255);
		   //dimension_pan.setBackground(Color.GRAY);
           dimension_pan.setBorder(BorderFactory.createTitledBorder("Possibls Dimensions "));
		  
         /*  DropTarget dt=new DropTarget();
		   dt.setComponent(row1_list);
           dimension_list.setDragEnabled(true);
           dimension_list.setDropTarget(dt);
          */
           JScrollPane scp4=new JScrollPane(dimension_list);
           scp4.setBounds(5, 17, 170, 235);
           dimension_pan.add(scp4);
           haut_pan.add(dimension_pan);
		   /*******************************************************************/
		   JPanel mesure_pan=new JPanel();
		   mesure_pan.setLayout(null);
		   mesure_pan.setBounds(670, 20, 180, 255);
		   //mesure_pan.setBackground(Color.GREEN);
           mesure_pan.setBorder(BorderFactory.createTitledBorder("Possibls Mesures "));
		   
           
           JScrollPane scp5=new JScrollPane(mesure_list);
           scp5.setBounds(5, 17, 170, 235);
           mesure_pan.add(scp5);
           haut_pan.add(mesure_pan);
           /*******************************************************************/
           JPanel face_pan=new JPanel();
           face_pan.setLayout(null);
           face_pan.setBounds(260,280,590,150);
           face_pan.setBorder(BorderFactory.createTitledBorder("Execution"));
		   
           
           JButton reset=new JButton("RESET");
           reset.setBackground(Color.LIGHT_GRAY);
           reset.setBounds(200,60, 100, 40);
           face_pan.add(reset);
           
           JButton start=new JButton("START");
           start.setBackground(Color.GRAY.brighter());
           start.setBounds(340,60, 100, 40);
           face_pan.add(start);
           
           haut_pan.add(face_pan);
		   /*******************************************************************/
            JPopupMenu add_to=new JPopupMenu();
            JMenuItem add_to_colonne=new JMenuItem("Add as Column dimension");
            JMenuItem add_to_ligne=new JMenuItem("Add as line dimension");
            
            add_to.add(add_to_ligne);
            add_to.add(add_to_colonne);
            
            /////////////
            JPopupMenu remove_ligne=new JPopupMenu();
            JMenuItem del_ligne=new JMenuItem("Remove");
            remove_ligne.add(del_ligne);
            
            JPopupMenu remove_col=new JPopupMenu();
            JMenuItem del_col=new JMenuItem("Remove");
            remove_col.add(del_col);
            
            JPopupMenu remove_mesure=new JPopupMenu();
            JMenuItem del_mesure=new JMenuItem("Remove");
            remove_mesure.add(del_mesure);
            
           /*******************************************************************/
           JPanel error_pan=new JPanel();
           error_pan.setLayout(null);
           //error_pan.setBorder(BorderFactory.createTitledBorder("Messages "));
		   
         
           erreur.setBackground(Color.LIGHT_GRAY);
           erreur.setLineWrap(true);
           JScrollPane s2=new JScrollPane(erreur);
           s2.setBounds(10,20, 850, 120);
           
           error_pan.add(s2); 
           
           error_pan.setBounds(10,460,870,160);
           this.add(error_pan);
           /************************************************************************** les evenements ****************************************/
           
           // l'evenement de selection de table 
           table_list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if(table_list.getSelectedIndex()>=0 && main.conn!=null){
							String requete="select * from "+table_list.getSelectedValue();
							main.conn.Select(requete);
							dimension_list.setListData(main.conn.name_colonne);
							mesure_list.setListData(main.conn.numerique_colonne.toArray());
						}
						else if(main.conn==null){
							JOptionPane.showMessageDialog(main,"You are not connected ! ","Error",JOptionPane.ERROR_MESSAGE);
							main.onglet.setSelectedIndex(1);
					
						}
			}   
           });
           // l'evenement de clicke sur une dimension possible
           dimension_list.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent event) {
				if(dimension_list.getSelectedIndex()>-1 && main.conn!=null && event.isPopupTrigger()){
					add_to.show(dimension_list,event.getX(),event.getY());
   					
   				}
			}   
              });
           // add diemsnion to line dimensions
           add_to_ligne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			  if(!selected_ligne_dims.contains(dimension_list.getSelectedValue())){
				  selected_ligne_dims.add(dimension_list.getSelectedValue());
				  fait_dimligne_list.setListData(selected_ligne_dims.toArray());
			  }
				
			}   
           });
           // add diemsnion to column dimensions
           add_to_colonne.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent arg0) {
   				if(!selected_col_dims.contains(dimension_list.getSelectedValue())){
  				  selected_col_dims.add(dimension_list.getSelectedValue());
  				  fait_dimcol_list.setListData(selected_col_dims.toArray());
  			  }
   				
   			}   
              });
           // l'evenement de clicke sur une mesure possible
           mesure_list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				if(mesure_list.getSelectedIndex()>-1 && main.conn!=null && !selected_mesures.contains(mesure_list.getSelectedValue())){
					selected_mesures.add(mesure_list.getSelectedValue());
					fait_mesure_list.addItem(mesure_list.getSelectedValue());
				}
			  }   
             });
           
           // supprimer une dimension de ligne
           fait_dimligne_list.addMouseListener(new MouseAdapter(){
   			public void mouseReleased(MouseEvent event) {
   				if(fait_dimligne_list.getSelectedIndex()>-1 && main.conn!=null && event.isPopupTrigger()){
   					remove_ligne.show(fait_dimligne_list,event.getX(),event.getY());
			     }
   			}
           });
           
           // supprimer la dimension de colonne
           fait_dimcol_list.addMouseListener(new MouseAdapter(){
      			public void mouseReleased(MouseEvent event) {
      				if(fait_dimcol_list.getSelectedIndex()>-1 && main.conn!=null && event.isPopupTrigger()){
      					remove_col.show(fait_dimcol_list,event.getX(),event.getY());
   			        }  
      			}
              });
           // supprimer une mesure
           fait_mesure_list.addMouseListener(new MouseAdapter(){
     			public void mouseReleased(MouseEvent event) {
     				if(fait_mesure_list.getSelectedIndex()>-1 && main.conn!=null && event.isPopupTrigger()){
     					remove_mesure.show(fait_mesure_list,event.getX(),event.getY());
  			        }  
     			}
             });
          
           // supprimer une ligne
           del_ligne.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent event) {

   				 if(fait_dimligne_list.getSelectedIndex()>-1){
   					  selected_ligne_dims.remove(fait_dimligne_list.getSelectedValue());
				      fait_dimligne_list.setListData(selected_ligne_dims.toArray());
   				  }
   				  
   			 }  
            });
           // supprimer une mesure
           del_col.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent event) {

   				 if(fait_dimcol_list.getSelectedIndex()>-1){
   					  selected_col_dims.remove(fait_dimcol_list.getSelectedValue());
				      fait_dimcol_list.setListData(selected_col_dims.toArray());
   				  }
   				 
   			 }  
            });
           
        // supprimer une mesure
           del_mesure.addActionListener(new ActionListener(){
   			public void actionPerformed(ActionEvent event) {

   				 if(fait_mesure_list.getSelectedIndex()>-1){
   					  selected_mesures.remove(fait_mesure_list.getSelectedItem());
				      fait_mesure_list.removeItem(fait_mesure_list.getSelectedItem());
   				  }
   				 
   			 }  
            });
           
           
          ///////////////////////////////////////////////////////////////////////////////////////////////////////
           start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(main.conn!=null && selected_ligne_dims.size()>0 && selected_col_dims.size()>0 && selected_mesures.size()>0){
						 /////////////////////////////////////////////////////////////
					try{
						 String dimcolonne_name=(String)((fait_dimcol_list.getSelectedIndex()!=-1)?fait_dimcol_list.getSelectedValue():selected_col_dims.get(0));
						
						 Object mesure=(fait_mesure_list.getSelectedIndex()!=-1)?fait_mesure_list.getSelectedItem():fait_mesure_list.getItemAt(0);
						 dimension dcolumn=new dimension(main.conn,dimcolonne_name);
						
						 String dimname;
						 ArrayList<dimension> alldims=new ArrayList<dimension>();
						 int nbr_requete_istime=1;
						 
						 for(int i=0;i<selected_ligne_dims.size();i++){
							 dimname=(String)(selected_ligne_dims.get(i));
							 dimension d=new dimension(main.conn,dimname);
							 if(!d.name.equalsIgnoreCase(dcolumn.name)){
							           alldims.add(d);
							           nbr_requete_istime*=d.values.size();
							           }
						 }
						 nbr_requete_istime*=dcolumn.values.size();
						 int test=-9;
						 
						 if(alldims.size()>4){
							  test=JOptionPane.showConfirmDialog(null,"You hace select "+alldims.size()+" dimensions \n "
							 			+ "\t This cube will generat "+nbr_requete_istime+" requests \n"
							 			+ " \t Do you want continue ? ","Informatif message",JOptionPane.YES_NO_OPTION);
				
						 }
						 
						 if(test==-9 || test==0){
								 String function=(String) function_list.getSelectedItem();
								 main.conn.alert_error=false;
								 first_child f=new first_child(main.conn, alldims, dcolumn, mesure, function); // face utilise les threads
								 
								 main.onglet.setComponentAt(3,f.toTable());
							      main.onglet.setSelectedIndex(3);
							      main.onglet.setEnabledAt(3, true);
							        
							      erreur.append("\n"+main.conn.message);
							      erreur.setForeground(main.conn.message_color);
						 }
						 
						 /////////////////////////////////////////////////////////////
						 
					}catch(Exception e){
						erreur.append("\n"+e.getMessage());
					    erreur.setForeground(Color.RED);
					}
				}
				else if(main.conn==null){ 
					erreur.append("\n"+" You must be connected to database");
				    erreur.setForeground(Color.RED);
				    JOptionPane.showMessageDialog(null," You must be connected to database  ","Error ",JOptionPane.ERROR_MESSAGE);
				    main.onglet.setSelectedIndex(1);
				    main.onglet.setEnabledAt(3,false);
				    main.onglet.setEnabledAt(2,false);
				    
				}else if(selected_ligne_dims.size()==0){
					erreur.append("\n"+" You dont have select any dimension in line ");
				    erreur.setForeground(Color.RED);
				    JOptionPane.showMessageDialog(null,"  You dont have select any dimension in line  ","Error ",JOptionPane.ERROR_MESSAGE);
				    
				}else if(selected_col_dims.size()==0){
					erreur.append("\n"+"  You dont have select any dimension in column ");
				    erreur.setForeground(Color.RED);
				    JOptionPane.showMessageDialog(null,"  You dont have select any dimension in column ","Error ",JOptionPane.ERROR_MESSAGE);
				    
				}else if(selected_mesures.size()==0){
					erreur.append("\n"+" You dont have select any mesure to calculat ");
				    erreur.setForeground(Color.RED);
				    JOptionPane.showMessageDialog(null," You dont have select any mesure to calculat ","Error ",JOptionPane.ERROR_MESSAGE);
				    
				}
				
			}  
           });
           //////////////////////////////////////////////////////////////////////////////////////////////////////
           reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(main.conn!=null && selected_ligne_dims.size()>0 && selected_mesures.size()>0 && selected_col_dims.size()>0){
					try{
						int test=JOptionPane.showConfirmDialog(null," \n"
								+ "Do you want really reset this cube ? \n ","Reset Cube",JOptionPane.YES_NO_OPTION);
					if(test==0){
						    fait_dimligne_list.setListData(new Object[0]);
						    selected_ligne_dims.clear();
					        fait_dimcol_list.setListData(new Object[0]);
					        selected_col_dims.clear();
					        
					        fait_mesure_list.removeAllItems();
					        fait_mesure_list.removeAllItems();
					        selected_mesures.clear();
					        
						}
						
					}catch(Exception e){
						erreur.append("\n"+e.getMessage());
					    erreur.setForeground(Color.RED);
					}
				}
			}  
           });
           //////////////////////////////////////////////////////////////////////////////////////////////////////
           /*******************************************************************/	   
	    } //fin de constructeur
	 /********************************************************************************************************/
	     
}
