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
    
    String groupName = null;
    int groupId;
	MYSQLDB db = new MYSQLDB();
    
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
		else if(db.getGroupId(groupName) != null){
			dialogMessage("Group error!","Group name already exists!");
		}
		
		//All clear, add group
		else{
			if(db.addGroup(groupName) != null){
				
				//add self to group
				db.addUserToGroup(Globals.clientName,groupName, 1);

				Toast toast = Toast.makeText(getApplicationContext(), "Group created!", Toast.LENGTH_SHORT);
				toast.show();
				
				//Disable button
				Button mButton;
				mButton = (Button)findViewById(R.id.groupAddButton);
				mButton.setEnabled(false);
			}
			else{
				dialogMessage("Error!","Could not create the group at the moment! Please try again later.");
			}
		}
    }
    	
	public void clickAddUser(View view){
		
		EditText mUser;
		mUser   = (EditText)findViewById(R.id.addUserText);
		
		//If no groupName added
		if(groupName == null){
			dialogMessage("Add group!","Please create a group before adding users!");
		}
		
		else{
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
					if(db.addUserToGroup(userName, groupName, 0) == 1){
						Toast toast = Toast.makeText(getApplicationContext(), "Invite sent to user!", Toast.LENGTH_SHORT);
						toast.show();
						
						//Clear edittext field
						mUser.setText("");
					}
					else if(db.addUserToGroup(userName, groupName, 0) == -1){
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
