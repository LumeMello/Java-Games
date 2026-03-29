package MG;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PoioCards {
	String[] cardList = {
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"10",
		"11"
	};
	
	int rows = 4;
	int columns = 5;
	
	int cardW = 90;
	int cardH = 128;
	
	ArrayList<Card> cardSet;
	ImageIcon cardBackImageIcon;
	
	int boardW = columns * cardW;
	int boardH = rows * cardH;
	
	JFrame frame = new JFrame("PoioCards");
	JLabel textLabel = new JLabel();
	JPanel textPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	JPanel restartPanel = new JPanel();
	JButton restartButton = new JButton();
	
	
	int errorCount = 0;
	ArrayList<JButton> board;
	Timer hideCardTimer;
	boolean gameReady = false;
	JButton card1Selected;
	JButton card2Selected;
	int points = 0;
	
	PoioCards(){
		setUpCards();
		shuffleCards();
		
		frame.setSize(boardW, boardH);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setText("Error:" + Integer.toString(errorCount));
		
		textPanel.setPreferredSize(new Dimension(boardW, 30));
		textPanel.add(textLabel);
		frame.add(textPanel,BorderLayout.NORTH);
		
		board = new ArrayList<JButton>();
		boardPanel.setLayout(new GridLayout(rows,columns));
		
		for (int i = 0; i < cardSet.size(); i++) {
			JButton tile = new JButton();
			tile.setPreferredSize(new Dimension(cardW, cardH));
			tile.setOpaque(true);
			tile.setIcon(cardSet.get(i).cardImageIcon);
			tile.setFocusable(false);
			tile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!gameReady) {
						return;
					}
					JButton tile = (JButton) e.getSource();
					if(tile.getIcon() == cardBackImageIcon) {
						if(card1Selected == null) {
							card1Selected = tile;
							int index = board.indexOf(card1Selected);
							card1Selected.setIcon(cardSet.get(index).cardImageIcon);
						}else if(card2Selected == null) {
							card2Selected = tile;
							int index = board.indexOf(card2Selected);
							card2Selected.setIcon(cardSet.get(index).cardImageIcon);
							
							if(card1Selected.getIcon() != card2Selected.getIcon()) {
								errorCount++;
								textLabel.setText("Erros:" + Integer.toString(errorCount));
								hideCardTimer.start();
							}else {
								card1Selected = null;
								card2Selected = null;
								points++;
								
								if(points == (board.size()/2)) {
									win();
								}
							}
						}
					}
				}
			});
			board.add(tile);
			boardPanel.add(tile);
		}
		frame.add(boardPanel);
		
		restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
		restartButton.setText("Restart Button");
		restartButton.setPreferredSize(new Dimension(boardW,30));
		restartButton.setFocusable(false);
		restartButton.setEnabled(false);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameReady) {
					return;
				}
				
				gameReady = false;
				restartButton.setEnabled(false);
				card1Selected = null;
				card2Selected = null;
				shuffleCards();
				
				for(int i = 0; i <board.size(); i++) {
					board.get(i).setIcon(cardSet.get(i).cardImageIcon);
					board.get(i).setEnabled(true);
				}
				points = 0;
				errorCount = 0;
				textLabel.setText("Erros:"+Integer.toString(errorCount));
				hideCardTimer.start();
			}
		});
		restartPanel.add(restartButton);
		frame.add(restartPanel, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
		
		hideCardTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideCards();
			}
		});
		hideCardTimer.setRepeats(false);
		hideCardTimer.start();
	}

	private void hideCards() {
		if(gameReady && card1Selected != null && card2Selected != null) {
			card1Selected.setIcon(cardBackImageIcon);
			card2Selected.setIcon(cardBackImageIcon);
			
			card1Selected = null;
			card2Selected = null;
		}else {
			for(int i = 0; i < board.size(); i++) {
				board.get(i).setIcon(cardBackImageIcon);
			}
			gameReady = true;
			restartButton.setEnabled(true);
		}
		
	}

	private void shuffleCards() {
		for (int i = 0; i < cardSet.size();i++) {
			int j = (int) (Math.random() * cardSet.size());
			
			Card temp = cardSet.get(i);
			cardSet.set(i, cardSet.get(j));
			cardSet.set(j, temp);
		}
		
	}

	private void win() {
		for(int i = 0; i <board.size(); i++) {
			board.get(i).setEnabled(false);
		}
		
		textLabel.setText("You Win! Total Error Count:" + errorCount);
	}

	private void setUpCards() {
		cardSet = new ArrayList<Card>();
		for (String cardName : cardList) {
			Image cardImg = new ImageIcon(getClass().getResource(cardName+".png")).getImage();
			ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardW, cardH, java.awt.Image.SCALE_SMOOTH));
			
			Card card = new Card(cardName, cardImageIcon);
			cardSet.add(card);
		}
		cardSet.addAll(cardSet);
		
		Image cardBackImg = new ImageIcon(getClass().getResource("1.png")).getImage();
		cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardW, cardH, java.awt.Image.SCALE_SMOOTH));
	}
}
