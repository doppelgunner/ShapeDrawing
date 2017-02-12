import java.awt.geom.*;
import java.io.*;
import java.awt.*;

public class QuadCurveData extends ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Point start;
	private Point end;
	private Point ctrlPoint;
	
	public QuadCurveData() {
		super();
	}
	
	@Override
	public void update(Point start, Point end, Point ctrlPoint) {
		this.start = start;
		this.end = end;
		this.ctrlPoint = ctrlPoint;
	}
	
	@Override
	public Shape getShape() {
		return new QuadCurve2D.Double(start.x,start.y, ctrlPoint.x,ctrlPoint.y, end.x,end.y);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (isEmpty()) return;
		super.draw(g2d);

		g2d.draw(getShape());
	}
	
	@Override
	public boolean isEmpty() {
		return start == null || end == null || ctrlPoint == null;
	}
	
	public void copy(QuadCurveData quadCurveData) {
		super.copy(quadCurveData);
		
		Point s = quadCurveData.start;
		Point e = quadCurveData.end;
		Point cP = quadCurveData.ctrlPoint;
		
		this.start = (s == null) ? null : new Point((int)s.getX(), (int)s.getY());
		this.end = (e == null) ? null : new Point((int)e.getX(), (int)e.getY());
		this.ctrlPoint = (e == null) ? null : new Point((int)cP.getX(), (int)cP.getY());
	}		
	
	public QuadCurveData clone() {
		QuadCurveData newQuadCurveData = new QuadCurveData();
		newQuadCurveData.copy(this);
		return newQuadCurveData;
	}
	
	@Override
	public String shapeName() {
		return "QUAD_CURVE";
	}
	
	@Override
	public String toString() {
		return "Quad curve: (" + start.x + "," + start.y + "), (" + ctrlPoint.x + "," + ctrlPoint.y + "), (" + end.x + "," + end.y + ")";
	}
}