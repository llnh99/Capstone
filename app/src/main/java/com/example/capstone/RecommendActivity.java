package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

public class RecommendActivity extends AppCompatActivity {
    ///private ActivityRecommendBinding binding;
    private static final String TAG = "Warn";
    private Chip chip_1, chip_2, chip_3, chip_4, chip_5, chip_6, chip_7, chip_8, chip_9, chip_10, chip_11, chip_12, chip_13, chip_14
            , chip_15, chip_16, chip_17, chip_18;
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Intent intent = new Intent(this, ResultActivity.class);
        //binding = ActivityRecommendBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        chip_1 = findViewById(R.id.chip1);
        chip_2 = findViewById(R.id.chip2);
        chip_3 = findViewById(R.id.chip3);
        chip_4 = findViewById(R.id.chip4);
        chip_5 = findViewById(R.id.chip5);
        chip_6 = findViewById(R.id.chip6);
        chip_7 = findViewById(R.id.chip7);
        chip_8 = findViewById(R.id.chip8);
        chip_9 = findViewById(R.id.chip9);
        chip_10 = findViewById(R.id.chip10);

        chip_11 = findViewById(R.id.chip11);
        chip_12 = findViewById(R.id.chip12);
        chip_13 = findViewById(R.id.chip13);
        chip_14 = findViewById(R.id.chip14);
        chip_15 = findViewById(R.id.chip15);
        chip_16 = findViewById(R.id.chip16);
        chip_17 = findViewById(R.id.chip17);
        chip_18 = findViewById(R.id.chip18);
        confirmButton = findViewById(R.id.confirmButton);
        ArrayList<String> sel = new ArrayList<>();
        ArrayList<String> gu = new ArrayList<>();
        ArrayList<String> regu = new ArrayList<>();
        ArrayList<Integer> chip1 = new ArrayList<>();
        ArrayList<Integer> chip2 = new ArrayList<>();
        ArrayList<Integer> chip3 = new ArrayList<>();
        ArrayList<Integer> chip4 = new ArrayList<>();
        ArrayList<Integer> chip5 = new ArrayList<>();
        ArrayList<Integer> chip6 = new ArrayList<>();
        ArrayList<Integer> chip7 = new ArrayList<>();
        ArrayList<Integer> chip8 = new ArrayList<>();
        ArrayList<Integer> chip9 = new ArrayList<>();
        ArrayList<Integer> chip10 = new ArrayList<>();
        ArrayList<Integer> chip11 = new ArrayList<>();
        ArrayList<Integer> chip12 = new ArrayList<>();
        ArrayList<Integer> chip13 = new ArrayList<>();
        ArrayList<Integer> chip14 = new ArrayList<>();
        ArrayList<Integer> chip15 = new ArrayList<>();
        ArrayList<Integer> chip16 = new ArrayList<>();
        ArrayList<Integer> chip17 = new ArrayList<>();
        ArrayList<Integer> chip18 = new ArrayList<>();
        int[] result = new int[25];
        Map<String, Integer> map = new HashMap<String, Integer>();

        db.collection("data")
                .orderBy("지역구")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.w(TAG, document.get("지역구").toString() + document.get(binding.chip3.getText() +"R"));
                                gu.add(document.get("지역구").toString());
                                chip1.add(Integer.parseInt(document.get(chip_1.getText() +"R").toString()));
                                chip2.add(Integer.parseInt(document.get(chip_2.getText() +"R").toString()));
                                chip3.add(Integer.parseInt(document.get(chip_3.getText() +"R").toString()));
                                chip4.add(Integer.parseInt(document.get(chip_4.getText() +"R").toString()));
                                chip5.add(Integer.parseInt(document.get(chip_5.getText() +"R").toString()));
                                chip6.add(Integer.parseInt(document.get(chip_6.getText() +"R").toString()));
                                chip7.add(Integer.parseInt(document.get(chip_7.getText() +"R").toString()));
                                chip8.add(Integer.parseInt(document.get(chip_8.getText() +"R").toString()));
                                chip9.add(Integer.parseInt(document.get(chip_9.getText() +"R").toString()));
                                chip10.add(Integer.parseInt(document.get(chip_10.getText() +"R").toString()));
                                chip11.add(Integer.parseInt(document.get(chip_11.getText() +"R").toString()));
                                chip12.add(Integer.parseInt(document.get(chip_12.getText() +"R").toString()));
                                chip13.add(Integer.parseInt(document.get(chip_13.getText() +"R").toString()));
                                chip14.add(Integer.parseInt(document.get(chip_14.getText() +"R").toString()));
                                chip15.add(Integer.parseInt(document.get(chip_15.getText() +"R").toString()));
                                chip16.add(Integer.parseInt(document.get(chip_16.getText() +"R").toString()));
                                chip17.add(Integer.parseInt(document.get(chip_17.getText() +"R").toString()));
                                chip18.add(Integer.parseInt(document.get(chip_18.getText() +"R").toString()));

                            }


                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(chip_1.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip1.get(i);
                                        }
                                        sel.add(chip_1.getText().toString());
                                    }
                                    if(chip_2.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip2.get(i);
                                        }
                                        sel.add(chip_2.getText().toString());
                                    }
                                    if(chip_3.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip3.get(i);
                                        }
                                        sel.add(chip_3.getText().toString());
                                    }
                                    if(chip_4.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip4.get(i);
                                        }
                                        sel.add(chip_4.getText().toString());
                                    }
                                    if(chip_5.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip5.get(i);
                                        }
                                        sel.add(chip_5.getText().toString());
                                    }
                                    if(chip_6.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip6.get(i);
                                        }
                                        sel.add(chip_6.getText().toString());
                                    }
                                    if(chip_7.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip7.get(i);
                                        }
                                        sel.add(chip_7.getText().toString());
                                    }
                                    if(chip_8.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip8.get(i);
                                        }
                                        sel.add(chip_8.getText().toString());
                                    }
                                    if(chip_9.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip9.get(i);
                                        }
                                        sel.add(chip_9.getText().toString());
                                    }
                                    if(chip_10.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip10.get(i);
                                        }
                                        sel.add(chip_10.getText().toString());
                                    }
                                    if(chip_11.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip11.get(i);
                                        }
                                        sel.add(chip_11.getText().toString());
                                    }
                                    if(chip_12.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip12.get(i);
                                        }
                                        sel.add(chip_12.getText().toString());
                                    }
                                    if(chip_13.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip13.get(i);
                                        }
                                        sel.add(chip_13.getText().toString());
                                    }
                                    if(chip_14.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip14.get(i);
                                        }
                                        sel.add(chip_14.getText().toString());
                                    }
                                    if(chip_15.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip15.get(i);
                                        }
                                        sel.add(chip_15.getText().toString());
                                    }
                                    if(chip_16.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip16.get(i);
                                        }
                                        sel.add(chip_16.getText().toString());
                                    }
                                    if(chip_17.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip17.get(i);
                                        }
                                        sel.add(chip_17.getText().toString());
                                    }
                                    if(chip_18.isChecked()){
                                        for(int i=0; i<25; i++){
                                            result[i] = result[i] + chip18.get(i);
                                        }
                                        sel.add(chip_18.getText().toString());
                                    }
                                    for (int i = 0; i < gu.size(); i++) {
                                        map.put(gu.get(i),result[i]);
                                    }

                                    List<String> keySetList = new ArrayList<>(map.keySet());

                                    // 오름차순
                                    System.out.println("------value 오름차순------");
                                    Collections.sort(keySetList, (o1, o2) -> (map.get(o1).compareTo(map.get(o2))));

                                    for(String key : keySetList) {
                                        System.out.println("key : " + key + " / " + "value : " + map.get(key));
                                        regu.add(key);
                                    }
                                    intent.putExtra("m1",regu.get(0));
                                    intent.putExtra("m2",regu.get(1));
                                    intent.putExtra("m3",regu.get(2));
                                    intent.putExtra("list",sel);
                                    startActivity(intent);
                                }
                            });


                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });

    }
}


