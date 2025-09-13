package io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class ImageFileWriter {
	private BufferedImage img;
	private File img_file;
	
	public ImageFileWriter(String initial_url, int w, int h) {
		img = new BufferedImage(w+1, h+1, BufferedImage.TYPE_INT_ARGB);
	}
	public abstract void onDraw(Graphics2D g2d);

	public void draw() {
		onDraw((Graphics2D) img.createGraphics());
	}
	
	public void load_subfolders(String initial_url) {
		System.out.println("loading subfolders, please wait...");
		
		String folder;
		for(int id=-5112; id<5112; id++) {
			if(id > 0)
				folder = "/positives/id_" + id + "/";
			else if (id < 0)
				folder = "/negatives/id_" + id + "/";
			else 
				folder = "/zeros/id_" + id + "/";
			
			img_file = new File(initial_url, folder);
			if(!img_file.exists()) {
				img_file.mkdirs();
			}
		}
	}
	
	public void saveImage(String url) {
		try {
			img_file = new File(url);
			if(!img_file.getParentFile().exists()) {
				img_file.getParentFile().mkdirs();
			}
			ImageIO.write(img, "png", img_file);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
}
