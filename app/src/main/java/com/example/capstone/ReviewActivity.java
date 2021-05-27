package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    EditText searchEditText;

    ArrayList<ReviewInfo> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);
        findViewById(R.id.searchButton).setOnClickListener(onClickListener);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));

        searchEditText = findViewById(R.id.searchEditText);
    }

    protected void onResume() {
        super.onResume();

        String searchText = searchEditText.getText().toString();

        if(user != null) {

            CollectionReference colRef = db.collection("reviews");
            colRef.orderBy("createdAt", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        reviewList = new ArrayList<>();

                        for(QueryDocumentSnapshot document : task.getResult()) {
                            reviewList.add(new ReviewInfo(
                                    (String) document.getData().get("uid"),
                                    document.getData().get("name").toString(),
                                    document.getData().get("title").toString(),
                                    document.getData().get("contents").toString(),
                                    (ArrayList<String>) document.getData().get("post"),
                                    document.getData().get("address_gu").toString(),
                                    Math.toIntExact((Long) document.getData().get("likes")),
                                    new Date(document.getDate("createdAt").getTime())));
                        }
                        if(searchText.length() > 0) {
                            RecyclerView.Adapter mAdapter = new ReviewAdapter(ReviewActivity.this, reviewList);
                            recyclerView.setAdapter(mAdapter);
                            ((ReviewAdapter) mAdapter).filter(searchText);
                        }
                        else {
                            RecyclerView.Adapter mAdapter = new ReviewAdapter(ReviewActivity.this, reviewList);
                            recyclerView.setAdapter(mAdapter);
                        }
                    } else{
                        Log.d(TAG, "Error getting documents : ", task.getException());
                    }
                }
            });
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.floatingActionButton :
                    myStartActivity(WriteReviewActivity.class);
                    break;

                case R.id.searchButton :
                    onResume();
                    break;
            }
        }
    };

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}