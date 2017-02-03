
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.lang.Math;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Keinan
 */
public class MyPanel extends JPanel{
    JPanel jp = new JPanel();
    int[] colorArray;
    BufferedImage grid;
    
    MyPanel(int[] array) {
        colorArray = array;
        jp.setBackground(Color.white);
        int w = jp.getWidth();
        int h = jp.getHeight();
        grid = (BufferedImage)(this.createImage(w,h));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(grid, null, 0, 0);
        
        double highestValue = Integer.MIN_VALUE;
        for(int i = 0; i < colorArray.length; i++) {
            if(colorArray[i] > highestValue) {
                highestValue = (double) colorArray[i];
            }
        }
        
        for(int i = 0; i < colorArray.length; i++) {
            int xStart = i*(this.getWidth()/colorArray.length);
            int yStart = this.getHeight();
            int xEnd = i*(this.getWidth()/colorArray.length);
            int yEnd = (int)((colorArray[i]/highestValue)*yStart);
            
            Line2D lin = new Line2D.Float(xStart, yStart, xEnd, yEnd);
            g2.drawLine(xStart, yStart, xEnd, yEnd);
        }
        jp.repaint();
    }
}
