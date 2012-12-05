package com.example.ifb;

import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ChooseGroup extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_group);
        HashMap[] groupMap;
        MYSQLDB db = new MYSQLDB();
        //groupMap = db.getUsersGroups(Globals.clientName); get hashmap of users groups
       // db.getUsersGroups("user1");
       // ListView groupList = (ListView)findViewById(R.id.groupList); create listview to show groups
      //  Toast.makeText(getApplicationContext(),groupMap.length,Toast.LENGTH_SHORT).show();
        // show groups in the listview
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
