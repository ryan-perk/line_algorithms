/**
 * LineDrawin.java
 *
 * @author ryanperkins
 */
import java.io.*;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * LineDrawin.class
 */
public class LineDrawin {
    // number of lines to be drawn
    public static int n;
    
    // which algorithm, 0 = basic, 1 = Bresenham
    public static int alg;
    
    // rainbow?
    private static boolean rainbow;
    
    /**
     * basicAlg method draws a line using the basic line drawing algorithm
     *
     * @param x0 - origin x coordinate
     * @param y0 - origin y coordinate
     * @param x1 - end x coordinate
     * @param y1 - end y coordinate
     * @param g - graphics package
     */
    public static void basicAlg(int x0,  int y0,  int x1,  int y1, Graphics g) {
        // delta x and delta y are the changes between the two x cords. and y cords.
        float dx = x1 - x0;
        float dy = y1 - y0;
        // start points
        float x = x0;
        float y = y0;
        // increment variable
        float incr;
        
        // dominating variable (x or y)
        if(Math.abs(dx) > Math.abs(dy))
            incr = Math.abs(dx);
        else
            incr = Math.abs(dy);
        
        // increments for the x and y variables
        float xIncr = dx/incr;
        float yIncr = dy/incr;
        
        // rainbow
        if(rainbow)
            g.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        
        // draw the line
        for(int i = 0; i < incr; i++) {
            x += xIncr;
            y += yIncr;
            g.drawLine((int)x, (int)y, (int)x, (int)y);
        }
    }
    
    /**
     * brz method draws a line using the Bresenham line drawing algorithm
     *
     * @param x0 - origin x coordinate
     * @param y0 - origin y coordinate
     * @param x1 - end x coordinate
     * @param y1 - end y coordinate
     * @param g - graphics package
     */
    public static void brz(int x0,  int y0,  int x1,  int y1, Graphics g) {
        // delta x and delta y are the changes between the two x cords. and y cords.
        int dx = x1 - x0;
        int dy = y1 - y0;
        // increments and up/down parameter
        int xIncr;
        int yIncr;
        int e;
        // cases
        int c;
        int temp;
        // start points
        int x;
        int y;
        
        // determine if dy < dx, otherwise dy/dx would = 0
        if(Math.abs(dy) < Math.abs(dx)) {
            // if the starting point is in > the end point, the values need to be swapped so it draws left to right
            if(x0 > x1) {
                // swap 'em
                temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
                dx = x1 - x0;
                dy = y1 - y0;
            }
            
            // start points
            x = x0;
            y = y0;
            
            // check for a negative slope
            yIncr = 1;
            if(dy < 0) {
                yIncr = -1;
                dy = -dy;
            }
            
            // establish the up/down parameter
            e = (2 * dy) - dx;
            
            // rainbow
            if(rainbow)
                g.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
            
            // draw the line
            for(int i = x; i < x1; i++) {
                g.drawLine(i, y, i, y);
                
                if(e > 0) {
                    y += yIncr;
                    e -= 2 * dx;
                }
                e += 2 * dy;
            }
        }
        else {
            // if the starting point is in > the end point, the values need to be swapped so it draws left to right
            if(y0 > y1) {
                // swap 'em
                temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
                dx = x1 - x0;
                dy = y1 - y0;
            }
            
            // start points
            x = x0;
            y = y0;
            
            // check for a negative slope
            xIncr = 1;
            if(dx < 0) {
                xIncr = -1;
                dx = -dx;
            }
            
            // establish the up/down parameter
            e = (2 * dx) - dy;
            
            // rainbow
            if(rainbow)
                g.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
            
            // draw the line
            for(int i = y; i < y1; i++) {
                g.drawLine(x, i, x, i);
                
                if(e > 0) {
                    x += xIncr;
                    e -= 2 * dy;
                }
                e += 2 * dx;
            }
        }
    }
    
    /**
     * main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        // scanner initialized to find # of lines
        Scanner scan = new Scanner(System.in);
        System.out.print("# of Lines: ");
        if(scan.hasNextInt())
            n = scan.nextInt();
        else {
            System.out.println("invalid response");
            return;
        }
        
        // which algorithm
        System.out.print("basic(0) or Bresenham(1): ");
        if(scan.hasNextInt())
            alg = scan.nextInt();
        else {
            System.out.println("invalid response");
            return;
        }
        
        // rainbow lines
        System.out.print("rainbow? (true or false): ");
        if(scan.hasNextBoolean())
            rainbow = scan.nextBoolean();
        else {
            System.out.println("invalid response, no rainbow D:");
            return;
        }
        
        // window is created
        JFrame f = new JFrame("Drawin' Lines");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(1600, 900));
        f.pack();
        f.setVisible(true);
        
        // panel with all drawn lines is created and added
        DrawPanel canvas = new DrawPanel();
        f.add(canvas);
    }
}

/**
 * DrawPanel.class
 */
class DrawPanel extends JPanel {
    /**
     * paintComponent method
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        // draws n lines at random x0, y0, x1, y1 with the basic algorithm
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < LineDrawin.n; i++) {
            // int x = (int)(Math.random() * 1600);
            // int y = (int)(Math.random() * 1600);
            if(LineDrawin.alg == 0) {
                 LineDrawin.basicAlg((int)(Math.random() * 1600), (int)(Math.random() * 900), (int)(Math.random() * 1600), (int)(Math.random() * 900), g); // random
                // LineDrawin.basicAlg(x, (int)(Math.random() * 900), x, (int)(Math.random() * 900), g); // vertical
                // LineDrawin.basicAlg((int)(Math.random() * 1600), y, (int)(Math.random() * 1600), y, g); // horizontal
            }
            else if(LineDrawin.alg == 1) {
                LineDrawin.brz((int)(Math.random() * 1600), (int)(Math.random() * 900), (int)(Math.random() * 1600), (int)(Math.random() * 900), g); // random
                // LineDrawin.brz(x, (int)(Math.random() * 900), x, (int)(Math.random() * 900), g); // vertical
                // LineDrawin.brz((int)(Math.random() * 1600), y, (int)(Math.random() * 1600), y, g); // horizontal
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("time: " + (time2 - time1));
    }
}
