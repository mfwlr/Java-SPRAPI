package sprites;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Sprite class holds the information about a specific sprite. This
 * includes: The sprite name - this is how the sprite is accessed All the
 * costumes for the sprite Size transforms X and Y position on canvas - TODO
 * 
 * @author Max
 *
 */
public class Sprite{
	private String spriteName;
	private HashMap<String, Costume> costumes;
	private ArrayList<String> costumeNames;
	
	
	private int currentCostumeIndex;
	
	private int width;
	private int height;
	
	private int xPos;
	private int yPos;
	
	private boolean flipped;

	
	private PropertyChangeSupport support;
	
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}
	

	public Sprite(String spriteName, String costumeName, String firstCostumePath, int width, int height) throws SprapiException {
		
		 support = new PropertyChangeSupport(this);
		
		
		this.spriteName = spriteName;
		this.width = width;
		this.height = height;

		// Defaults
		
		this.xPos = 0;
		this.yPos = 0;
		
		this.flipped = false;
	

		// First costume setup
		costumes = new HashMap<>();
		costumeNames = new ArrayList<>();
		
		Costume fc;
		if (costumeName.equals("")) {
			fc = new Costume(firstCostumePath);
			costumeName = fc.getCostumeName();
		} else {
			fc = new Costume(costumeName, firstCostumePath);
		}

		costumes.put(costumeName, fc);
		costumeNames.add(costumeName);
		currentCostumeIndex = 0;
		
	}

	public Sprite(String spriteName, String firstCostumePath, int width, int height) throws SprapiException {
		this(spriteName, "", firstCostumePath, width, height);
	}
	
	
	public void addCostume(String name, String costumePath) throws SprapiException {
		if(!costumes.containsKey(name)) {
			Costume newC = new Costume(name, costumePath);
			costumes.put(name,  newC);
			costumeNames.add(name);
		}
		else {
			throw new SprapiException("Sprite " + spriteName + " already has costume " + name);
		}
	}
	
	public void removeCostume(String name) throws SprapiException {
		Costume currentCostume = costumes.get(costumeNames.get(currentCostumeIndex));
		if(costumes.containsKey(name) && costumes.size() != 1 && !currentCostume.getCostumeName().equals(name)) {
			costumes.remove(name);
			costumeNames.remove(name);
		}
		else {
			throw new SprapiException("Sprite " + spriteName + " does not have the costume " + name);
		}
	}
	
	public void changeCostume(String name) throws SprapiException {
		if(costumes.containsKey(name)) {
			support.firePropertyChange("costume", 
					costumes.get(costumeNames.get(currentCostumeIndex)), 
					costumes.get(name));
			currentCostumeIndex = costumeNames.indexOf(name);
			if(flipped) {
				flipSprite();
			}
		}
		else {
			throw new SprapiException("Sprite " + spriteName + " does not have the costume " + name);
		}
		
		
	}
	
	public void changePos(int x, int y) {
		IntTuple op = new IntTuple(xPos, yPos);
		IntTuple np = new IntTuple(x, y);
		
		support.firePropertyChange("pos",op,np);
		xPos = x;
		yPos = y;
		
		
	}
	
	public void changeDimensions(int width, int height) {
		IntTuple op = new IntTuple(this.width, this.height);
		IntTuple np = new IntTuple(width, height);
		
		support.firePropertyChange("dim",op,np);
		this.width = width;
		this.height = height;
	}
	
	public void changeDimensions(int dim) {
		this.changeDimensions(dim,dim);
	}
	public void changeWidth(int width) {
		this.changeDimensions(width, this.height);
	}
	
	public void flipSprite() {
		support.firePropertyChange("flip",this.flipped, !this.flipped);
		flipped = !flipped;
	}
	
	public void changeHeight(int height) {
		this.changeDimensions(this.width, height);
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public boolean getFlipped() {
		return flipped;
	}

	public String getCostumePath() {
		return costumes.get(costumeNames.get(currentCostumeIndex)).getCostumePath();
	}
	
	public void nextCostume() throws SprapiException {
		currentCostumeIndex++;
		if(currentCostumeIndex >= costumeNames.size()) {
			currentCostumeIndex = 0;
		}
		changeCostume(costumeNames.get(currentCostumeIndex));
	}
	
	public void previousCostume() throws SprapiException {
		currentCostumeIndex--;
		if(currentCostumeIndex == -1) {
			currentCostumeIndex = costumeNames.size()-1;
		}
		changeCostume(costumeNames.get(currentCostumeIndex));
	}
	
	public String displayCostumeList() {
		return costumeNames.toString();
	}
	
	public void swapCostumeLocation(int indexOne, int indexTwo) {
		Collections.swap(costumeNames, indexOne,indexTwo);
		String currentCostume = costumeNames.get(currentCostumeIndex);
		currentCostumeIndex = costumeNames.indexOf(currentCostume);
	}
	
	public void swapCostumeLocation(String c1, String c2) {
		int i1 = costumeNames.indexOf(c1);
		int i2 = costumeNames.indexOf(c2);
		if(i1 != -1 && i2 != -1){
			swapCostumeLocation(i1,i2);
		}else {
			new SprapiException("One of the provided costume indices does not exist - please use displayCostumeList() to check your current costumes");
		}
	}

}
