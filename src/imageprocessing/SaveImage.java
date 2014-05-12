/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imageprocessing;

import ij.ImagePlus;
import ij.io.FileSaver;
import java.io.File;

/**
 *
 * @author andy
 */
public class SaveImage {
    
    private String new_extension, newPath;
    
    private String parseFileFormat(String absPath) {
        String extension = "";
        
        int i = absPath.lastIndexOf('.');
        int p = Math.max(absPath.lastIndexOf('/'), absPath.lastIndexOf('\\'));

        if (i > p) {
            extension = absPath.substring(i+1);
        }
        
        return extension.toLowerCase();
    }
    
    private void checkPathExistance(String path) {
        File check = new File(path);
        if(!check.exists())
            check.mkdirs();
    }
    
    // move to new directory, and replace the extension
    private String generateNewPath(String originalPath, File originalDir, File targetDir, String fileFormat) {
        String new_path_name = originalPath.replace(originalDir.getAbsolutePath(), targetDir.getAbsolutePath());
        
        String original_extension = parseFileFormat(originalPath);
        new_extension = (fileFormat.equals("default")) ? original_extension : fileFormat;

        String original_name = (new File(originalPath)).getName();
        String new_name = original_name.replace(original_extension, new_extension);
        
        String result = new_path_name.replace(original_name, new_name);
        checkPathExistance(result.replace(new_name, ""));
        return result;
    }
    
    public String getNewPath() {
        return newPath;
    }
    
    public long getFileSize() {
        File file = new File(newPath);
        return file.length();
    }
    
    public SaveImage(ImagePlus img, String originalPath, File originalDir, File targetDir, String fileFormat) {
        FileSaver saver = new FileSaver(img);
        
        newPath = generateNewPath(originalPath, originalDir, targetDir, fileFormat);

        if(new_extension.equals("jpg") || new_extension.equals("jpeg")) {
            saver.saveAsJpeg(newPath);
        } else if(new_extension.equals("tif") || new_extension.equals("tiff")) {
            saver.saveAsTiff(newPath);
        } else if(new_extension.equals("png")) {
            saver.saveAsPng(newPath);
        }
    }
}
