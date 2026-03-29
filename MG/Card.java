package MG;

import javax.swing.ImageIcon;

public class Card {
	String name;
	ImageIcon cardImageIcon;
	
	Card(String name, ImageIcon image){
		this.name = name;
		this.cardImageIcon = image;
	}
	
	public String toString() {
		return name;
	}
}
