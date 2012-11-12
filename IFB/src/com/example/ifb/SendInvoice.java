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

public class SendInvoice extends Activity {
	DB db = new DB(this);
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
    	if(toText.equals("Username") || amount.equals("0.0") || desc.equals("")){
    		final Context context = this;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				alertDialogBuilder.setTitle("Please enter correct data");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Add correct data!")
					.setCancelable(false)
					.setPositiveButton("OK!",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					  });
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
    		
    		
    	}
    	else{
    		
    		double amountToDouble = Double.parseDouble(amount);
    		db.addInvoice(mEdit.getText().toString(), amountToDouble, dEdit.getText().toString());
    		mEdit.setText("");
    		dEdit.setText("");
    		aEdit.setText("");
    	}
    }
}
