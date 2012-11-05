package com.example.ifb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void newGroup(View view) {
    	Intent i = new Intent(this, NewGroup.class);  
    	startActivityForResult(i, 0);
    }
    
    public void chooseGroup(View view) {
    	Intent i = new Intent(this, ChooseGroup.class);  
    	startActivityForResult(i, 0);
    }
    
    public void sendInvoice(View view) {
    	Intent i = new Intent(this, SendInvoice.class);  
    	startActivityForResult(i, 0);
    }
    
    public void exit(View view) {
    	System.exit(0);
    }
}