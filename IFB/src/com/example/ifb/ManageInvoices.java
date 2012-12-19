package com.example.ifb;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class ManageInvoices extends Activity {
	
	MYSQLDB db = new MYSQLDB();
	String[] groupMembers;
	ListView lv;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_invoices);
        
        //extView mTextView = (TextView)findViewById(R.id.listView1);
        groupMembers = db.getGroupUsersInfo(db.getGroupId(Globals.currentGroup));
        
    //    ArrayAdapter<String> arrayAdapter =      
     //           new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, groupMembers);
      //          lv.setAdapter(arrayAdapter);
       
	}
	
}