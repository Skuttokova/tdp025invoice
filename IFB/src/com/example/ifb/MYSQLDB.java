package com.example.ifb;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

public class MYSQLDB {
	//http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
	
	JSONParser jParser= new JSONParser();
	
	private static String db_url = "http://54.247.71.173/IFB/data.php";

	
	public Integer getUserId(String name){
		String query = "data={\"query\":\"SELECT id FROM `Users` WHERE name='"+name+"'\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			Integer id = null;
			//TODO get value from id
			id = Integer.parseInt(jsonArray.getJSONObject(0).getString("id"));
			
			return id;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	public Integer getGroupId(String name){
		String query = "data={\"query\":\"SELECT id FROM `Group` WHERE name='"+name+"'\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			Integer id = null;
			//TODO get value from id
			id = Integer.parseInt(jsonArray.getJSONObject(0).getString("id"));
			
			return id;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	
	//TODO Finish
	public Integer addUser(String username, String password) throws NoSuchAlgorithmException{
		//md5 password
		String encPassword = MessageDigest.getInstance(password).toString();
		String query = "data={\"query\":\"INSERT INTO Users ('name','password') VALUES("+username+","+encPassword+")\"}";
		
		JSONObject json = sendQuery(query);
		return null;
	}
	
	
	public JSONObject sendQuery(String query){
		//Add password
		query += "&password=1234";
		
		//Replace characters
		//<space>,{,},"
		query = query.replace(" ","%20").replace("{", "%7B").replace("}","%7D").replace(":", "%3A").replace("\"", "%22").replace("`", "%60");
		
		JSONObject jQuery = new JSONObject();
		try {
			jQuery.put("query", query);
		} catch (JSONException e) {
			
		}
		JSONObject json = jParser.makeHttpRequest(db_url, "GET", query);
		return json; 
	}
	
	public boolean checkSuccess(JSONObject json){
		try{
			if(json == null){
				//Server/client connection error
				return false;
			}
			else if(json.getInt("success")==1){
				return true;
			}
			else{
				//No rows matched
				return false;
			}
		}
		catch (JSONException e){
			e.printStackTrace();
			return false;
		}
	}
}
