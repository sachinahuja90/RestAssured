package com.nag.restapi.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtility extends ZipUtils {
	

   	/**
     * This function recursively copy all the sub folder and files from sourceFolder to destinationFolder
     * */
    public void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
    	if(sourceFolder.exists()) {
    		
	        //Check if sourceFolder is a directory or file
	        //If sourceFolder is file; then copy the file directly to new location
	        if (sourceFolder.isDirectory())
	        {
	            //Verify if destinationFolder is already present; If not then create it
	            if (!destinationFolder.exists())
	            {
	                destinationFolder.mkdir();
	            }
	             
	            //Get all files from source directory
	            String files[] = sourceFolder.list();
	             
	            //Iterate over all files and copy them to destinationFolder one by one
	            for (String file : files)
	            {
	            	if(!file.equalsIgnoreCase("archive")) {
	                File srcFile = new File(sourceFolder, file);
	                File destFile = new File(destinationFolder, file);
	                 
	                //Recursive function call
	                copyFolder(srcFile, destFile);
	                //srcFile.delete();
	            	}
	            }
	           // sourceFolder.delete();
	        }
	        else
	        {
	            //Copy the file content from one place to another
	            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            //sourceFolder.delete();
	            
	        }
    	}
    }
    
            
    public void deleteFileFlder(File file) {
            //to end the recursive loop
    	if (!file.exists())
    		return;
            
        //if directory, go inside and call recursively
    	if (file.isDirectory()) {
    		for (File f : file.listFiles()) {
            //call recursively
    			deleteFileFlder(f);
            }
    	}
        //call delete to delete files and empty directory
    	file.delete();
    }

    
    
    
}
