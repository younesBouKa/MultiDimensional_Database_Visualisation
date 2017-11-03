package OLAP_App;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class main_window extends JFrame{
	
	            String database="";
				JTabbedPane onglet;
				connect conn=null;
				cube_menager cube=new cube_menager(this);
				database_menager db_menager=new database_menager(this);
				home aceuil=new home(this);
				String source_path="/olap_images/";
				JFrame isme=this;
				
	public main_window(String name) {
			
			 super(name);
	   	     this.setSize(900,710);
	   	     this.setLayout(null);
	   	     this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			 JPanel cn=new JPanel();                           // le container principale
			 cn.setLayout(null);
			 this.setLocationRelativeTo(null);
			 Image img=this.getToolkit().getImage(getClass().getResource(source_path+"home.jpg"));  // cette methode ne bloque pas le programme pendant le telechargement de l'image
			 this.setIconImage(img);
			 this.setResizable(false);
			 /*********************************************************************************************************************************************/
	 		 /************** le menu general      *********************/
	         JMenuBar menubar=new JMenuBar();
	             menubar.setBounds(2,2,890,20);
	         JMenu file=new JMenu("File");
		         JMenuItem nouveau=new JMenuItem("New");
		         JMenuItem open=new JMenuItem("Open");
		         JMenuItem save=new JMenuItem("Save");
		         JMenuItem saveas=new JMenuItem("Save as");
		         JMenuItem quiter=new JMenuItem("Exit");
	             file.add(nouveau);
	             file.add(open);
	             file.add(save);
	             file.add(saveas);
	             file.add(quiter);
	         JMenu forms=new JMenu("Edit");
		         JMenuItem ligne_select=new JMenuItem("cut");
		         JMenuItem rectangle_select=new JMenuItem("copy");
		         JMenuItem polygone_select=new JMenuItem("paste");
		         forms.add(ligne_select);
		         forms.add(rectangle_select);
		         forms.add(polygone_select);

	         JMenu effet=new JMenu("Tools");
		         JMenuItem slice=new JMenuItem("slice");
		         JMenuItem dice=new JMenuItem("Dice");
		         JMenuItem rotate=new JMenuItem("Rotate");
		         JMenuItem drill_up=new JMenuItem("Drill-up");
		         JMenuItem drill_down=new JMenuItem("Drill-Down");
		         effet.add(slice);
		         effet.add(dice);
		         effet.add(rotate);
		         effet.add(drill_up);
		         effet.add(drill_down);
	         JMenu seting=new JMenu("Seting");
	         menubar.add(file);
	         menubar.add(forms);
	         menubar.add(effet);
	         menubar.add(seting);
	 		cn.add(menubar);
	 		/**********************************/
	   	     /*********************************************************************************************************************************************/
	 		  onglet=new JTabbedPane();
	 		  onglet.setBounds(2,22,890,660);
	        /*
                
                JTabbedPane onglet=new JTabbedPane();
                
                JPanel adapter=new JPanel();
                adapter.setBackground(Color.red);
                
                JPanel monitor=new JPanel();
                monitor.setBackground(Color.GREEN);
                       
                JPanel Color_menagement=new JPanel();
                Color_menagement.setBackground(Color.BLUE);
                
                JPanel intel=new JPanel();
                intel.setBackground(Color.BLACK);
                
                onglet.add("Adapter",adapter);
                onglet.add("Monitor",monitor);
                onglet.add("Color Menagement",Color_menagement);
                onglet.add("Intel (R)",intel);
                
                */
                
                
                
	          
	          onglet.add("Home",aceuil);
	          
	          onglet.add("Database Menagement",db_menager);
	          
	          onglet.add("Cube Menagement",cube);
	          
	         onglet.add("MultiD View",new JPanel());
	         onglet.setEnabledAt(3,false); // pour blocker le pannel  de visualisation de cube
	         onglet.setEnabledAt(1,false); 
	          
	          onglet.setEnabledAt(2,false); // pour blocker le pannel  de construction de cube
	          onglet.setSelectedIndex(0);   // pour aller a un pannel
	         
	          cn.add(onglet);
	          /************************************************************************************************************************************************/
	          /************************************************************************************************************************************************/
	        
	           save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//instancier JFileChooser
					final JFileChooser fc = new JFileChooser();
					//invoquer la m�thode showSaveDialog
					int valeuretour = fc.showSaveDialog(isme);
					// juste les dossiers
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					// initialiser le nom du fichier a enregistrer
					fc.setSelectedFile(new File("cocoo.txt"));
					// recuperer le nom du dossier
					String choosenfile=fc.getSelectedFile().getAbsolutePath();
					// enregistrer le fichier dans le dossier choisi
				      File f1=new File(choosenfile+".olap");
				         FileOutputStream fos=null;
				     
				        ObjectOutputStream oos=null;
				        BufferedOutputStream bos=null;
				        
				        byte buff[]=new byte[8];
				        try{
				        	  fos=new FileOutputStream(f1);
				        	   oos=new ObjectOutputStream(fos);
				        	   //////////////////////////////oos.writeObject();
				        
				        	  }catch(IOException e){
				        		  e.printStackTrace();
				        	  }
				        
				    // tester si l'operation est bien effectuee
	               if(fc.APPROVE_OPTION==valeuretour){
	            	   System.out.println("sdfsdfds");
                           JOptionPane.showMessageDialog(null,"well saved ");
	               }
				} 
	           });   
	           
	         /************************************************************************************************************************************************/
	         this.setContentPane(cn);
	     	 this.setVisible(true);
	}

}
