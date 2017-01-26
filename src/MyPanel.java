
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

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
    
    JPanel jp = null;
    int[] colorArray;
    BufferedImage grid;
    Graphics2D gc;
    
    
    MyPanel(int[] array) {
        colorArray = array;
        jp.setBackground(Color.white);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D)g;
        if(grid == null){
           int w = this.getWidth();
           int h = this.getHeight();
           grid = (BufferedImage)(this.createImage(w,h));
           gc = grid.createGraphics();
        }
        g2.drawImage(grid, null, 0, 0);
    }
    
    private void drawHistogram() {
        for(int i = 0; i < colorArray.length; i++) {
            int xStart = i;
            int yStart = 0;
            int xEnd = i;
            int yEnd = colorArray[i];
            Line2D lin = new Line2D.Float(xStart, yStart, xEnd, yEnd);
            gc.draw(lin);
        }
    }
}
