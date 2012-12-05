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

public class GroupMembers extends Activity {
	MYSQLDB db = new MYSQLDB();
	String toText = "";
	String amount = "";
	String desc = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_invoice);
        

 
	}
    public void sendInvoice(View view){
    	
    	EditText mEdit;
    	EditText aEdit;
    	EditText dEdit;
    	mEdit = (EditText)findViewById(R.id.toText);
    	aEdit = (EditText)findViewById(R.id.amountText);
    	dEdit = (EditText)findViewById(R.id.descriptionText);
    	toText = mEdit.getText().toString();
    	amount = aEdit.getText().toString();
    	desc = dEdit.getText().toString();
    	
    	if(toText.equals("") || amount.equals("0.0") || desc.equals(""))
    		dialogMessage("Empty field", "Please dont leave any field empty.");
    	else{
    		double amountToDouble = Double.parseDouble(amount);
    		if(db.getUserId(toText) != null){
    			Button mButton;
    			mButton = (Button) findViewById(R.id.sendInvoiceButton);
    			mButton.setEnabled(false);
	    		if(db.addPrivInvoice(toText, amountToDouble, desc, Globals.clientName) != null){
	    		
					//Show toast
					Context context = getApplicationContext();
					CharSequence text = "Invoice sent to " + toText;
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
		    		
		    		mEdit.setText("");
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