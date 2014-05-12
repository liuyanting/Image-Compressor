/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileaccess;

/**
 *
 * @author andy
 */
public class FileFormat {
    private final String extensions[] = { "jpg", "tif", "png" };
    private final String potentialKeywords[] = { "jpeg", "tiff" };
    
    public String[] getAcceptableFileFormat() {
        return extensions;
    }
    
    private String[] concate(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;
        String[] C= new String[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }
    
    public String[] getFilterKeyword() {
        return concate(extensions, potentialKeywords);
    }
}
