package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "Warn";
    private TextView textView4, textView5, textView6;
    private Button button, button2;
    private NaverMap naverMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //네이버지도
        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.map, mapFragment).commit();

        }
        mapFragment.getMapAsync(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent barintent = new Intent(this, ChartActivity.class);
        Intent radarintent = new Intent(this, RadarChartActivity.class);
        Intent intent = getIntent();

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> sel = (ArrayList<String>)  intent.getSerializableExtra("list");
        ArrayList<String> detail = new ArrayList<>();

        String m1 = intent.getStringExtra("m1");
        String m2 = intent.getStringExtra("m2");
        String m3 = intent.getStringExtra("m3");

        list.add(m1);
        list.add(m2);
        list.add(m3);
        /*
        textView4 = findViewById(R.id.textView4);
        textView4.setText(m1);
        textView5 = findViewById(R.id.textView5);
        textView5.setText(m2);
        textView6 = findViewById(R.id.textView6);
        textView6.setText(m3);

         */
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("data")
                        .whereIn("지역구",list)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                            System.out.print(document.get(sel.get(0)+"R").toString());


                                    }
                                    startActivity(barintent);


                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(radarintent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }
}
