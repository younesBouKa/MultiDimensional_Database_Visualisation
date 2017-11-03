package OLAP_App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class home extends JPanel {
	
	       String source_path="/olap_images/";
	       
                  //Image img1=this.getToolkit().getImage(getClass().getResource(source_path+"images_033.jpg"));
		   Image img2=this.getToolkit().getImage(getClass().getResource(source_path+"home.jpg"));
		   //Image img3=this.getToolkit().getImage(getClass().getResource(source_path+"images_125.jpg"));
		   //Image img4=this.getToolkit().getImage(getClass().getResource(source_path+"images_235.jpg"));
		   //Image namelogo=this.getToolkit().getImage(getClass().getResource(source_path+"mylogo.png"));
		   
	
	/*********************************************************************************************************/
	   public  home(main_window main){
	    	
		   //this.setBackground(Color.GRAY);
		   
		   this.setLayout(null);
		   this.setBounds(0, 0, 500, 500);
		   
		   JButton start=new JButton("Start");
		   start.setBounds(330, 550, 110, 42);
		   this.add(start);
		   
		   JButton propos=new JButton("About");
		   propos.setBounds(450, 550, 110, 42);
		   this.add(propos);
		   
		     int x=140,y=60;
			 int wid=300,hei=220;
			 int sv=10,sh=10;
		   
		   
			 //this.add(new image(img1,x,y,wid,hei));
			// this.add(new image(img2,x+wid+sh,y,wid,hei));
			// this.add(new image(img3,0,0,this.getWidth(),this.getHeight()));
			 //this.add(new image(img4,x+wid+sh,y+hei+sv,wid,hei));
                         //this.add(new image(namelogo,785,600,100,30));
             
             start.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					main.onglet.setSelectedIndex(1);
					main.onglet.setEnabledAt(1,true);
			          
				}
            	 
             });
             
            propos.addActionListener(new ActionListener(){
 				public void actionPerformed(ActionEvent arg0) {
 					 JFrame po=new JFrame();
 					 po.setSize(350, 200);
 					 
 					 JPanel pan=new JPanel();
 					 pan.setLayout(null);
 					 
 					 JTextArea pro=new JTextArea();
 					 pro.setBounds(30,30,320,230);
 					 pro.setEditable(false);
 					 pro.setBackground(null);
 					 Font f=new Font("Verdana",Font.BOLD,18);
 					 pro.setFont(f);
 					 pro.setForeground(Color.BLACK);
 					 pro.setText(" In this project i tried to  \n "
                                                    + " visualize a MySQL database  \n "
                                                    + " in a clear view by using  \n "
                                                    + " a multidimensinal table \n "
                                                    + "\n By Younes Boukanoucha  ");
 					 pan.add(pro);
 					 
 					 po.setContentPane(pan);
 					 
 					 po.setVisible(true);
 					 po.setResizable(false);
 					 po.setLocationRelativeTo(main);
 				}
             	 
              });
	    }
	 /********************************************************************************************************/
     public void paintComponent(Graphics g){
    	 
    	   g.setColor(Color.LIGHT_GRAY);
		   g.fillRect(0, 0, this.getWidth(),this.getHeight());
		   
           g.drawImage(img2, 0, 0, this.getWidth(),this.getHeight(),this);
		   
		   g.setColor(Color.BLUE);
		   g.setFont(new Font(Font.SANS_SERIF,Font.ITALIC+Font.BOLD,27));
		   //g.drawString("On_Line Analtical Processing", 280,30);
		
     }
	/********************************************************************************************************/
}
