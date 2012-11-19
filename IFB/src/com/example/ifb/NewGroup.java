package com.example.ifb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class NewGroup extends Activity {
    DB database = new DB(this);
    
    String groupName = null;
    int groupId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group);
    }

    public void clickAddGroup(View view){
		EditText mGroup;
	
		mGroup   = (EditText)findViewById(R.id.groupNameText);
		groupName = mGroup.getText().toString();
		
		//If no groupname
		if(groupName.equals("")){
			dialogMessage("Add group name!","Please add a group name!");
		}
		
		//Check if group already exists
		
		else if(database.getGroupId(groupName) != null){
			
		}
		
		//All clear, add group
		else{
			database.addGroup(groupName);
			
			//TODO add self to group
			//
			
			//Show toast
			Context context = getApplicationContext();
			CharSequence text = "Group created!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			//Disable button
			Button mButton;
			mButton = (Button)findViewById(R.id.groupAddButton);
			mButton.setEnabled(false);
			
			groupId = database.getGroupId(groupName);
		}
    }
    	
	public void clickAddUser(View view){
		
		EditText mUser;
		mUser   = (EditText)findViewById(R.id.addUserText);
		
		//If no groupName added
		/*if(groupName == null){
			dialogMessage("Add grouop!","Please create a group before adding users!");
		}*/
		
		//If no username added
		//else 
		if(mUser.getText().toString().equals("")){
			dialogMessage("Add user!","Please write a username to add to the group!");
		}
		
		//All clear, add user
		else{
			String userName = mUser.getText().toString();
			
			//TODO add check to see if user exists
			MYSQLDB db = new MYSQLDB();
			db.getUserId(userName);
			
			if(database.getUserId(userName) != null){
			
				//Add user
				database.addUser(userName);
				
				//Show toast
				Context context = getApplicationContext();
				CharSequence text = "User added to group";
				int duration = Toast.LENGTH_SHORT;
	
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
				//Clear edittext field
				mUser.setText("");
			}
			else{
				dialogMessage("User error!","User does not exist!");
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
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
