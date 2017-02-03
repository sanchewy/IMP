/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

import javax.swing.*;
import java.awt.*;
import static java.awt.Color.blue;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;

class IMP implements MouseListener {

    JFrame frame;
    JPanel mp;
    JButton start;
    JScrollPane scroll;
    JMenuItem openItem, exitItem, resetItem;
    Toolkit toolkit;
    File pic;
    ImageIcon img;
    int colorX, colorY;
    int[] pixels;
    int[] results;
   //Instance Fields you will be using below

    //This will be your height and width of your 2d array
    int height = 0, width = 0;
    int resHeight = 0, resWidth = 0;

    //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
    IMP() {
        toolkit = Toolkit.getDefaultToolkit();
        frame = new JFrame("Image Processing Software by Hunter");
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu functions = getFunctions();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                quit();
            }
        });
        openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                handleOpen();
            }
        });
        resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                reset();
            }
        });
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                quit();
            }
        });
        file.add(openItem);
        file.add(resetItem);
        file.add(exitItem);
        bar.add(file);
        bar.add(functions);
        frame.setSize(600, 600);
        mp = new JPanel();
        mp.setBackground(new Color(0, 0, 0));
        scroll = new JScrollPane(mp);
        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        JPanel butPanel = new JPanel();
        butPanel.setBackground(Color.black);
        start = new JButton("start");
        start.setEnabled(false);
        butPanel.add(start);
        frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
        frame.setJMenuBar(bar);
        frame.setVisible(true);
    }

    /* 
     * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
     * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
     */
    private JMenu getFunctions() {
        JMenu fun = new JMenu("Functions");
        JMenuItem firstItem = new JMenuItem("Grayscale");
        JMenuItem secondItem = new JMenuItem("Rotate 90");
        JMenuItem thirdItem = new JMenuItem("Histogram");
        JMenuItem fourthItem = new JMenuItem("3x3 Edge Det");
        JMenuItem fifthItem = new JMenuItem("Track Object");
        JMenuItem sixthItem = new JMenuItem("Equalize");

        firstItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                grayscale();
            }
        });
        secondItem.addActionListener(new ActionListener() {
            //@Overried
            public void actionPerformed(ActionEvent evt) {
                rotate90();
            }
        });
        thirdItem.addActionListener(new ActionListener() {
            //@Overried
            public void actionPerformed(ActionEvent evt) {
                histogram();
            }
        });
        fourthItem.addActionListener(new ActionListener() {
            //@Overried
            public void actionPerformed(ActionEvent evt) {
                edgeDetection();
            }
        });
        fifthItem.addActionListener(new ActionListener() {
            //@Overried
            public void actionPerformed(ActionEvent evt) {
                objTrack();
            }
        });
        sixthItem.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent evt) {
                equalize();
            }
        });
        fun.add(firstItem);
        fun.add(secondItem);
        fun.add(thirdItem);
        fun.add(fourthItem);
        fun.add(fifthItem);
        fun.add(sixthItem);
        return fun;

    }

    /*
     * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
     * You don't need to worry about this method. 
     */
    private void handleOpen() {
        img = new ImageIcon();
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            pic = chooser.getSelectedFile();
            img = new ImageIcon(pic.getPath());
        }
        width = resWidth = img.getIconWidth();
        height = resHeight = img.getIconHeight();

        JLabel label = new JLabel(img);
        label.addMouseListener(this);
        pixels = new int[width * height];

        results = new int[width * height];

        Image image = img.getImage();

        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("Interrupted waiting for pixels");
            return;
        }
        for (int i = 0; i < width * height; i++) {
            results[i] = pixels[i];
        }
        turnTwoDimensional();
        mp.removeAll();
        mp.add(label);

        mp.revalidate();
    }

    /*
     * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
     * So this method changes the one dimensional array to a two-dimensional. 
     */
    private void turnTwoDimensional() {
        picture = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                picture[i][j] = pixels[i * width + j];
            }
        }
    }
    /*
     *  This method takes the picture back to the original picture
     */

    private void reset() {
        height = resHeight;
        width = resWidth;
        for (int i = 0; i < width * height; i++) {
            pixels[i] = results[i];
        }
        Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));

        turnTwoDimensional();
        JLabel label2 = new JLabel(new ImageIcon(img2));
        label2.addMouseListener(this);
        mp.removeAll();
        mp.add(label2);
        mp.repaint();
        mp.revalidate();
    }
    /*
     * This method is called to redraw the screen with the new image. 
     */

    private void resetPicture() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i * width + j] = picture[i][j];
            }
        }
        Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width));

        JLabel label2 = new JLabel(new ImageIcon(img2));
        mp.removeAll();
        mp.add(label2);
        mp.repaint();
        mp.revalidate();

    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */

    private int[] getPixelArray(int pixel) {
        int temp[] = new int[4];
        temp[0] = (pixel >> 24) & 0xff;
        temp[1] = (pixel >> 16) & 0xff;
        temp[2] = (pixel >> 8) & 0xff;
        temp[3] = (pixel) & 0xff;
        return temp;

    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */

    private int getPixels(int rgb[]) {
        int rgba = (rgb[0] << 24) | (rgb[1] << 16) | (rgb[2] << 8) | rgb[3];
        return rgba;
    }

    public int[] getValue() {
        int pix = picture[colorY][colorX];
        int temp[] = getPixelArray(pix);
        return temp;
    }

    /**
     * ************************************************************************************************
     * This is where you will put your methods. Every method below is called
     * when the corresponding pulldown menu is used. As long as you have a
     * picture open first the when your fun1, fun2, fun....etc method is called
     * you will have a 2D array called picture that is holding each pixel from
     * your picture. 
   ************************************************************************************************
     */
    /*
     * Example function that just removes all red values from the picture. 
     * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
     * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
     * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
     * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
     * integer value so you can give it back to the program and display the new picture. 
     */

    /*
     * fun2
     * This is where you will write your STACK
     * All the pixels are in picture[][]
     * Look at above fun1() to see how to get the RGB out of the int (getPixelArray)
     * and then put the RGB back to an int (getPixels)
     */
    /*
     * Take color image and turn it grayscale.
     */
    private void grayscale() {
        // 0.21 R + 0.72 G + 0.07 B
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgbArray[] = new int[4];

                //get three ints for R, G and B
                rgbArray = getPixelArray(picture[i][j]);

           //rgbArray[2] = 0;
                //take three ints for R, G, B and put them back into a single int
                double gval = rgbArray[1] * .21 + rgbArray[2] * .72 + rgbArray[3] * .07;
                rgbArray[1] = (int) gval;
                rgbArray[2] = (int) gval;
                rgbArray[3] = (int) gval;

                picture[i][j] = getPixels(rgbArray);
            }
        }
        resetPicture();
    }

    /*
     * Turn photo 90 degrees clockwise
     */
    private void rotate90() {
        int newImage[][] = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newImage[j][height - i - 1] = picture[i][j];
            }
        }
        int temp = height;
        height = width;
        width = temp;
        picture = newImage;
        resetPicture();
    }

    /*
     * Show histogram of colors in image in 3 seperate windows.
     */
    private void histogram() {
        //First count all pixel values in R and G and B array
        int colorCounter[][] = new int[3][256];  //[color][intensity value]
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int argb[] = new int[4];
                argb = getPixelArray(picture[i][j]);
                colorCounter[0][argb[1]]++; //Red
                colorCounter[1][argb[2]]++; //Green
                colorCounter[2][argb[3]]++; //Blue
            }
        }
        JFrame redFrame = new JFrame("Red");
        redFrame.setSize(305, 600);
        redFrame.setLocation(800, 0);
        JFrame greenFrame = new JFrame("Green");
        greenFrame.setSize(305, 600);
        greenFrame.setLocation(1150, 0);
        JFrame blueFrame = new JFrame("blue");
        blueFrame.setSize(305, 600);
        blueFrame.setLocation(1450, 0);
        MyPanel redPanel = new MyPanel(colorCounter[0]);
        JPanel greenPanel = new MyPanel(colorCounter[1]);
        JPanel bluePanel = new MyPanel(colorCounter[2]);
        redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
        redFrame.setVisible(true);
        greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
        greenFrame.setVisible(true);
        blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
        blueFrame.setVisible(true);
        start.setEnabled(true);

    //System.out.println("Red Counter - "+redTempCounter);
        //Then pass those arrays to MyPanel constructor
        //Then when button is pushed call drawHistogram in MyPanel.....you write DrawHistogram
        //Don't forget to call repaint();
    }

    private void equalize() {
        int colorCounter[][] = new int[3][256];  //[color][intensity value]
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int argb[] = new int[4];
                argb = getPixelArray(picture[i][j]);
                colorCounter[0][argb[1]]++; //Red
                colorCounter[1][argb[2]]++; //Green
                colorCounter[2][argb[3]]++; //Blue
            }
        }
        for (int i = 0; i < colorCounter.length; i++) {
            float freqSum = 0;
            float totalPixels = height * width;
            for (int j = 0; j < colorCounter[i].length; j++) {
                freqSum += colorCounter[i][j];
                colorCounter[i][j] = Math.round((freqSum / totalPixels) * ((float) 255));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int orgInt[] = getPixelArray(picture[i][j]);    //Get original image intensity values
                //Use these values to reference replacement values in colorCounter and create new value array
                int argb[] = new int[]{255, colorCounter[0][orgInt[1]], colorCounter[1][orgInt[2]], colorCounter[2][orgInt[3]]};
//                System.out.print("red: "+argb[1]+", green: "+argb[2]+", blue: "+argb[3]+" Take 2::   ");
                picture[i][j] = getPixels(argb);        //Turn value array into pixel
//                argb = getPixelArray(picture[i][j]);
//                System.out.println("red: "+argb[1]+", green: "+argb[2]+", blue: "+argb[3]);
            }
        }
        resetPicture();
    }

    private void edgeDetection() {
    }

    private void objTrack() {
        int thresh = 20;
        int track[] = getValue();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int argb[] = getPixelArray(picture[i][j]);
                if( (argb[1] > track[1]-thresh && argb[1] < track[1]+thresh) 
                        && (argb[2] > track[2]-thresh && argb[2] < track[2]+thresh)
                        && (argb[3] > track[3]-thresh && argb[3] < track[3]+thresh)) {
                    picture[i][j] = getPixels(new int[]{255, 255, 255, 255});   //Pixel is should be displayed
                } else{
                    picture[i][j] = getPixels(new int[]{255, 0, 0, 0}); //Pixel should not be displayed
                }
            }
        }
        resetPicture();
    }

    private void quit() {
        System.exit(0);
    }

    // Mouse listeners
    @Override
    public void mouseEntered(MouseEvent m) {
    }

    @Override
    public void mouseExited(MouseEvent m) {
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }

    @Override
    public void mousePressed(MouseEvent m) {
    }

    @Override
    public void mouseReleased(MouseEvent m) {
    }

    public static void main(String[] args) {
        IMP imp = new IMP();
    }

}
