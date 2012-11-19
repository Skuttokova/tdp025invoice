package com.example.ifb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import com.facebook.android.*;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.*;
import com.facebook.android.AsyncFacebookRunner;

public class MainActivity extends Activity {

	Facebook facebook = new Facebook("292110737575173");
    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        facebook.authorize(this, new DialogListener() {
            public void onComplete(Bundle values) {}

            public void onFacebookError(FacebookError error) {}

            public void onError(DialogError e) {}

            public void onCancel() {}
        });
    }
    
    public void logout(){
    	
    mAsyncRunner.logout(getBaseContext(), new RequestListener() {

    	  public void onComplete(String response, Object state) {}
    	  
    	  public void onIOException(IOException e, Object state) {}
    	  
    	  public void onFileNotFoundException(FileNotFoundException e,
    	        Object state) {}
    	  
    	  public void onMalformedURLException(MalformedURLException e,
    	        Object state) {}
    	  
    	  public void onFacebookError(FacebookError e, Object state) {}
    	});
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
