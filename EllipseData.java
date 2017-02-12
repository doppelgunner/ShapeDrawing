import java.awt.geom.*;
import java.io.*;
import java.awt.*;

public class EllipseData extends ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Point start;
	private Point end;
	
	public EllipseData() {
		super();
	}
	
	@Override
	public void update(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Shape getShape() {
		int x = x();
		int y = y();
		int width = width();
		int height = height();
		
		return new Ellipse2D.Double(x,y,width,height);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (isEmpty()) return;
		super.draw(g2d);
		
		Shape shape = getShape();

		if (isDraw) {
			g2d.draw(shape);
		} else {
			g2d.fill(shape);
		}
	}
	
	public int x() {
		return (start.x < end.x) ? start.x : end.x;
	}
	
	public int y() {
		return (start.y < end.y) ? start.y : end.y;
	}
	
	public int width() {
		return Math.abs(end.x - start.x);
	}
	
	public int height() {
		return Math.abs(end.y - start.y);
	}
	
	@Override
	public boolean isEmpty() {
		return start == null || end == null;
	}
	
	public void copy(EllipseData ellipseData) {
		super.copy(ellipseData);
		
		Point s = ellipseData.start;
		Point e = ellipseData.end;
		
		this.start = (s == null) ? null : new Point((int)s.getX(), (int)s.getY());
		this.end = (e == null) ? null : new Point((int)e.getX(), (int)e.getY()); 
	}		
	
	public EllipseData clone() {
		EllipseData newEllipseData = new EllipseData();
		newEllipseData.copy(this);
		return newEllipseData;
	}
	
	@Override
	public String shapeName() {
		return "ELLIPSE";
	}
	
	@Override
	public String toString() {
		return "Ellipse: (" + x() + "," + y() + "), (" + width() + "," + height() + ")";
	}
}