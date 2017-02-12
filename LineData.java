import java.awt.geom.*;
import java.io.*;
import java.awt.*;

public class LineData extends ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	private Point start;
	private Point end;
	
	public LineData() {
		super();
	}
	
	@Override
	public void update(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public Shape getShape() {
		return new Line2D.Double(start.x,start.y,end.x,end.y);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (isEmpty()) return;
		super.draw(g2d);

		g2d.draw(getShape());
	}
	
	@Override
	public boolean isEmpty() {
		return start == null || end == null;
	}
	
	public void copy(LineData lineData) {
		super.copy(lineData);
		
		Point s = lineData.start;
		Point e = lineData.end;
		
		this.start = (s == null) ? null : new Point((int)s.getX(), (int)s.getY());
		this.end = (e == null) ? null : new Point((int)e.getX(), (int)e.getY()); 
	}		
	
	public LineData clone() {
		LineData newLineData = new LineData();
		newLineData.copy(this);
		return newLineData;
	}
	
	@Override
	public String shapeName() {
		return "LINE";
	}
	
	@Override
	public String toString() {
		return "Line: (" + start.x + "," + start.y + "), (" + end.x + "," + end.y + ")";
	}
}