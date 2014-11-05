package net.foxgenesis.slimesoccer.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public final class ImageUtil {
	public static BufferedImage convertToBufferedImage(Image img, int imgType){
		BufferedImage buffImg = new BufferedImage(img.getWidth(), img.getHeight(), imgType);
		Graphics g = buffImg.getGraphics();
		for(int i=0;i<img.getWidth();i++)
			for(int j=0;j<img.getHeight();j++) {
				Color a = img.getColor(i, j);
				g.setColor(new java.awt.Color(a.getRed(),a.getGreen(),a.getBlue()));
				g.fillRect(i,j,1,1);
			}
		return buffImg;
	}
	
	public static Image toImage(BufferedImage img){
		try {
			Texture texture = BufferedImageUtil.getTexture("", img);
			Image slickImage = new Image(texture.getImageWidth(), texture.getImageHeight() );
			slickImage.setTexture(texture) ;
			return slickImage;
		} catch (IOException e) {
			return null;
		} catch (SlickException e) {
			return null;
		} 
	}
}
