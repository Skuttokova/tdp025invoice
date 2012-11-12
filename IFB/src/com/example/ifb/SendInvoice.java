package com.example.ifb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class SendInvoice extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_invoice);
    

 
	}
    public void sendInvoice(View view){
    	DB db = new DB(this);
    	
    	
    	
    	db.addInvoice("Micke", 100, "bio");
    }
}
