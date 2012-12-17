package com.example.ifb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*import com.facebook.android.*;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.*;
import com.facebook.android.AsyncFacebookRunner;*/

public class MainActivity extends Activity {

	/*Facebook facebook = new Facebook("292110737575173");
    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);*/
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        Button login = (Button)findViewById(R.id.login_button);
        
        login.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View viewParam)
        	{
        		EditText usernameEdit = (EditText) findViewById(R.id.username);
        		EditText passwordEdit = (EditText) findViewById(R.id.password);
        		
        		String username = usernameEdit.getText().toString();
        		String password = passwordEdit.getText().toString();
        		
        		if(username.length() <= 0 || password.length() <= 0)
        		{
        			Toast.makeText(getApplicationContext(),"Please enter username and password",Toast.LENGTH_SHORT).show();
        		}
        		else
        		{
        			//skicka username och password till databasen och kolla ifall man f�r logga in
        			
        			Globals.clientName = username;
        			
        			//Globals.currentGroup = "Hejsan";
                    Intent intent = new Intent(MainActivity.this,ChooseGroup.class);
                    startActivity(intent);
        		}
        	}
        	
        	
        });
        //setContentView(R.layout.activity_main);
     /*   facebook.authorize(this, new DialogListener() {
            public void onComplete(Bundle values) {}

            public void onFacebookError(FacebookError error) {}

            public void onError(DialogError e) {}

            public void onCancel() {}
        });*/
        
        //setContentView(R.layout.activity_main);
    }
    
   /* public void logout(){
    	
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
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void newGroup(View view) {
    	Intent i = new Intent(this, NewGroup.class);  
    	startActivityForResult(i, 0);
    }
    
}
