package KIRBYAMOLE;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class KirbyAMole {
	int Width = 600;
	int Height = 650;
	
	JFrame frame = new JFrame("Kirby: Whac A Mole");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	
	JButton[] board = new JButton[9];
	
	ImageIcon kirbyIcon;
	ImageIcon kingIcon;
	
	JButton currKirbyTile;
	JButton currKingTile;
	
	Random random = new Random();
	Timer setKirbyTimer;
	Timer setKingTimer;
	
	int score;
	
	KirbyAMole(){
		
		frame.setSize(Width,Height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		textLabel.setFont(new Font("Arial", Font.PLAIN,50));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setText("Score: 0");
		textLabel.setOpaque(true);
		
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel);
		
		boardPanel.setLayout(new GridLayout(3,3));
		boardPanel.setLayout(new GridLayout(3,3));
		
		frame.add(textPanel, BorderLayout.NORTH);
		frame.add(boardPanel);
		
		Image kirbyImg = new ImageIcon(getClass().getResource("./kirby.png")).getImage();
		kirbyIcon = new ImageIcon(kirbyImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
		
		Image kingImg = new ImageIcon(getClass().getResource("./kingddd.png")).getImage();
		kingIcon = new ImageIcon(kingImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
		
		score = 0;
		
		for(int i = 0; i < 9; i++) {
			JButton tile = new JButton();
			board[i] = tile;
			boardPanel.add(tile);
			tile.setFocusable(false);
			
			tile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton tile = (JButton)e.getSource();
					if(tile == currKirbyTile) {
						score++;
						textLabel.setText("Score: " + Integer.toString(score));
					}else if(tile == currKingTile){
						textLabel.setText("Game Over, Score:" + Integer.toString(score));
						setKirbyTimer.stop();
						setKingTimer.stop();
						for(int i = 0; i < 9; i++) {
							board[i].setEnabled(false);
						}
					}
				}
			});
		}
		
		setKirbyTimer = new Timer(750, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currKirbyTile != null) {
					currKirbyTile.setIcon(null);
					currKirbyTile = null;
				}
				
				int num = random.nextInt(9);
				JButton tile = board[num];
				
				if(currKingTile == tile) return;
				
				currKirbyTile = tile;
				currKirbyTile.setIcon(kirbyIcon);
				
			}
		});
		
		setKingTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currKingTile != null) {
					currKingTile.setIcon(null);
					currKingTile = null;
				}
				
				int num = random.nextInt(9);
				JButton tile = board[num];
				
				if(currKirbyTile == tile) return;
				
				currKingTile = tile;
				currKingTile.setIcon(kingIcon);
				
			}
		});
		
		setKingTimer.start();
		setKirbyTimer.start();
		frame.setVisible(true);
	}
}
