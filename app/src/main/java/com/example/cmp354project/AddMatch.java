package com.example.cmp354project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMatch extends AppCompatActivity implements View.OnClickListener {

    EditText et_cs, et_ChampionName, et_nameOfItem, et_damage;
    private Button btn_addItem, btn_removeItem, btn_submitMatch;
    Spinner spinner_role, spinner_winOrLoss;

    Dialog champSelectDialog,itemSelectDialog;

    ArrayAdapter<CharSequence> roleadapter,resultadapter, champSelectAdapter,itemSelecAdapter;
    FirebaseFirestore db;
    int index = 1;
    String itemsFinalString = "";
    ArrayList<String> items = new ArrayList<String>();
    TextView tv_selectChamp,tv_selectItem;
    FirebaseAuth mAuth;






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_match_to_history);



        et_cs = findViewById(R.id.et_cs);
       // et_ChampionName = findViewById(R.id.et_championName);
       // et_nameOfItem = findViewById(R.id.et_nameOfItem);
        et_damage = findViewById(R.id.et_damage);
        tv_selectChamp = findViewById(R.id.tv_enterChamp);
        tv_selectItem=findViewById(R.id.tv_ItemSelect);

        btn_addItem = findViewById(R.id.btn_addItem);
        btn_removeItem = findViewById(R.id.btn_removeItem);
        btn_submitMatch = findViewById(R.id.btn_submitMatch);

        spinner_role = findViewById(R.id.spinner_role);
        spinner_winOrLoss = findViewById(R.id.spinner_winOrLoss);

        tv_selectChamp.setOnClickListener(this);
        btn_submitMatch.setOnClickListener(this);
        btn_addItem.setOnClickListener(this);
        btn_removeItem.setOnClickListener(this);

        roleadapter =ArrayAdapter.createFromResource(this, R.array.Roles, android.R.layout.simple_spinner_item);
        roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_role.setAdapter(roleadapter);

        resultadapter = ArrayAdapter.createFromResource(this, R.array.Result, android.R.layout.simple_spinner_item);
        resultadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_winOrLoss.setAdapter(resultadapter);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();




    }

    public void onClick_item(View view)
    {
        itemSelectDialog = new Dialog(AddMatch.this);
        // set custom dialog
        itemSelectDialog.setContentView(R.layout.item_spinner);
        // set custom height and width
        itemSelectDialog.getWindow().setLayout(650, 800);
        // set transparent background
        itemSelectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //show dialog
        itemSelectDialog.show();

        EditText et_searchItem = itemSelectDialog.findViewById(R.id.et_searchItem);
        ListView lv_viewItem = itemSelectDialog.findViewById(R.id.lv_viewItem);

        itemSelecAdapter =ArrayAdapter.createFromResource(AddMatch.this, R.array.Items, android.R.layout.simple_spinner_item);
        itemSelecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set adapter
        lv_viewItem.setAdapter(itemSelecAdapter);
        et_searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemSelecAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lv_viewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when item selected from list
                // set selected item on textView
                tv_selectItem.setText(itemSelecAdapter.getItem(position));

                // Dismiss dialog
                itemSelectDialog.dismiss();
            }
        });
    }



    @Override
    public void onClick(View v) {
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       String userEmail = mAuth.getCurrentUser().getEmail();
       if(v.getId() == R.id.tv_enterChamp)
       {
           champSelectDialog = new Dialog(AddMatch.this);
           // set custom dialog
           champSelectDialog.setContentView(R.layout.searchable_spinner);
           // set custom height and width
           champSelectDialog.getWindow().setLayout(650, 800);
           // set transparent background
           champSelectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           //show dialog
           champSelectDialog.show();
           // Initialize and assign variable
           EditText et_searchChamp = champSelectDialog.findViewById(R.id.et_searchChamp);
           ListView lv_viewChamp = champSelectDialog.findViewById(R.id.lv_ViewChamp);
           // Initialize array adapter
           champSelectAdapter =ArrayAdapter.createFromResource(AddMatch.this, R.array.Champions, android.R.layout.simple_spinner_item);
           champSelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           // set adapter
           lv_viewChamp.setAdapter(champSelectAdapter);
           et_searchChamp.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   champSelectAdapter.getFilter().filter(s);
               }
               @Override
               public void afterTextChanged(Editable s) {

               }
           });
           lv_viewChamp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // when item selected from list
                   // set selected item on textView
                   tv_selectChamp.setText(champSelectAdapter.getItem(position));
                   // Dismiss dialog
                   champSelectDialog.dismiss();
               }
           });
       }
        if(v.getId()==R.id.btn_addItem)
        {
            if(index <= 6)
            {
                items.add(tv_selectItem.getText().toString());
                tv_selectItem.setText("");
                index++;
            }
            else {
                Toast.makeText(this, "You cannot add more than 6 items",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId()== R.id.btn_removeItem)
        {
            if (index > 0)
            {
                items.remove(index);
                index--;
            }
            else {
                Toast.makeText(this, "You cannot remove items from an empty inventory",Toast.LENGTH_SHORT).show();
            }

        }
        else if(v.getId() == R.id.btn_submitMatch)
        {
            for(String s : items)
            {
                itemsFinalString += s + ",";
           }
            if(singleton.getInstance().getIntValue() > 10)
            {
                Toast.makeText(this, "You cannot have more than 10 matches saved", Toast.LENGTH_SHORT);
            }

            Map<String,Object> match = new HashMap<>();
            Map<String,Object> numberOfMatches = new HashMap<>();


            match.put("Champion",tv_selectChamp.getText().toString());
            match.put("Creep Score", et_cs.getText().toString());
            match.put("Result",spinner_winOrLoss.getSelectedItem().toString() );
            match.put("Items", itemsFinalString);
            match.put("Role", spinner_role.getSelectedItem().toString());
            match.put("Damage",et_damage.getText().toString());
            String matchNumber = "Match " + singleton.getInstance().getIntValue();
            db.collection(userEmail).document(matchNumber).set(match);
            numberOfMatches.put("Number of matches",singleton.getInstance().getIntValue());
            db.collection(userEmail).document("Account Setup").update(numberOfMatches);
            singleton.getInstance().setIntValue(singleton.getInstance().getIntValue()+1);


            Toast.makeText(this, "Match has been added",Toast.LENGTH_SHORT).show();
            createNotification();


            finish();
        }

    }

    public void createNotification()
    {

 Intent notificationIntent = new Intent(this, LoggedIn.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// fix this to pass stuff

        PendingIntent pendingIntent =
               PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        int icon = R.drawable.league_icon;
        CharSequence tickerText = "Match has been added to your match history";
        CharSequence contentTitle = "League.gg";
        CharSequence contentText = "Click to go to your match history";

        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("Channel_ID", "My Notifications", NotificationManager.IMPORTANCE_HIGH);
        }

        NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat
                .Builder(this, "Channel_ID")
                .setSmallIcon(icon)
                .setTicker(tickerText)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId("Channel_ID")
                .build();

        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);




    }

}
