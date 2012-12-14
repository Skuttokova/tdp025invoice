package com.example.ifb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseGroup extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);
        String[] groupArray;
        final MYSQLDB db = new MYSQLDB();
        groupArray = db.getUsersGroups(Globals.clientName); //get hashmap of users groups
        
        final ListView lv = (ListView) findViewById(R.id.groupList);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, groupArray);
        lv.setAdapter(arrayAdapter);
        
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                
                String item = ((TextView)view).getText().toString();
                Globals.currentGroup = item;
                Intent intent = new Intent(ChooseGroup.this,ChosenGroup.class);
                startActivity(intent);
            }
        });
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addGroup(View view) {
    	Intent i = new Intent(this, NewGroup.class);  
    	startActivityForResult(i, 0);
    }
}
