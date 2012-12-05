package com.example.ifb;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
			//get value from id
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
	
	public Integer getInvoiceId(String uniqueId){
		String query = "data={\"query\":\"SELECT id FROM `Invoice` WHERE uniqueId='"+uniqueId+"'\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			Integer id = null;
			//get value from id
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
			//get value from id
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
	
	//TODO Check if works
	public HashMap[] getUsersGroups(String userName){
		Integer userid = getUserId(userName);
		String query = "data={\"query\":\"SELECT * FROM `UserInGroup` WHERE userId="+userid+"\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			
			HashMap[] groupMap = new HashMap[jsonArray.length()];
			for(int i=0; i<jsonArray.length(); i++){
				HashMap map = new HashMap();
				map.put("id", Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
				map.put("name", Integer.parseInt(jsonArray.getJSONObject(i).getString("name")));
				groupMap[i] = map;
			}
			
			return groupMap;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	//TODO check if works
	public HashMap getGroupInfo(Integer groupId){
		
		String query = "data={\"query\":\"SELECT * FROM `Group` WHERE id="+groupId+"\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			HashMap map = new HashMap();
			map.put("id",Integer.parseInt(jsonArray.getJSONObject(0).getString("id")));
			map.put("name",jsonArray.getJSONObject(0).getString("name"));
			
			return map;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	public Integer addGroup(String name){
		String query = "data={\"query\":\"INSERT INTO `Group` VALUES('','"+name+"')\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	public Integer addUser(String username, String password) throws NoSuchAlgorithmException{
		//md5 password
		String encPassword = MessageDigest.getInstance(password).toString();
		String query = "data={\"query\":\"INSERT INTO `Users` VALUES('','"+username+"','"+encPassword+"')\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	//Looks up id from names
	public Integer addUserToGroup(String userName, String groupName){
		int userId = getUserId(userName);
		int groupId = getGroupId(groupName);
		
		String query = "data={\"query\":\"INSERT INTO `UserInGroup` VALUES('',"+groupId+","+userId+")\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	//Uses ids from parameters
	public Integer addUserToGroup(Integer userId, Integer groupId){
		String query = "data={\"query\":\"INSERT INTO `UserInGroup` VALUES('',"+groupId+","+userId+")\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	public Integer addPrivInvoice(String userName, Double amount, String desc, String senderName){
		int id = getUserId(userName);
		int senderId = getUserId(senderName);
		String uniqueId = UUID.randomUUID().toString();
		//Add Invoice
		String query = "data={\"query\":\"INSERT INTO `Invoice` VALUES('',"+amount+","+-1+",'"+desc+"',"+senderId+",'"+uniqueId+"')\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Integer invoiceId  = getInvoiceId(uniqueId);
			//Add to UsersInvoices
			query = "data={\"query\":\"INSERT INTO `UsersInvoices` VALUES('',"+invoiceId+","+id+","+0+")\"}";
			json = sendQuery(query);
			if(checkSuccess(json))
				return 1;
			else
				return null;
		}
		else
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
