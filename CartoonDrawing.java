import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CartoonDrawing extends Frame {
	
	private Color[] colors = {
		Color.black, Color.red, Color.blue, Color.white, Color.gray,
		
		new Color(242,229,177), new Color(255,204,193), new Color(62,62,60),
		new Color(49,147,204), new Color(142,38,46), new Color(248,198,79)
	};
	private int colorIndex;
	
	private static final Color DRAWING_COLOR = new Color(255, 100, 200);
	private static final Color FINAL_DRAWING_COLOR = Color.green;
	
	private BufferedImage buffImage;	
	
	private boolean hideImage = true;
	private boolean hideDrawing = false;
	private boolean hideInfo;
	
	private Point startPoint;
	private Point endPoint;
	private Point currentPoint;
	
	private Point ctrlPoint1;
	private Point ctrlPoint2;
	
	private GPPoint.Type currentGPType = GPPoint.Type.LINE;
	
	private ShapeData currentData = new RectData();
	private ShapeDataList shapeDataList = new ShapeDataList();
	
	private String filePath = "draw.txt";
	
	public static final ShapeData[] shapeChoices = {
		new RectData(), new LineData(), new EllipseData(), new QuadCurveData(), new CubicCurveData(), new GPData()
	};
	public static final GPPoint.Type[] gpPointChoices = {
		GPPoint.Type.LINE, GPPoint.Type.QUAD, GPPoint.Type.CUBIC,
	};
	
	public int gpPointChoice = 0;
	public int choice = 0;
	
	public void setImage(String path) {
		try {
			buffImage = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		
		if (!hideInfo) {
			int x = 30;
			int y = 50;
			g2d.drawString("currentColor: " + currentData.color,x, y);
			g2d.drawString("[A] to change color, or 1 to 5 important colors (black,red,blue,white,gray)", x, y += 15);
			g2d.drawString("currentIsDraw: " + currentData.isDraw + " [F]", x, y += 15);
			g2d.drawString("closeGap: " + currentData.closeGap + " [C]", x, y += 15);
			g2d.drawString("save: [S], load: [L], hide image: [H], undo: [Z], hide drawing: [J]", x, y += 15);
			g2d.drawString("[Q] change shape, current: " + shapeChoices[choice].shapeName(), x, y += 15);
			g2d.drawString("mouse wheel to change stroke: " + currentData.stroke, x, y += 15);
			g2d.drawString("draw point: L-click[start], M-click[ctrlPoint1], R-click[ctrlPoint2]",x, y += 15);
			if (currentData instanceof GPData) {
				g2d.drawString("gpData: " + currentGPType, x, y+=15);
			}
		}
		
		
		if (!hideDrawing) {
			//draw shapes
			shapeDataList.draw(g2d);
			
			//draw
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			currentData.draw(g2d);
		}
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		if (!hideImage) { //[H] - hide image?
			if (buffImage != null) {
				g2d.drawImage(buffImage,200,200,null);
			}
		}
	}
	
	public void updateShapeData() {
		if (!(currentData instanceof GPData) && startPoint == null) return;
		
		if (currentData instanceof QuadCurveData) {
			currentData.update(startPoint,currentPoint,ctrlPoint1);
		} else if (currentData instanceof CubicCurveData){
			currentData.update(startPoint,currentPoint,ctrlPoint1,ctrlPoint2);
		} else if (currentData instanceof GPData) {
			currentData.update(startPoint,endPoint,ctrlPoint1,ctrlPoint2,currentGPType);
		} else {
			currentData.update(startPoint,currentPoint);
		}
		
		repaint();
	}
	
	public void load() {
		shapeDataList = (ShapeDataList)deserialize(filePath);
		System.out.println(shapeDataList);
	}
	
	public void save() {
		serialize(filePath, shapeDataList);
	}
	
	private class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyReleased(KeyEvent e) {	
			if (e.getKeyCode() == KeyEvent.VK_SPACE) { //add currentPoints to pointsList
				if (currentData == null || currentData.isEmpty()) return;
		
				if (currentData instanceof GPData) {
					if (currentData.closeGap) {
						GPData gpData = (GPData) currentData;
						GPPoint gpPoint = gpData.get(0);
						gpPoint.type = GPPoint.Type.LINE;
						gpPoint.ctrlPoint1 = null;
						gpPoint.ctrlPoint2 = null;
						gpData.add(gpPoint.clone());
					}
					
					shapeDataList.add(currentData);
					currentData = shapeChoices[choice].clone();
				} else {
					shapeDataList.add(currentData);
					shapeChoices[choice] = currentData;
					currentData = shapeChoices[choice].clone();
				}
				
				
				repaint();
				
			} else if (e.getKeyCode() == KeyEvent.VK_Z) {
				if (currentData instanceof GPData) {
					GPData gpData = (GPData) currentData;
					gpData.removeLast();
					repaint();
				} else {
					shapeDataList.removeLast();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_L) { //Load draw.txt TODO
				load();
			} else if (e.getKeyCode() == KeyEvent.VK_H) { //Hide / Unhide Image
				hideImage = !hideImage; //toggle
			} else if (e.getKeyCode() == KeyEvent.VK_S) { //Save TODO
				save();
			} else if (e.getKeyCode() == KeyEvent.VK_F) { // Toggle Draw / Fill
				currentData.isDraw = !currentData.isDraw;
			} else if (e.getKeyCode() == KeyEvent.VK_C) {
				currentData.closeGap = !currentData.closeGap;
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				choice = (++choice) % shapeChoices.length;
				currentData = shapeChoices[choice].clone();
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				shapeDataList.clear();
			} else if (e.getKeyCode() == KeyEvent.VK_J) {
				hideDrawing = !hideDrawing;
			} else if (e.getKeyCode() == KeyEvent.VK_K) {
				gpPointChoice = (++gpPointChoice) % gpPointChoices.length;
				currentGPType = gpPointChoices[gpPointChoice];
			} else if (e.getKeyCode() == KeyEvent.VK_N) {
				if (currentData instanceof GPData) {
					updateShapeData();
					repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_I) {
				hideInfo = !hideInfo;
			} else if (e.getKeyCode() == KeyEvent.VK_1) {
				updateColor(0);;
			} else if (e.getKeyCode() == KeyEvent.VK_2) {
				updateColor(1);
			} else if (e.getKeyCode() == KeyEvent.VK_3) {
				updateColor(2);
			} else if (e.getKeyCode() == KeyEvent.VK_4) {
				updateColor(3);
			} else if (e.getKeyCode() == KeyEvent.VK_5) {
				updateColor(4);
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				updateColor(((++colorIndex) % colors.length));
			}
			
			repaint();
		}
	}
	
	private void updateColor(int n) {
		colorIndex = n;
		currentData.color = colors[colorIndex];
	}
	
	private class MyMouseAdapter extends MouseAdapter {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			if (SwingUtilities.isLeftMouseButton(e)) {
				currentPoint = e.getPoint();
			} else if (SwingUtilities.isMiddleMouseButton(e)) {
				ctrlPoint1 = e.getPoint();
			} else if (SwingUtilities.isRightMouseButton(e)) {
				ctrlPoint2 = e.getPoint();
			}
		
			
			if (!(currentData instanceof GPData)) {
				updateShapeData();
			}
			
			if (currentData instanceof GPData) {
				GPData gpData = (GPData)currentData;
				GPPoint gpPoint = gpData.getLast();
				if (gpPoint != null) {
					gpPoint.ctrlPoint1 = ctrlPoint1;
					gpPoint.ctrlPoint2 = ctrlPoint2;
				}
				
				repaint();
			} 
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (currentData instanceof GPData) {
					endPoint = e.getPoint();
					if (ctrlPoint1 == null) {
					ctrlPoint1 = new Point(endPoint.x + -10, endPoint.y);
					}
					
					if (ctrlPoint2 == null) {
						ctrlPoint2 = new Point(endPoint.x, endPoint.y -10);
					}
					return;
				}
				
				startPoint = e.getPoint();
				
				if (ctrlPoint1 == null) {
					ctrlPoint1 = new Point(startPoint.x + -10, startPoint.y);
				}
				
				if (ctrlPoint2 == null) {
					ctrlPoint2 = new Point(startPoint.x, startPoint.y -10);
				}
				
			} else if (SwingUtilities.isMiddleMouseButton(e)) {
				ctrlPoint1 = e.getPoint();
			} else if (SwingUtilities.isRightMouseButton(e)) {
				ctrlPoint2 = e.getPoint();
			}
			//updateShapeData();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (currentData instanceof GPData) {
				return;
			}
			
			if (SwingUtilities.isLeftMouseButton(e)) {
				endPoint = e.getPoint();
			} else if (SwingUtilities.isMiddleMouseButton(e)) {
				ctrlPoint1 = e.getPoint();
			} else if (SwingUtilities.isRightMouseButton(e)) {
				ctrlPoint2 = e.getPoint();
			}
			//currentPoint = null;
			
			//addToList();
		}
	}
	
	private class MyMouseWheelListener implements MouseWheelListener {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int notches = -e.getWheelRotation();
			int newStroke = currentData.stroke + notches;
			
			if (newStroke <= 0) {
				newStroke = 1;
			} else if (newStroke > 10) {
				newStroke = 10;
			}
			
			currentData.stroke = newStroke;
			repaint();
		}
	}
	
	public Object deserialize(String path) {
		Object object = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
			object = ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	public void serialize(String path, Object obj) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(obj);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CartoonDrawing(String title, int width, int height) {
		super(title);
		setSize(width,height);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(new CustomWindowListener());
		addMouseMotionListener(new MyMouseAdapter());
		addMouseListener(new MyMouseAdapter());
		addMouseWheelListener(new MyMouseWheelListener());
		
		addKeyListener(new MyKeyAdapter());
	}
	
	public CartoonDrawing(String title) {
		this(title,640,640);
		load();
	}
	
	public static void main(String[] args) {
		String imagePath = "images/VSpecial.jpg";
		CartoonDrawing app = new CartoonDrawing("Cartoon Drawing");
		app.setImage(imagePath);
		
	}
}