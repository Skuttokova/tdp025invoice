package com.example.ifb;

import android.os.Bundle;
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

public class GroupMembers extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_members);
        TextView mTextView = (TextView) findViewById(R.id.groupMembersTextView);
        mTextView.setText(Globals.currentGroup);
        
        String[] members = new String[] {"member1", "member2","member3","member4","member5","member6"};
        
        ListView lv = (ListView) findViewById(R.id.groupMembersListView);
        ArrayAdapter<String> arrayAdapter =      
        new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, members);
        lv.setAdapter(arrayAdapter);
	}
}
