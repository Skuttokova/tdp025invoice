package com.example.ifb;

import com.example.ifb.R;
import com.example.ifb.R.id;
import com.example.ifb.R.layout;

import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class ChosenGroup extends Activity {
	MYSQLDB db = new MYSQLDB();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_group);
        TextView mTextView = (TextView) findViewById(R.id.groupNameTextView);
        mTextView.setText(Globals.currentGroup);
	}
    
    @Override
    public void onBackPressed() {
        Globals.currentGroup = "";
        this.finish();
    }
    
    
    public void showGroupInvoices(View view) {
    	Intent i = new Intent(this, ShowTotal.class);  
    	startActivityForResult(i, 0);
    }
    
    public void sendGroupInvoice(View view) {
    	Intent i = new Intent(this, SendGroupInvoice.class);  
    	startActivityForResult(i, 0);
    }
    
    public void members(View view) {
    	Intent i = new Intent(this, GroupMembers.class);  
    	startActivityForResult(i, 0);
    }
    
    public void manageInvoices(View view) {
    	Intent i = new Intent(this, ManageInvoices.class);  
    	startActivityForResult(i, 0);
    }
    
    public void leaveGroup(View view) {
    	MYSQLDB db = new MYSQLDB();
    	if(db.leaveGroup(Globals.currentGroup, Globals.clientName) != null){
			//Show toast
			Context context = getApplicationContext();
			CharSequence text = "Left group: " + Globals.currentGroup + "!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			Globals.currentGroup = "";
            Intent intent = new Intent(ChosenGroup.this,ChooseGroup.class);
            startActivity(intent);
    	}
    }
}
