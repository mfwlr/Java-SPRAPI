package rps;

import sprites.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * RockPaperScissors GUI is a canned graphical interface for a simple
 * Rock, Paper, Scissors game. It uses the SPRAPI to display loosely 
 * 'Scratch-like' sprites for both players.
 * @author Max
 *
 */
public class RockPaperScissorsGUI extends JFrame {

	private SprapiPanel leftPanel;
	private SprapiPanel rightPanel;
	private JLabel rightPlayerName;
	private JLabel leftPlayerName;
	private Sprite rightSprite;
	private Sprite leftSprite;
	
	private int width;
	private int height;

	private JPanel jp1;
	private JPanel jp2;

	/**
	 * Default constructor - default size is 
	 * 500 by 600, with a title of 'Rock Paper Scissors'
	 */
	public RockPaperScissorsGUI() {
		this(500, 600, "Rock Paper Scissors");
	}

	/**
	 * Constructor for the GUI
	 * @param width - width of the GUI window, an integer
	 * @param height - height of the GUI window, an integer
	 * @param title - The title for the rock, paper, scissors game window
	 */
	public RockPaperScissorsGUI(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.setSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setTitle(title);
		this.setResizable(false);
		jp1 = new JPanel();
		jp1.setLayout(new BorderLayout());
		
		

		jp2 = new JPanel();
		jp2.setLayout(new BorderLayout());
		


		this.setLayout(new GridLayout(1, 2));
		this.add(jp1);
		this.add(jp2);

		jp1.setOpaque(true);
		jp2.setOpaque(true);

		rightPlayerName = new JLabel("CPU", SwingConstants.CENTER);
		leftPlayerName = new JLabel("Left", SwingConstants.CENTER);

		rightPlayerName.setOpaque(true);
		rightPlayerName.setBackground(Color.BLACK);
		rightPlayerName.setForeground(Color.WHITE);

		leftPlayerName.setOpaque(true);
		leftPlayerName.setBackground(Color.BLACK);
		leftPlayerName.setForeground(Color.WHITE);

		jp1.add(leftPlayerName, BorderLayout.NORTH);
		jp2.add(rightPlayerName, BorderLayout.NORTH);
		
		this.setLocationRelativeTo(null);

	}

	/**
	 * This function displays the game window.
	 */
	public void displayGameWindow() {
		this.setVisible(true);
	}

	/**
	 * Use this function to change panel color for the left panel.
	 * Color should be a Color object.
	 * @param c - A Color object. Can use Color constants or a
	 * new Color object.
	 */
	public void setLeftColor(Color c) {
		leftPanel.setOpaque(true);
		leftPanel.setBackground(c);
	}

	/**
	 * Use this function to change panel color for the right panel.
	 * Color should be a Color object.
	 * @param c - A Color object. Can use Color constants or a
	 * new Color object.
	 */
	public void setRightColor(Color c) {
		rightPanel.setOpaque(true);
		rightPanel.setBackground(c);
	}

	/**
	 * Initialize the first sprite on the left. A sprite requires a starting
	 * costume for this GUI.
	 * @param name - Sprite name and first costume name
	 * @param path - The image path for the first costume
	 * @throws SprapiException - An exception for errors related to the image path
	 */
	public void leftSpriteInit(String name, String path) throws SprapiException {
	
		leftSprite = new Sprite("left", name, path, width/2 - 50, height/2 - 50);

		leftPanel = new SprapiPanel(leftSprite);

		leftSprite.addPropertyChangeListener(leftPanel);
		jp1.add(leftPanel);
		leftPanel.setSpriteVisible(false);

	}

	/**
	 * Initialize the first sprite on the right. A sprite requires a starting
	 * costume for this GUI.
	 * @param name - Sprite name and first costume name
	 * @param path - The image path for the first costume
	 * @throws SprapiException - An exception for errors related to the image path or sprite
	 */
	public void rightSpriteInit(String name, String path) throws SprapiException {
		
		rightSprite = new Sprite("right", name, path, width/2 - 50, height/2 - 50);

		rightPanel = new SprapiPanel(rightSprite);

		rightSprite.addPropertyChangeListener(rightPanel);
		jp2.add(rightPanel);
		rightPanel.setSpriteVisible(false);
	}
	
	/**
	 * Helper function which accesses a panel on the GUI.
	 * Panel must be "left" or "right".
	 * Returns null if no panel is specified.
	 */
	private Sprite spriteCheck(String panel) {
		if (panel.equals("left")) {
			return leftSprite;
		}else if(panel.equals("right")) {
			return rightSprite;
		}
		return null;
	}

	/**
	 * Add a costume to a sprite. Will not do so if an incorrect
	 * panel is specified.
	 * @param panel - Panel to change. Use "left" or "right"
	 * @param name - The name of the costume to be added
	 * @param path - The path for the costume's image
	 * @throws SprapiException - An exception for errors related to the image path or sprite
	 */
	public void addCostume(String panel, String name, String path) throws SprapiException {
		Sprite s = spriteCheck(panel);
		if(s != null) {
			s.addCostume(name,path);
		}

	}

	/**
	 * 
	 * Removes a costume from a sprite. Will not do so if an incorrect
	 * panel is specified.
	 * @param panel - Panel to change. Use "left" or "right"
	 * @param name - The name of the costume to be added
	 * @throws SprapiException - An exception for errors related to the image path or sprite
	 */
	public void removeCostume(String panel, String name) throws SprapiException {
		Sprite s = spriteCheck(panel);
		if(s != null) {
			s.removeCostume(name);
		}
	}

	/**
	 * List the costumes on a sprite in a message box. Will not do so if an incorrect panel is specified;
	 * @param panel - "left" or "right"
	 */
	public void listCostumes(String panel){
		Sprite s = spriteCheck(panel);
		if(s != null) {
			String message = s.displayCostumeList();
			JOptionPane.showMessageDialog(this, message, "Costume list for sprite " + panel, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * 
	 * Changes a costume for a sprite. Will not do so if an incorrect
	 * panel is specified.
	 * @param panel - Panel to change. Use "left" or "right"
	 * @param name - The name of the costume to change to
	 * @throws SprapiException - An exception for errors related to the image path or sprite
	 */
	public void changeCostume(String panel, String name) throws SprapiException {
		Sprite s = spriteCheck(panel);
		if(s != null) {
			s.changeCostume(name);
		}
	}
	
	/**
	 * Changes the costume to the next costume in the sprite's costume list.
	 * @param panel - "left" or "right"
	 * @throws SprapiException -  An exception for errors related to the image path or sprite
	 */
	public void nextCostume(String panel) throws SprapiException {
		Sprite s = spriteCheck(panel);
		if(s != null) {
			s.nextCostume();
		}
	}
	
	/**
	 * Changes the costume to the previous costume in the sprite's costume list.
	 * @param panel - "left" or "right"
	 * @throws SprapiException -  An exception for errors related to the image path or sprite
	 */
	public void prevCostume(String panel) throws SprapiException {
		Sprite s = spriteCheck(panel);
		if(s != null) {
			s.previousCostume();
		}
	}
	
	/**
	 * Make a given sprite visible or not
	 * @param panel - "left" or "right"
	 * @param vis - true (to be visible) or false (to be invisible)
	 */
	public void spriteVisibility(String panel, boolean vis) {
		if (panel.equals("left")) {
			leftPanel.setSpriteVisible(vis);
		}else if(panel.equals("right")) {
			rightPanel.setSpriteVisible(vis);
		}
	}
	
	/**
	 * Set the player name for the left or right player/panel
	 * @param panel - "left" or "right"
	 * @param name - player's name
	 */
	public void setPlayerName(String panel, String name) {
		if (panel.equals("left")) {
			leftPlayerName.setText(name);
		}else if(panel.equals("right")) {
			rightPlayerName.setText(name);
		}
	}
	/**
	 * Flip a given sprite horizontally.
	 * @param panel - "left" or "right"
	 */
	public void flipSprite(String panel) {
		Sprite s = spriteCheck(panel);
		if (s!= null) {
			s.flipSprite();
		}
	}
	
	/**
	 * Resize a given sprite
	 * @param panel - "left" or "right"
	 * @param width - new width for the sprite
	 * @param height - new height for the sprite
	 */
	public void resizeSprite(String panel, int width, int height) {
		Sprite s = spriteCheck(panel);
		if (s!= null) {
			s.changeDimensions(width, height);
		}
	}
	
	}
	

