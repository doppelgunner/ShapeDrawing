import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.*;

public class GPData extends ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	public ArrayList<GPPoint> pointList;
	
	public GPData() {
		super();
		pointList = new ArrayList<>();
	}
	
	public GPPoint get(int n) {
		return pointList.get(n);
	}
	
	@Override
	public void update(Point startPoint, Point endPoint, Point ctrlPoint1, Point ctrlPoint2, GPPoint.Type type) {
		GPPoint gpPoint = new GPPoint(endPoint, ctrlPoint1, ctrlPoint2, type);
		add(gpPoint);
	}
	
	public GPPoint getLast() {
		if (pointList.isEmpty()) return null;
		int lIndex = pointList.size() - 1;
		GPPoint gpPoint = pointList.get(lIndex);
		return gpPoint;
	}
	
	public void add(GPPoint gpPoint) {
		pointList.add(gpPoint);
	}
	
	public void remove(GPPoint gpPoint) {
		pointList.remove(gpPoint);
	}

	public GPPoint removeLast() {
		GPPoint gpPoint = getLast();
		pointList.remove(gpPoint);
		return gpPoint;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (isEmpty()) return;
		super.draw(g2d);

		GeneralPath gp = new GeneralPath();
		GPPoint gpPoint0 = pointList.get(0);
		gp.moveTo(gpPoint0.endPoint.x, gpPoint0.endPoint.y);
		
		for (int i = 1; i < pointList.size(); i++) {
			GPPoint gpPoint = pointList.get(i);
			switch (gpPoint.type) {
				case LINE:
					gp.lineTo(gpPoint.endPoint.x, gpPoint.endPoint.y);
					break;
				case QUAD:
					gp.quadTo(gpPoint.ctrlPoint1.x, gpPoint.ctrlPoint1.y, gpPoint.endPoint.x, gpPoint.endPoint.y);
					break;
				case CUBIC:
					gp.curveTo(
						gpPoint.ctrlPoint1.x, gpPoint.ctrlPoint1.y, 
						gpPoint.ctrlPoint2.x, gpPoint.ctrlPoint2.y,
						gpPoint.endPoint.x, gpPoint.endPoint.y);
					break;
			}
		}
		
		if (isDraw) g2d.draw(gp);
		else g2d.fill(gp);
	}
	
	@Override 
	public boolean isEmpty() {
		return pointList.isEmpty();
	}
	
	public int size() {
		return pointList.size();
	}
	
	public void copy(GPData gpData) {
		super.copy(gpData);
		ArrayList<GPPoint> newPointList = new ArrayList<>();
		for (int i = 0; i < gpData.size(); i++) {
			GPPoint old = gpData.get(i);
			newPointList.add(old.clone());
		}
		this.pointList = newPointList;
	}
	
	public GPData clone() {
		GPData newGPData = new GPData();
		newGPData.copy(this);
		return newGPData;
	}
	
	@Override
	public String shapeName() {
		return "GENERAL_PATH";
	}
	
	@Override
	public String toString() {
		String printString = "GeneralPath:\n";
		for (int i = 0; i < pointList.size(); i++) {
			GPPoint p = pointList.get(i);
			printString += p.toString() + "\n";
		}
		return printString;
	}
	
}