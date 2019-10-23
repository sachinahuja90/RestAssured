package com.nag.restapi.customException;

/* Class Decription - Class define Framework's own custom exception which can be thrown when File extension is not matched.    
 * Created by - Sachin Ahuja
 * Created on - 15th March
 * Modified by
 * Modified on
 * */
@SuppressWarnings("serial")
public class IncorrectFileExtensionException extends Exception { 
	
    public IncorrectFileExtensionException(String errorMessage) {
        super(errorMessage);
    }
}