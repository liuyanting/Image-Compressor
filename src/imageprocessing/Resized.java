/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imageprocessing;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;

/**
 *
 * @author andy
 */
public class Resized {
    
    private BufferedImage shrinked;
    
    public Resized(ImagePlus image, double factor) {
        int height = image.getHeight();
        int width = image.getWidth();
        
        shrinked = Scalr.resize(image.getBufferedImage(), 
                                Scalr.Method.AUTOMATIC, 
                                (int) (width * ( 1 - factor / 100) ),
                                (int) (height * ( 1 - factor / 100) ), 
                                Scalr.OP_ANTIALIAS);
    }    
    
    public ImagePlus getResult() {
        return new ImagePlus("Resized Image", shrinked);
    }
}
