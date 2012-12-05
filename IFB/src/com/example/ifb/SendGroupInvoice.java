package com.example.ifb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SendGroupInvoice extends Activity {
	MYSQLDB db = new MYSQLDB();
	String groupName = Globals.currentGroup;;
	String amount = "";
	String desc = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_group_invoice);
        TextView mTextView = (TextView)findViewById(R.id.sendGroupInvoiceTextView);
        mTextView.setText("To: " + groupName);
 
	}
    public void sendInvoice(View view){
    	
    	if(groupName.equals("")){
    		dialogMessage("No current group", "Please choose a group.");
    		setContentView(R.layout.choose_group);
    	}
    	else{
	    	EditText aEdit;
	    	EditText dEdit;
	    	aEdit = (EditText)findViewById(R.id.amountText);
	    	dEdit = (EditText)findViewById(R.id.descriptionText);
	    	amount = aEdit.getText().toString();
	    	desc = dEdit.getText().toString();
	    	
	    	if(amount.equals("0.0") || desc.equals(""))
	    		dialogMessage("Empty field", "Please dont leave any field empty.");
	    	else{
	    		double amountToDouble = Double.parseDouble(amount);
	    		if(db.getGroupId(groupName) != null){
	    			Button mButton;
	    			mButton = (Button) findViewById(R.id.sendInvoiceButton);
	    			mButton.setEnabled(false);
		    		if(db.addInvoice(groupName, amountToDouble, desc, Globals.clientName) != null){
		    		
						//Show toast
						Context context = getApplicationContext();
						CharSequence text = "Invoice sent to " + groupName;
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
			    		
			    		dEdit.setText("");
			    		aEdit.setText("");
		    		}
		    		else{
		    			dialogMessage("Send error", "Could not send invoice at the moment, please try again later.");
		    		}
		    		mButton.setEnabled(true);
	    		}
	    		else{
	    			dialogMessage("Username error", "User does not exist!");
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
}
