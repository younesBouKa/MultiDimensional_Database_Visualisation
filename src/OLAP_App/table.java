package OLAP_App;

import javax.swing.*;

public class table extends JTable{

	Object data[][];
	Object ligne_titre[];
	Object colonne_titre[];
	
	public table(){
		
	}
	
	public table(Object d[][],String col_titre[],String line_titre[]){
		this.data=d;
		this.ligne_titre=line_titre;
		this.colonne_titre=col_titre;
	}
	
	public void set_lignes_title(String line_titre[] ){
		this.ligne_titre=line_titre;
	}
	
	public void set_col_title(String col_titre[]){
		this.colonne_titre=col_titre;
		
	}
	
	public void set_data(Object d[][]){
		this.data=d;
	}
	
	public void fill(){
		Object cols_titles[]=colonne_titre;
		
		Object da[][]=new Object[ligne_titre.length][colonne_titre.length+1];
		
	}
	
	public void set_cell(Object o,int i,int j){
		if(data!=null){
			if(i<data.length && j<data[0].length)
				data[i][j]=o;
		}
	}
	
	public void set_col_titre(String o,int i){
		if(colonne_titre!=null){
			if(i<colonne_titre.length)
				colonne_titre[i]=o;
		}
	}
	
	
	
}
