package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SprapiPanel extends JPanel implements PropertyChangeListener {

	private BufferedImage spriteImage;

	// The panel needs to know four things about the sprite still
	private int xPos;
	private int yPos;

	private int width;
	private int height;

	private boolean flipped;
	private boolean spriteVisible;

	private String curPath;

	public SprapiPanel(Sprite s) throws SprapiException {
		xPos = s.getXPos();
		yPos = s.getYPos();

		height = s.getHeight();
		width = s.getWidth();

		curPath = s.getCostumePath();

		try {
			spriteImage = drawSpriteImage(ImageIO.read(new File(curPath)));
		} catch (IOException e) {
			throw new SprapiException("Error with making new sprite - file path '" + curPath + "' not found on system. Did you move the image?");
		}
		flipped = false;
		spriteVisible = true;
	}

	private BufferedImage drawSpriteImage(BufferedImage in) throws IOException {

		Image tmp = in.getScaledInstance(height, width, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		if (flipped) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-dimg.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			dimg = op.filter(dimg, null);
		}

		return dimg;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object newVal = e.getNewValue();
		try {
			switch (e.getPropertyName()) {
			case "costume":
				curPath = ((Costume) newVal).getCostumePath();

				spriteImage = drawSpriteImage(ImageIO.read(new File(curPath)));

			case "pos":
				IntTuple r = ((IntTuple) newVal);
				xPos = r.getX();
				yPos = r.getY();
				break;
			case "dim":
				IntTuple d = ((IntTuple) newVal);
				width = d.getX();
				height = d.getY();

				spriteImage = drawSpriteImage(ImageIO.read(new File(curPath)));
				;

				break;

			case "flip":
				Boolean flip = ((Boolean) newVal);
				flipped = flip;

				spriteImage = drawSpriteImage(ImageIO.read(new File(curPath)));
				;

			}
		} catch (IOException er) {
			JOptionPane.showMessageDialog(null, "The current costume's path could not be found - did you move the image?", "Error - costume image not found",JOptionPane.ERROR_MESSAGE);
		}

		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (spriteVisible) {
			g.drawImage(spriteImage, xPos, yPos, this);
		}
	}

	public void setSpriteVisible(boolean vis) {
		spriteVisible = vis;
		repaint();
	}

}
