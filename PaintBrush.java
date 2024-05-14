import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class PaintBrush extends Applet {
	
	ArrayList<GeoShape> shapes = new ArrayList<>();
	int X1, Y1, X2, Y2;
	Color currentColor = Color.black;
	char currentShape ; 
	boolean Solid = false;
    boolean freeDrawing = false;
	ArrayList<Point> currentPencilPoints = new ArrayList<>();
	
	Button btnRect;
	Button btnLine;
	Button btnOval;
	Button btnPencil;
	Button btnRed;
	Button btnGreen;
	Button btnBlue;
	Button btnBlack;
	Button btnClearAll;
	Checkbox checkSolid;
	
	
	public void init(){
		Button btnRect = new Button("Rectangle");
		
		btnRect.addActionListener(new ShapeButtonListener('r'));
		add(btnRect);
		
		
		Button btnLine = new Button("Line");
		
		btnLine.addActionListener(new ShapeButtonListener('l'));
		add(btnLine);
		
		Button btnOval = new Button("Oval");
		
		btnOval.addActionListener(new ShapeButtonListener('o'));
		add(btnOval);
		
		Button btnPencil = new Button("Pencil");
		btnPencil.addActionListener(new ShapeButtonListener('p'));
		
		add(btnPencil);
		
		Button btnRed = new Button("Red");
		btnRed.addActionListener(new ColorButtonListener(Color.red));
		
		add(btnRed);
		
		Button btnGreen = new Button("Green");
		
		
		btnGreen.addActionListener(new ColorButtonListener(Color.green));
		add(btnGreen);
		
		Button btnBlue = new Button("Blue");
		
		btnBlue.addActionListener(new ColorButtonListener(Color.blue));
		add(btnBlue);
		
		Button btnBlack = new Button("Black");
		btnBlack.addActionListener(new ColorButtonListener(Color.black));
		
		add(btnBlack);
		
		
		Button btnEraser = new Button("Eraser");
		btnEraser.addActionListener(new ShapeButtonListener('e'));
		
		add(btnEraser);
		
		
	
		
		Button btnClearAll = new Button("Clear All");
		
		btnClearAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapes.clear();
				X1=Y1=X2=Y2=0;
                repaint();
            }
        });
		
		
		add(btnClearAll);
		
		Button btnUndo = new Button("Undo");
		
		btnUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if(shapes.size()!=0){
                shapes.remove(shapes.size()-1);
				
				X2 = 0;
				Y2 = 0;
				X1 = 0;
				Y1 = 0;
                repaint();
				}
            }
        });
		
		add(btnUndo);
		
		
		
		Checkbox checkSolid = new Checkbox("Solid");
		
		checkSolid.addItemListener(new ItemListener() {
			
            public void itemStateChanged(ItemEvent e) {
                Solid = checkSolid.getState();
            }
   });
		
		add(checkSolid);
		
		addMouseListener(new MouseListener());
		
        addMouseMotionListener(new MouseMotionListener());
}
	
	
	abstract class GeoShape {
        public abstract void draw(Graphics g);
      }

    class Rectangle extends GeoShape {
        private int x1, y1, x2, y2;
        private Color color;
        private boolean Solid;

        public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean Solid) {
            this.x1 = Math.min(x1, x2);
			
            this.y1 = Math.min(y1, y2);
			
            this.x2 = Math.max(x1, x2);
            this.y2 = Math.max(y1, y2);
			
            this.color = color;
			
            this.Solid = Solid;
         }

        public void draw(Graphics g) {
            g.setColor(color);
			
			
			int width = x2 - x1;
            int height = y2 - y1;
			
			
            if (Solid) {
                g.fillRect(x1, y1, x2 - x1, y2 - y1);
				
				
         } else {
                g.drawRect(x1, y1, x2 - x1, y2 - y1);
           }
       }
    }

    class Line extends GeoShape {
        private int x1, y1, x2, y2;
        private Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
			
            this.y1 = y1;
            this.x2 = x2;
			
            this.y2 = y2;
			
            this.color = color;
       }

        public void draw(Graphics g) {
			
            g.setColor(color);
			
            g.drawLine(x1, y1, x2, y2);
      }
    }

    class Oval extends GeoShape {
        private int x1, y1, x2, y2;
        private Color color;
        private boolean Solid;

        public Oval(int x1, int y1, int x2, int y2, Color color, boolean Solid) {
            this.x1 = Math.min(x1, x2);
			
            this.y1 = Math.min(y1, y2);
			
            this.x2 = Math.max(x1, x2);
            this.y2 = Math.max(y1, y2);
			
			
            this.color = color;
			
            this.Solid = Solid;
          }

        public void draw(Graphics g) {
            g.setColor(color);
			
            int width = x2 - x1;
            int height = y2 - y1;
			
            if (Solid) {
                g.fillOval(x1, y1, width, height);
				
				
         } else {
                g.drawOval(x1, y1, width, height);
              }
    }
	
   }

    class Pencil extends GeoShape {
        private ArrayList<Point> points;
        private Color color;


        public Pencil(ArrayList<Point> points, Color color) {
            this.points = points;
			
            this.color = color;
        }


        public void draw(Graphics g) {
            g.setColor(color);
            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
				
                Point p2 = points.get(i);
				
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
           }
		   
		   
         }
  }



    class Point {
        public int x, y;


        public Point(int x, int y) {
            this.x = x;
            this.y = y;
          }
}



        class Eraser extends GeoShape{
			int x1,x2,y1,y2;
			Color color= Color.WHITE;
			
			
			public Eraser(int x1,int y1,int x2,int y2){
			this.x1=x1;			
			this.x2=x2;
			this.y1=y1;
			this.y2=y2;
			
			}
			
			public void draw(Graphics g){
				g.setColor(color);
				g.fillRect(x1,y1,10,10);
				
			  }
			
			
  }
	
    
    
	
	
	public void paint(Graphics g) {
        for (GeoShape shape : shapes) {
            shape.draw(g);
        }



        if (currentShape == 'p' && freeDrawing && currentPencilPoints.size() > 1) {
            g.setColor(currentColor);
            for (int i = 1; i < currentPencilPoints.size(); i++) {
				
                Point p1 = currentPencilPoints.get(i - 1);
				
                Point p2 = currentPencilPoints.get(i);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
              }
        }


        if (currentShape != 'p' && X1 != X2 && Y1 != Y2) {
            
            switch (currentShape) {
                case 'r':
                    new Rectangle(X1, Y1, X2, Y2, currentColor, Solid).draw(g);
					
                  break;
                case 'l':
				
                    new Line(X1, Y1, X2, Y2, currentColor).draw(g);
					
                      break;
                case 'o':
				
                    new Oval(X1, Y1, X2, Y2, currentColor, Solid).draw(g);
                  break;
           
		   }
        }
 }



class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            X1 = e.getX();
			
            Y1 = e.getY();
			
            X2 = X1;
            Y2 = Y1;
            currentPencilPoints.clear();
			
            currentPencilPoints.add(new Point(X1, Y1));
            repaint();
        }



        public void mouseReleased(MouseEvent e) {
            X2 = e.getX();
            Y2 = e.getY();

            switch (currentShape) {
                case 'r':
				
                    shapes.add(new Rectangle(X1, Y1, X2, Y2, currentColor, Solid));
                      break;
                case 'l':
				
                    shapes.add(new Line(X1, Y1, X2, Y2, currentColor));
					
                    break;
                case 'o':
                    shapes.add(new Oval(X1, Y1, X2, Y2, currentColor, Solid));
					
                        break;
                case 'p':
                    if (freeDrawing) {
						
                        shapes.add(new Pencil(new ArrayList<>(currentPencilPoints), currentColor));
                    }
                 break;
            }

            currentPencilPoints.clear();
			
            repaint();
      }
 }



    class MouseMotionListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
			if(currentShape=='e'){
				shapes.add(new Eraser(e.getX(), e.getY(), 5, 5));
				repaint();
			}
			else{
            X2 = e.getX();
            Y2 = e.getY();

            
            currentPencilPoints.add(new Point(X2, Y2));
            repaint();
			}
        }
   }



    class ShapeButtonListener implements ActionListener {
        private char shape;


        public ShapeButtonListener(char shape) {
            this.shape = shape;
        }



        public void actionPerformed(ActionEvent e) {
            currentShape = shape;
            freeDrawing = (shape == 'p'); 
          }
      }

    class ColorButtonListener implements ActionListener {
        private Color color;



        public ColorButtonListener(Color color) {
            this.color = color;
        }
 
 
 
        public void actionPerformed(ActionEvent e) {
            currentColor = color;
        }
    }

}