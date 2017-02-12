import java.awt.geom.*;
import java.io.*;
import java.awt.*;

public class CubicCurveData extends ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Point start;
	private Point end;
	private Point ctrlPoint1;
	private Point ctrlPoint2;
	
	public CubicCurveData() {
		super();
	}
	
	@Override
	public void update(Point start, Point end, Point ctrlPoint1, Point ctrlPoint2) {
		this.start = start;
		this.end = end;
		this.ctrlPoint1 = ctrlPoint1;
		this.ctrlPoint2 = ctrlPoint2;
	}
	
	@Override
	public Shape getShape() {
		return new CubicCurve2D.Double(start.x,start.y, ctrlPoint1.x,ctrlPoint1.y, ctrlPoint2.x,ctrlPoint2.y, end.x,end.y);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (isEmpty()) return;
		super.draw(g2d);

		g2d.draw(getShape());
	}
	
	@Override
	public boolean isEmpty() {
		return start == null || end == null || ctrlPoint1 == null || ctrlPoint2 == null;
	}
	
	public void copy(CubicCurveData cubicCurveData) {
		super.copy(cubicCurveData);
		
		Point s = cubicCurveData.start;
		Point e = cubicCurveData.end;
		Point cP1 = cubicCurveData.ctrlPoint1;
		Point cP2 = cubicCurveData.ctrlPoint2;
		
		this.start = (s == null) ? null : new Point((int)s.getX(), (int)s.getY());
		this.end = (e == null) ? null : new Point((int)e.getX(), (int)e.getY());
		this.ctrlPoint1 = (e == null) ? null : new Point((int)cP1.getX(), (int)cP1.getY());
		this.ctrlPoint2 = (e == null) ? null : new Point((int)cP2.getX(), (int)cP2.getY());
	}		
	
	public CubicCurveData clone() {
		CubicCurveData newCubicCurveData = new CubicCurveData();
		newCubicCurveData.copy(this);
		return newCubicCurveData;
	}
	
	@Override
	public String shapeName() {
		return "CUBIC_CURVE";
	}
	
	@Override
	public String toString() {
		return 
			"Cubic curve: (" + start.x + "," + start.y + "), (" 
			+ ctrlPoint1.x + "," + ctrlPoint1.y + "), (" 
			+ ctrlPoint2.x + "," + ctrlPoint2.y + "), (" 
			+ end.x + "," + end.y + ")";
	}
}