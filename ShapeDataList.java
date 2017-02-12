import java.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;

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