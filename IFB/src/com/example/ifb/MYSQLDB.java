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


//-----------------------Getters-Start-----------------------
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
	
	public String getUserName(Integer id){
		String query = "data={\"query\":\"SELECT name FROM `Users` WHERE id="+id+"\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			String name = null;
			//get value from id
			name = jsonArray.getJSONObject(0).getString("name");
			
			return name;
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
	
	//Gets users in specific group
	public Integer[] getGroupUsers(Integer groupId){
		String query = "data={\"query\":\"SELECT userId FROM `UserInGroup` WHERE groupId="+groupId+" AND accepted=1\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			Integer[] groupArray = new Integer[jsonArray.length()];
			//get value from id
			for(int i = 0; i < jsonArray.length(); i++){
				groupArray[i] = Integer.parseInt(jsonArray.getJSONObject(i).getString("userId"));
			}
			
			return groupArray;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	public String[] getGroupUsersInfo(Integer groupId){
		Integer[] userIdArray = getGroupUsers(groupId);
		String[] userNames = new String[userIdArray.length];
		for(int i = 0; i < userIdArray.length; i++){
			String query = "data={\"query\":\"SELECT name FROM `Users` WHERE id="+userIdArray[i]+"\"}";
			JSONObject json = sendQuery(query);
			if(checkSuccess(json)){
				try {
					JSONArray jsonArray = json.getJSONArray("result");
					userNames[i] = (String) jsonArray.getJSONObject(0).getString("name");
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}
			else
				return null;
		}
		return userNames;	
	}
	
	//Get groups that user is a member of
	public String[] getUsersGroups(String userName){
		Integer userId = getUserId(userName);
		String query = "data={\"query\":\"SELECT * FROM `UserInGroup` WHERE userId="+userId+" AND accepted=1\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			
			String[] groupArray = new String[jsonArray.length()];
			for(int i=0; i<jsonArray.length(); i++){
				
				//Get group-info from groupId
				groupArray[i] = getGroupInfo(Integer.parseInt(jsonArray.getJSONObject(i).getString("groupId")));
			}
			
			return groupArray;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	//See all new group invites
	public String[] getGroupInvites(String userName){
		Integer userId = getUserId(userName);
		String query = "data={\"query\":\"SELECT * FROM `UserInGroup` WHERE userId="+userId+" AND accepted=0\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
			JSONArray jsonArray = json.getJSONArray("result");
			
			String[] groupArray = new String[jsonArray.length()];
			for(int i=0; i<jsonArray.length(); i++){
				
				//Get group-info from groupId
				groupArray[i] = getGroupInfo(Integer.parseInt(jsonArray.getJSONObject(i).getString("groupId")));
			}
			
			return groupArray;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	//Get info of group
	public String getGroupInfo(Integer groupId){
		
		String query = "data={\"query\":\"SELECT * FROM `Group` WHERE id="+groupId+"\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
				JSONArray jsonArray = json.getJSONArray("result");
				return jsonArray.getJSONObject(0).getString("name");
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	//Get invoices you've sent
	public HashMap[] getInvoicesSent(String userName){
		int userId = getUserId(userName);
		String query = "data={\"query\":\"SELECT * FROM `Invoice` WHERE fromUserId="+userId+" and paid=0\"}";
		
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
				JSONArray jsonArray = json.getJSONArray("result");
				
				HashMap[] invoiceMap = new HashMap[jsonArray.length()];
				for(int i=0; i<jsonArray.length(); i++){
					
					//Get group-info from groupId
					HashMap map = new HashMap();
					map.put("id",Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
					map.put("amount",jsonArray.getJSONObject(i).getString("amount"));
					map.put("description",jsonArray.getJSONObject(i).getString("description"));
					
					Integer invoiceId = (Integer) map.get("id");
					Integer[] users = getUserWhoGotInvoice(invoiceId);
					
					//Add users who got invoice
					for(int j=0; j<users.length; j++)
						map.put("user"+j,users[j]);
					invoiceMap[i] = map;
				}
				return invoiceMap;
			
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	//Get id of users who got invoice  
	public Integer[] getUserWhoGotInvoice(Integer invoiceId){
		
		String query = "data={\"query\":\"SELECT * FROM `UsersInvoices` WHERE invoiceId="+invoiceId+" AND `paid`=0\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try{
				JSONArray jsonArray = json.getJSONArray("result");
				
				Integer[] idArray = new Integer[jsonArray.length()];
				for(int i=0; i<jsonArray.length(); i++){
					idArray[i] = Integer.parseInt(jsonArray.getJSONObject(0).getString("userId"));
				}
				
				return idArray;
			}
			catch(JSONException e){
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	public HashMap[] getUnpaidInvoices (String userName, String groupName){
		int userId = getUserId(userName);
		int groupId = getGroupId(groupName);
		String query = "data={\"query\":\"SELECT `invoiceId` FROM `UsersInvoices` WHERE userId="+userId+" AND `paid`=0\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			JSONArray jsonArray;
			try {
				jsonArray = json.getJSONArray("result");
				
				//Have to create ArrayList, will not be able to know the size of the array
				ArrayList<HashMap> invoices = new ArrayList<HashMap>();
				//For each invoice sent to user
				int count = 0;
				for(int i=0; i<jsonArray.length();i++){
					int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("invoiceId"));
					if(isInvoiceApartOfGroup(id,groupId) == 1){
						String query2 = "data={\"query\":\"SELECT * FROM `Invoice` WHERE id="+id+"\"}";
						JSONObject json2 = sendQuery(query2);
						if(checkSuccess(json2)){
							JSONArray jsonArray2 = json2.getJSONArray("result");
							HashMap map = new HashMap();
							map.put("id",Integer.parseInt(jsonArray2.getJSONObject(0).getString("id")));
							map.put("amount",Double.parseDouble(jsonArray2.getJSONObject(0).getString("amount")));
							map.put("description",jsonArray2.getJSONObject(0).getString("description"));
							map.put("fromId",Integer.parseInt(jsonArray2.getJSONObject(0).getString("fromUserId")));
							invoices.add(count, map);
							count++;
						}
						else
							return null;
					}
				}
				HashMap[] newInvoices = invoices.toArray(new HashMap[invoices.size()]);
				return newInvoices;
			} 
			catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
	//
	public Integer isUserMemberOfGroup(Integer userId,Integer groupId){
		final int MEMBER = -1;
		final int INVITED = 2;
		final int NOTMEMBER = 1;
		final int DECLINED = 3;
		
		String query = "data={\"query\":\"SELECT `accepted` FROM `UserInGroup` WHERE userId="+userId+" AND `groupId`="+groupId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try {
				JSONArray jsonArray = json.getJSONArray("result");
				if(jsonArray.length() < 1)
					return NOTMEMBER;
				else if(jsonArray.getJSONObject(0).getInt("accepted") == 1)
					return MEMBER;
				else if(jsonArray.getJSONObject(0).getInt("accepted") == 0)
					return INVITED;
				else if(jsonArray.getJSONObject(0).getInt("accepted") == -1)
					return DECLINED;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return MEMBER;
	}
	
	public Integer isInvoiceApartOfGroup(Integer invoiceId, Integer groupId){
		String query = "data={\"query\":\"SELECT * FROM `Invoice` WHERE id="+invoiceId+" AND `groupId`="+groupId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try {
				JSONArray jsonArray = json.getJSONArray("result");
				if(jsonArray.length() < 1)
					return -1;
				else
					return 1;
			}
			catch (JSONException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}
	
	public Double getTotal(String groupName){
		int groupId = getGroupId(groupName);
		String query = "data={\"query\":\"SELECT SUM(amount) FROM `Invoice` WHERE groupId="+groupId+" AND `paid`='0'\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try {
				JSONArray jsonArray = json.getJSONArray("result");
				return (Double) jsonArray.getJSONObject(0).getDouble("SUM(amount)");
			}
			catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}
	
//-----------------------Getters-End-----------------------	
	
//-----------------------Adders-Start-----------------------
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
	public Integer addUserToGroup(String userName, String groupName, int acceptance){
		int userId = getUserId(userName);
		int groupId = getGroupId(groupName);
		
		int code = isUserMemberOfGroup(userId, groupId);
		
		//Check if already a member of group
		if(code == -1)
			return -1;
		//Check if user already invited
		else if(code == 2)
			return 2;
		//Check if user declined invite
		else if(code == 3)
			return 3;
		
		//Not a member, add
		String query = "data={\"query\":\"INSERT INTO `UserInGroup` VALUES('',"+groupId+","+userId+",'"+acceptance+"')\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	public Integer addInvoice(String groupName, Double amount, String desc, String senderName){
		int groupId = getGroupId(groupName);
		int senderId = getUserId(senderName);
		String uniqueId = UUID.randomUUID().toString();
		//Add Invoice
		String query = "data={\"query\":\"INSERT INTO `Invoice` VALUES('',"+amount+","+groupId+",'"+desc+"',"+senderId+",'"+uniqueId+"','0')\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Integer invoiceId  = getInvoiceId(uniqueId);
			
			//Get array of users in group
			Integer[] groupArray = getGroupUsers(groupId);
			
			int failCount = 0;
			for(int i = 0; i < groupArray.length; i++){
				//Add to UsersInvoices
				query = "data={\"query\":\"INSERT INTO `UsersInvoices` VALUES('',"+invoiceId+","+groupArray[i]+","+0+")\"}";
				json = sendQuery(query);
				if(checkSuccess(json)){
				}
				else
					failCount++;
			}
			if(failCount>0){
				return null;
			}
			else
				return 1;
		}
		else
			return null;
	}
//-----------------------Adders-End-----------------------
	
//-----------------------Removers/Updaters-Start-----------------------
	//Removes user from group
	public Integer leaveGroup(String groupName, String userName){
		int groupId = getGroupId(groupName);
		int userId = getUserId(userName);
		String query = "data={\"query\":\"DELETE FROM `UserInGroup` WHERE `groupId` = "+groupId+" AND `userId` = "+userId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			//If group has no members, remove it
			if(getGroupUsers(groupId).length < 1){
				//Remove group
				if(removeGroup(groupId) != null)
					return 1;
				else
					return null;
			}
			else
				return 1;
		}
		else
			return null;
	}
	
	public Integer removeGroup(Integer groupId){
		String query = "data={\"query\":\"DELETE FROM `Group` WHERE `id` = "+groupId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	//Updates if user has accepted or declined group invite
	public Integer updateAccepted(String userName, String groupName, int choice){
		int userId = getUserId(userName);
		int groupId = getGroupId(groupName);
		String query = "data={\"query\":\"UPDATE `UserInGroup` SET `accepted`="+choice+" WHERE `userId`="+userId+" AND `groupId`="+groupId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json))
			return 1;
		else
			return null;
	}
	
	//TODO test
	//Update invoice 
	public Integer userPaidInvoice(Integer invoiceId, Integer userId){
		
		String query = "data={\"query\":\"UPDATE `UsersInvoices` SET `paid`=1 WHERE `invoiceId`="+invoiceId+" AND `userId="+userId+"\"}";
		JSONObject json = sendQuery(query);
		if(checkSuccess(json)){
			if(getUserWhoGotInvoice(invoiceId).length == 0){
				//Update invoice to fullypaid
				String query2 = "data={\"query\":\"UPDATE `Invoice` SET `paid`=1 WHERE `id`="+invoiceId+"\"}";
				JSONObject json2 = sendQuery(query2);
				if(checkSuccess(json2))
					//Update completed and fullypaid original invoice
					return 1;
				else
					return null;
			}
			
			//Not fully paid, but update completed
			return 1;
		}
		else
			return null;
	}
//-----------------------Removers/Updaters-End-----------------------
	
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
