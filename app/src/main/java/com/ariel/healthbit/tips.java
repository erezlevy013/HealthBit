package com.ariel.healthbit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class tips extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference ref;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbalWEIGHTTRACKER);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        TextView tip_context = (TextView) findViewById(R.id.tip_context);

        // Making date in String format
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());


        // Contting into db and search for dailt tip
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("DailyTips");
        Query Tips = ref.orderByChild("date").equalTo(date);
        Tips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    TipEvent t = ds.getValue(TipEvent.class);
                    tip_context.setText(t.getContext());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)//toolbar definition
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuAbout) {
            // About activity
            Intent myIntent = new Intent(getApplicationContext(), About_activity.class);
            startActivity(myIntent);
            return true;
        } else if (id == R.id.menuSettings) {
            // Contact activity
            Intent myIntent = new Intent(getApplicationContext(), ContactActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}