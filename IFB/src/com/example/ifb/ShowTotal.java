package com.example.ifb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.example.ifb.R;
import com.example.ifb.R.id;
import com.example.ifb.R.layout;

import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.KeyEvent;
import android.view.Menu;
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
import android.content.Intent;


public class ShowTotal extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_total);
        
    	MYSQLDB db = new MYSQLDB();
    	HashMap[] unpaidInvoices;
    	ListView lv;
    	
        Double total = db.getTotal(Globals.currentGroup);
        Integer groupMembers = db.getGroupUsers(db.getGroupId(Globals.currentGroup)).length;
        Double perPerson;
        if(total == null){
        	perPerson = 0.0;
        	total = 0.0;
        }
        else if((total != null || total != 0) && (groupMembers != null || groupMembers != 0)){
        	perPerson = total/groupMembers;
        }
        else{
        	perPerson = 0.0;
        	total = 0.0;
        }
        
        TextView totalTextView = (TextView) findViewById(id.totalTextView);
        totalTextView.setText("Total: "+total);
        
        Integer[] members = db.getGroupUsers(db.getGroupId(Globals.currentGroup));
       
        HashMap payers = new HashMap();
    	unpaidInvoices = db.getUnpaidInvoices(Globals.clientName, Globals.currentGroup);    	
    	for(int i = 0; i < unpaidInvoices.length; i++){
	    	Double amount = (Double) unpaidInvoices[i].get("amount");
	    	Integer fromId = (Integer) unpaidInvoices[i].get("fromId");
	    	if(payers.get(fromId) != null){
	    		Double currAmount = (Double) payers.get(fromId);
	    		payers.put(fromId, currAmount+amount);
	    	}
	    	else
	    		payers.put(fromId, amount);
    	}
    	
    	String[] payerList = new String[members.length];
    	
    	Iterator it = payers.entrySet().iterator();
    	int count = 0;
    	
    	while(it.hasNext()){
    		HashMap.Entry pairs = (Entry) it.next();
    		Double toPay = (Double)pairs.getValue()-perPerson;
    		String userPaid = db.getUserName((Integer) pairs.getKey()) + ": " + toPay;
    		payerList[count] = userPaid;
    		for(int i=0;i<members.length;i++){
    			if(members[i] == pairs.getKey())
    				members[i] = null;
    		}
    		count++;
    		it.remove();
    	}
    	
    	//Add users who have not paid
    	for(int i = 0; i<members.length; i++){
    		if(members[i] != null){
    			payerList[count] =  db.getUserName(members[i]) + ": -" + perPerson;
    			count++;
    		}
    	}
    	
        lv = (ListView) findViewById(R.id.payersListView);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, payerList);
        lv.setAdapter(arrayAdapter);
    	
    }

}
