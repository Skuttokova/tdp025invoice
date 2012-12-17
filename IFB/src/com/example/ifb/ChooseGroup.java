package com.example.ifb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseGroup extends Activity{
	
	ListView lv;
	String[] groupArray;
	MYSQLDB db;
	String[] invites;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);
        
        db = new MYSQLDB();
        groupArray = db.getUsersGroups(Globals.clientName); //get array of users groups
        
        lv = (ListView) findViewById(R.id.groupList);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, groupArray);
        lv.setAdapter(arrayAdapter);
        
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                
                String item = ((TextView)view).getText().toString();
                Globals.currentGroup = item;
                Intent intent = new Intent(ChooseGroup.this,ChosenGroup.class);
                startActivity(intent);
            }
        });
        
        getInvites();
    } 
    
    public void getInvites(){
        invites = db.getGroupInvites(Globals.clientName);
        for(int i = 0; i < invites.length; i++){
        	setAcceptance(invites[i]);
        }
    }
    
	public void setAcceptance(final String groupName){
		final Context context = this;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle("New group invite!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You have been invited to the group: "+groupName+".")
				.setCancelable(false)
				.setNegativeButton("Decline", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						db.updateAccepted(Globals.clientName, groupName, -1);
						dialog.cancel();
					}
				})
				.setPositiveButton("Accept",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						db.updateAccepted(Globals.clientName, groupName, 1);
						dialog.cancel();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
    
    public void refresh(View view){
    	groupArray = db.getUsersGroups(Globals.clientName); //get array of users groups
    	
        lv = (ListView) findViewById(R.id.groupList);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, groupArray);
        lv.setAdapter(arrayAdapter);
        
		Toast toast = Toast.makeText(getApplicationContext(), "Refresh!", Toast.LENGTH_SHORT);
		toast.show();
		
		getInvites();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addGroup(View view) {
    	Intent i = new Intent(this, NewGroup.class);  
    	startActivityForResult(i, 0);
    }
}
