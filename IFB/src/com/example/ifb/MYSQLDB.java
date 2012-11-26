package com.example.ifb;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MYSQLDB {
	//http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
	
	JSONParser jParser= new JSONParser();
	
	private static String db_url = "http://54.247.71.173/IFB/data.php";

	public void getUserId(String userName){
		String query = "data={\"query\":\"SELECT id FROM Users WHERE name='"+userName+"'\"}";
		
		JSONObject json = sendQuery(query);
		JSONArray result = checkSuccess(json);
	}
	
	public JSONObject sendQuery(String query){
		//Add password
		query += "&password=1234";
		
		//Replace characters
		//<space>,{,},"
		query = query.replace(" ","%20").replace("{", "%7B").replace("}","%7D").replace(":", "%3A").replace("\"", "%22");
		
		JSONObject jQuery = new JSONObject();
		try {
			jQuery.put("query", query);
		} catch (JSONException e) {
			
		}
		JSONObject json = jParser.makeHttpRequest(db_url, "GET", query);
		return json; 
	}
	
	public JSONArray checkSuccess(JSONObject json){
		JSONArray result = null;
		try{
			if(json == null){
				return null;
			}
			else if(json.getInt("success")==1){
				result = json.getJSONArray("result");
				
				return result;
			}
			else{
				return null;
			}
		}
		catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
}
