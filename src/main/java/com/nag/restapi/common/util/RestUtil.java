package com.nag.restapi.common.util;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* Class Decription - Class contains all the functions that are common for all the application and tests     
 * Created by - Sachin Ahuja
 * Created on - 27th April
 * Modified by
 * Modified on
 * */

public class RestUtil {
	

   /* Function Decription - Function cenverts a raw response object to a XmlPath Object which is easy to iterate   
 	 * Created by - Sachin Ahuja
 	 * Created on - 11th March
 	 * Modified by
 	 * Modified on
	 * */
	
   public static XmlPath rawToXML(Response r) {
      XmlPath x = new XmlPath(r.asString());
      return x;
   }

   /* Function Decription - Function cenverts a raw response object to a JsonPath Object which is easy to iterate   
	 * Created by - Sachin Ahuja
	 * Created on - 11th March
	 * Modified by
	 * Modified on
	 * */
	
   public static JsonPath rawToJson(Response r) {
      JsonPath x = new JsonPath(r.asString());
      return x;
   }
   
   /* Function Decription - Function helps user to read XML file and convert into a String file which is returned by the function   
 	 * Created by - Sachin Ahuja
 	 * Created on - 11th March
 	 * Modified by
 	 * Modified on
	 * */
   public static String xmlToString(String path) throws IOException {
      return new String(Files.readAllBytes(Paths.get(path)));
   }
   
   
   /* Function Decription - Function converts a Hashmap<String, Integer> to a Sorted Linked Hashmap in decending Order   
	 * Created by - Sachin Ahuja
	 * Created on - 11th March
	 * Modified by
	 * Modified on
	 * */
   static public LinkedHashMap<String, Integer> convertMapToReverseSortedMap(HashMap<String,Integer> map) {
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		  
		 map.entrySet()
		     .stream()
		     .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		     .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		 return sortedMap;
	}
   
}