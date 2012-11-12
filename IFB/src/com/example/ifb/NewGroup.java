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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group);
    }

    public void clickAddGroup(View view){
		EditText mGroup;
	
		mGroup   = (EditText)findViewById(R.id.groupNameText);
		groupName = mGroup.getText().toString();
		
		if(groupName.equals("")){
			final Context context = this;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				alertDialogBuilder.setTitle("Add group name!");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Please add a group name!")
					.setCancelable(false)
					.setPositiveButton("Okey",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							dialog.cancel();
						}
					  });
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
		}
		
		//TODO Check if group already exists
		/*else if(){
			
		}*/
		
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
		}
    }
    	
	public void clickAddUser(View view){
		
		EditText mUser;
		mUser   = (EditText)findViewById(R.id.addUserText);
		
		//If no groupName added
		if(groupName == null){
			final Context context = this;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				alertDialogBuilder.setTitle("Add group!");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Please create a group before adding users!")
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
		
		//If no username added
		else if(mUser.getText().toString().equals("")){
			final Context context = this;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				alertDialogBuilder.setTitle("Add user!");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Please write a username to add to the group!")
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
		
		//All clear, add user
		else{
			
			//TODO add check to see if user exists
			
			//Add user
			database.addUser(mUser.getText().toString());
			
			//Show toast
			Context context = getApplicationContext();
			CharSequence text = "User added to group";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			//Clear edittext field
			mUser.setText("");
		}
	}
    
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
