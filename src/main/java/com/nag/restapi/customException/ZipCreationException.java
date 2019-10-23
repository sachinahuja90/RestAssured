package com.nag.restapi.customException;

import com.nag.restapi.reporterLogger.ReportLogger;

/* Class Decription - Class define Framework's own custom exception which can be thrown when File extension is not matched.    
 * Created by - Sachin Ahuja
 * Created on - 15th March
 * Modified by
 * Modified on
 * */
@SuppressWarnings("serial")
public class ZipCreationException extends Exception { 
	
    public ZipCreationException(String errorMessage) {
        super("\nError while creating ZIP file for folder :  "+errorMessage);
        ReportLogger.error("Error while creating ZIP file for folder : "+errorMessage);
    }
}