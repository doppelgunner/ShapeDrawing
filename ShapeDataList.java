import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

import jankovicsandras.imagetracer.ImageTracer;

public class ShapeDataList implements Serializable {
	static final long serialVersionUID = 1L;
	
	private ArrayList<ShapeData> shapeDataList;
	
	public ShapeDataList() {
		shapeDataList = new ArrayList<>();
	}
	
	public ShapeData get(int n) {
		return shapeDataList.get(n);
	}
	
	public Shape getShape(int n) {
		return get(n).getShape();
	}
	
	public void add(ShapeData shapeData) {
		if (shapeData.isEmpty()) return;
		
		shapeDataList.add(shapeData);
	}
	
	public void draw(Graphics2D g2d) {
		for (int i = 0; i < shapeDataList.size(); i++) {
			ShapeData shapeData = shapeDataList.get(i);
			shapeData.draw(g2d);
		}
	}
	
	public void save(BufferedImage bImage) throws Exception {
		System.out.println("saving image...");
		Graphics g = bImage.getGraphics();
		draw((Graphics2D)g);
		
		String filename = System.nanoTime() + ".png";
		File outputfile = new File(filename);
		ImageIO.write(bImage, "png", outputfile);
		System.out.println("saved at: " + outputfile.toString());
		
	}
	
	public void saveSVG(BufferedImage bImage) throws Exception {
		System.out.println("saving image...");
		Graphics g = bImage.getGraphics();
		draw((Graphics2D)g);
		String filename = System.nanoTime() + ".svg";
		
		String svgString = ImageTracer.imageToSVG(bImage, null, null);
		ImageTracer.saveString(filename,svgString);
		
		System.out.println("saved at: " + filename);
	}
	
	public void removeLast() {
		if (isEmpty()) return;
		shapeDataList.remove(shapeDataList.size()-1);
	}
	
	public boolean isEmpty() {
		if (shapeDataList == null || shapeDataList.isEmpty()) return true;
		return false;
	}
	
	public void clear() {
		shapeDataList.clear();
	}
	
	@Override
	public String toString() {
		String printString = "------------ShapedataList------------\n";
		for (int i = 0; i < shapeDataList.size(); i++) {
			ShapeData shapeData = shapeDataList.get(i);
			printString += shapeData.toString() + "\n";
		}
		
		return printString;
	}
}