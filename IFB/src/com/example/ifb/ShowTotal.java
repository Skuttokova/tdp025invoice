package com.example.ifb;

import java.util.HashMap;

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


public class ShowTotal extends Activity {
	
	MYSQLDB db = new MYSQLDB();
	HashMap[] unpaidInvoices;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	unpaidInvoices = db.getUnpaidInvoices(Globals.clientName);
    	for(int i = 0; i < unpaidInvoices.length; i++){
	    	Integer invoiceId = (Integer) unpaidInvoices[i].get("id");
	    	Double amount = (Double) unpaidInvoices[i].get("amount");
	    	String desc = (String) unpaidInvoices[i].get("description");
	    	Integer fromId = (Integer) unpaidInvoices[i].get("fromUserId");
    	}
    	
    }

}
