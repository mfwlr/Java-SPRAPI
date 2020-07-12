package sprites;

import java.io.File;

/**
 * A costume is simply a path to an image and a default size for that image.
 * Costumes are used by sprites to provide multiple "looks"
 * @author Max
 *
 */
public class Costume {
	private String costumeName;
	private String costumePath;
	
	
	public Costume(String costumePath) throws SprapiException {
		this(new File(costumePath).getName().split("\\.")[0], costumePath);
	}
	

	
	public Costume(String costumeName, String costumePath) throws SprapiException {
		this.costumePath = costumePath;
		this.costumeName = costumeName;
		if(!new File(this.costumePath).exists()) {
			throw new SprapiException("File path for costume does not exist - " + costumePath);
		}
		
	}
	
	public String toString() {
		String out = String.format("%s: Filepath=%s", costumeName, costumePath);
		return out;
	}

	protected String getCostumeName() {
		return costumeName;
	}

	protected void setCostumeName(String costumeName) {
		this.costumeName = costumeName;
	}

	protected String getCostumePath() {
		return costumePath;
	}

	protected void setCostumePath(String costumePath) {
		this.costumePath = costumePath;
	}
	
	public boolean equals(Costume other) {
		return costumeName.equals(other.costumeName);
		
	}


	
	

}
