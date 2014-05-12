/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileaccess;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author andy
 */
public class GetDirContents {
    private final DefaultListModel<String> listModel = new DefaultListModel();
    private JFileChooser chooser;
    private File selectedDir;
    private final FileFilter filter;
    
    private String parseFileFormat(String absPath) {
        String extension = "";
        
        int i = absPath.lastIndexOf('.');
        int p = Math.max(absPath.lastIndexOf('/'), absPath.lastIndexOf('\\'));

        if (i > p) {
            extension = absPath.substring(i+1);
        }
        
        return extension.toLowerCase();
    }
    
    private boolean filterContents(File file) {
        boolean result = false;
        
        FileFormat comparison = new FileFormat();
        String template = parseFileFormat(file.getAbsolutePath());
        for(String known : new FileFormat().getFilterKeyword()) {
            if(template.equals(known)) {
                result = true;
                break;
            }
        }
        
        if(file.getName().startsWith("."))
            result = false;
        
        return result;
    }
    
    private void parseContents(File dir) { 
        File[] files = dir.listFiles();
        if(files != null) {
            for(File f : files) {
                if(f.isDirectory())
                    parseContents(f);
                else {
                    if(filterContents(f))
                        listModel.addElement(f.getAbsolutePath());
                }
            }
        }
    }
    
    public DefaultListModel<String> getListModel() {       
        return listModel;
    }
    
    public File getSelectedDir() {
        return selectedDir;
    }
    
    public GetDirContents(String titleName) {
        this.filter = new FileNameExtensionFilter("Acceptable image type", new FileFormat().getAcceptableFileFormat());
        chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if(getSelectedFile().isFile()) {
                    JOptionPane.showMessageDialog(chooser, 
                                                  "You should choose directory only!", 
                                                  "Wrong Selection", 
                                                  JOptionPane.ERROR_MESSAGE);
                } else {
                    selectedDir = getSelectedFile();
                    parseContents(selectedDir);
                    super.approveSelection();
                }
            }
            
            @Override
            public void cancelSelection() {
                selectedDir = null;
                super.cancelSelection();
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle(titleName);
        chooser.setFileFilter(filter);
        chooser.showDialog(this.chooser, "Choose");
    }
}
