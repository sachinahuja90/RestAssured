package com.nag.restapi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.nag.restapi.customException.ZipCreationException;
import com.nag.restapi.reporterLogger.ReportLogger;

public class ZipUtils {

    private static List <String> fileList;
    private static String OUTPUT_ZIP_FILE;
    private static String SOURCE_FOLDER; // SourceFolder path

    public ZipUtils() {
        fileList = new ArrayList < String > ();
    }
    
	/*
	 * public static void main(String args[]) throws ZipCreationException { ZipUtils
	 * zip=new ZipUtils();
	 * zip.createZipFolder("C:\\Users\\sachinahuja\\Desktop\\NAGP Sessions"); }
	 */
    public void createZipFolder(String src) throws ZipCreationException {
    	if(new File(src).exists()) {
    		SOURCE_FOLDER=src;
	    	OUTPUT_ZIP_FILE=src+".zip";
	        generateFileList(new File(SOURCE_FOLDER));
	        zipIt(OUTPUT_ZIP_FILE);
	    }
    }

    private void zipIt(String zipFile) throws ZipCreationException {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            
            FileInputStream in = null;

            for (String file: ZipUtils.fileList) {
                
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            zos.closeEntry();
            ReportLogger.LOGGER.info("ZIP file created for folder : "+SOURCE_FOLDER);
        } catch (IOException ex) {
            throw new ZipCreationException(SOURCE_FOLDER); 
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
            	ReportLogger.LOGGER.error(e.toString());
            }
        }
    }

    private void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}