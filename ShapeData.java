import java.io.*;
import java.awt.*;
import java.awt.geom.*;

public class ShapeData implements Serializable {
	static final long serialVersionUID = 1L;
	
	public Color color;
	public boolean isDraw;
	public boolean closeGap;
	public int stroke;
	
	protected ShapeData() {
		this.color = Color.black;
		this.isDraw = true;
		this.closeGap = false;
		this.stroke = 1;
	}
	
	public boolean isEmpty() {return true;}
	public void draw(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(stroke));
		g2d.setColor(color);
	}
	
	public void update(Point startPoint, Point endPoint) {}
	public void update(Point startPoint, Point endPoint, Point ctrlPoint) {}
	public void update(Point startPoint, Point endPoint, Point ctrlPoint1, Point ctrlPoint2) {}
	public void update(Point startPoint, Point endPoint, Point ctrlPoint1, Point ctrlPoint2, GPPoint.Type type) {}
	
	public Shape getShape() {return null;}
	public Area getArea() {return new Area(getShape());}
	
	public void copy(ShapeData sd) {
		color = new Color(sd.color.getRed(),sd.color.getGreen(),sd.color.getBlue(),sd.color.getAlpha());
		isDraw = sd.isDraw;
		closeGap = sd.closeGap;
		stroke = sd.stroke;
	}
	
	public ShapeData clone() {
		ShapeData sd = new ShapeData();
		sd.copy(this);
		return sd;
	}
	
	public String shapeName() {
		return "none";
	}
	
	//public void add(Point p) {}
	//public Point removeLast() {return null;}
	//public void remove(Point p) {}
}