package com.nag.restapi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.nag.restapi.customException.ZipCreationException;
import com.nag.restapi.reporterLogger.ReportLogger;

/* Class Decription - Class contains all the functions that are common for all the application and tests     
 * Created by - Sachin Ahuja
 * Created on - 27th April
 * Modified by
 * Modified on
 * */

public class Utilities extends FileUtility{
	

   
   /* Function Decription - Function converts a Hashmap<String, Integer> to a Sorted Linked Hashmap in decending Order   
	 * Created by - Sachin Ahuja
	 * Created on - 11th March
	 * Modified by
	 * Modified on
	 * */
    public LinkedHashMap<String, Integer> convertMapToReverseSortedMap(HashMap<String,Integer> map) {
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		  
		 map.entrySet()
		     .stream()
		     .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		     .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		 return sortedMap;
	}

   	public void createZipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
       ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
       Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
               Files.copy(file, zos);
               zos.closeEntry();
               return FileVisitResult.CONTINUE;
           }
       });
       zos.close();
   	}
   	
   	public String dateFormatter(Date date) {
		Format formatter = new SimpleDateFormat("mm-dd-yyyy");
	    String s = formatter.format(date);
	    return s;
	}
   	
   	public Date increamentCurrentDate(int days) {
   		LocalDate date =  LocalDate.now().plusDays(days);
   		Date date2=java.sql.Date.valueOf(date);
	    return date2;
	}
   	
   	public String getCurrentDateTime() {
   		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   		Date date = new Date();   		
   		return dateFormat.format(date);
   	}
   	
   	public void archieveLastReports(String src) throws IOException, ZipCreationException{
   		createZipFolder(src);
   		deleteFileFlder(new File(src));
   		String archiveReportZip=src.replaceAll(ReportLogger.reportPropertyMap.get("htmlReportFolder").replace("/", "")+"", ReportLogger.reportPropertyMap.get("archieveFolder").replace("/", ""))+".zip";
   		new File(System.getProperty("user.dir")+ReportLogger.reportPropertyMap.get("archieveFolder")).mkdir();
   		copyFolder(new File(src+".zip"),new File(archiveReportZip));
   		deleteFileFlder(new File(src+".zip"));
   	}
   	
   	
   	
   	  	
   	
   	
   	
   	
   
   
}