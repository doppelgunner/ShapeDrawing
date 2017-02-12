import java.awt.geom.*;
import java.awt.*;
import java.io.*;
public class GPPoint implements Serializable {
	static final long serialVersionUID = 1L;
	
	public Type type;
	
	public Point endPoint;
	public Point ctrlPoint1;
	public Point ctrlPoint2;
	
	public GPPoint() {
		this(null,null,null,Type.LINE);
	}
	public GPPoint(Point endPoint, Point ctrlPoint1, Point ctrlPoint2, GPPoint.Type type) {
		this.endPoint = endPoint;
		this.ctrlPoint1 = ctrlPoint1;
		this.ctrlPoint2 = ctrlPoint2;
		this.type = type;
	}
	
	public enum Type {
		LINE,QUAD,CUBIC
	}
	
	public void copy(GPPoint gpPoint) {

		type = gpPoint.type;
		endPoint =  (gpPoint.endPoint == null) ? null : new Point(gpPoint.endPoint.x, gpPoint.endPoint.y);
		ctrlPoint1 = (gpPoint.ctrlPoint1 == null) ? null : new Point(gpPoint.ctrlPoint1.x, gpPoint.ctrlPoint1.y);
		ctrlPoint2 = (gpPoint.ctrlPoint2 == null) ? null : new Point(gpPoint.ctrlPoint2.x, gpPoint.ctrlPoint2.y);
	}
	
	public GPPoint clone() {
		GPPoint newGPPoint = new GPPoint();
		newGPPoint.copy(this);
		return newGPPoint;
	}
	
	public String toString() {
		String pointString = "Point: (" + endPoint.x + "," + endPoint.y + ")";
		String typeString = " type: " + type;
		String ctrlPoint1String = (ctrlPoint1 == null) ? "" : " Ctrl: (" + ctrlPoint1.x + "," + ctrlPoint1.y + ")";
		String ctrlPoint2String = (ctrlPoint1 == null) ? "" : " (" + ctrlPoint2.x + "," + ctrlPoint2.y + ")";
		return pointString + typeString + ctrlPoint1String + ctrlPoint2String;
	}
}