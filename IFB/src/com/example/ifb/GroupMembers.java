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

public class GroupMembers extends Activity {
    MYSQLDB db = new MYSQLDB();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_members);
        TextView mTextView = (TextView) findViewById(R.id.groupMembersTextView);
        mTextView.setText(Globals.currentGroup);
        
       // String[] members = db.getGroupUsersInfo(db.getGroupId(Globals.currentGroup));
        String[] members = new String[] {"member","member","member","member","member","member","member"};
        
        
        ListView lv = (ListView) findViewById(R.id.groupMembersListView);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, members);
        lv.setAdapter(arrayAdapter);
	}
    
    public void addUser (View view){

    	EditText mUser = (EditText)findViewById(R.id.groupMembersNameField);
    	
		//If no username added
		if(mUser.getText().toString().equals("")){
			dialogMessage("Add user!","Please write a username to add to the group!");
		}
		
		//All clear, add user
		else{
			String userName = mUser.getText().toString();
			
			//add check to see if user exists
			if(db.getUserId(userName) != null){
			
				//Add user to group
				if(db.addUserToGroup(userName, Globals.currentGroup) == 1){
					Toast toast = Toast.makeText(getApplicationContext(), "User added to group", Toast.LENGTH_SHORT);
					toast.show();
					
					//Clear edittext field
					mUser.setText("");
				}
				else if(db.addUserToGroup(userName, Globals.currentGroup) == -1){
					dialogMessage("Error!","User already a member of the group!");
				}
				else{
					dialogMessage("Error!","Could not add user to the group at the moment! Please try again later.");
				}
			}
			else{
				dialogMessage("User error!","User does not exist!");
				//Clear edittext field
				mUser.setText("");
			}
				
		}
    }
    
	public void dialogMessage(String title, String message){
		final Context context = this;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle(title);
 
			// set dialog message
			alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Okey",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
}
