package OLAP_App;

import java.awt.*;
import javax.swing.*;

public class image extends JPanel{

	Image img;
	int x, y, w, h;
	
 /*********************************************************************************/
	public image(Image img,int x,int y,int w, int h){
		this.setLayout(null);
		this.img=img;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.setBounds(x, y, w, h);
	}
 /***********************************************************************************/	
	public void paintComponent(Graphics g){
		 g.setColor(Color.WHITE);
		   g.fillRect(0, 0, this.getWidth(),this.getHeight());
		   
		  g.drawImage(img,0,0,w,h,this);
	}
    /**************************************************************************************/
	 /**************************************************************************************/
	 /**************************************************************************************/

}
