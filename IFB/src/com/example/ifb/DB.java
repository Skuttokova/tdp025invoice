package com.example.ifb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
//http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DB extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "IFB";
 
    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//Users table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Users" + "("
                + "id" + " INTEGER PRIMARY KEY," + "name" + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        //Groups table
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Groups" + "("
                + "id" + " INTEGER PRIMARY KEY," + "name" + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
       //UserInGroups table
       CREATE_CONTACTS_TABLE = "CREATE TABLE " + "UserInGroups" + "("
                + "groupId" + " INTEGER," + "userId" + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        //Invoice table
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + "Invoice" + "("
                 + "id" + " INTEGER," + "amount" + " DOUBLE, " + "description" + "TEXT," + "groupId" + "INTEGER,"
        		+ "userId" + "INTEGER" + ")";
         db.execSQL(CREATE_CONTACTS_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void addUser(String name) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("name", name); // User Name
	 
	    // Inserting Row
	    db.insert("Users", null, values);
	    db.close(); // Closing database connection
	}
	
	public void addGroup(String name) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put("name", name); // Group Name
	 
	    // Inserting Row
	    db.insert("Groups", null, values);
	    db.close(); // Closing database connection
	}
	
   public void addUserToGroup(String group, String name) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    //TODO Get user id (make read function)
	    int userId = 0;
	    
	    //TODO Get group id
	    int groupId = 0;
	    
	    ContentValues values = new ContentValues();
	    values.put("groupId", groupId); // Group Name
	    values.put("userId", userId); // User Name
	 
	    // Inserting Row
	    db.insert("UserInGroups", null, values);
	    db.close(); // Closing database connection
	}
	
	public void addInvoice(String toUsername, double amount, String description, int groupId) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    //TODO get userId with toUsername
	    int userId = 0;
	    
	    ContentValues values = new ContentValues();
	    values.put("amount", amount);
	    values.put("description", description);
	    values.put("groupId",groupId);
	    values.put("userId",userId);
	 
	    // Inserting Row
	    db.insert("Groups", null, values);
	    db.close(); // Closing database connection
	}
	
	public void addInvoice(String toUsername, double amount, String description) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	  //TODO get userId with toUsername
	    int userId = 0;
	    
	    ContentValues values = new ContentValues();
	    values.put("amount", amount);
	    values.put("description", description);
	    values.put("groupId",-1);
	    values.put("userId",userId);
	 
	    // Inserting Row
	    db.insert("Groups", null, values);
	    db.close(); // Closing database connection
	}
	

    
}