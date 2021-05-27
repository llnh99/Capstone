package com.example.capstone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InfoActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findViewById(R.id.infoChangeButton).setOnClickListener(onClickListener);
        //findViewById(R.id.reviewCheckButton).setOnClickListener(onClickListener);
        //findViewById(R.id.commentCheckButton).setOnClickListener(onClickListener);
        findViewById(R.id.logoutButton2).setOnClickListener(onClickListener);

        upload();
    }

    protected void onResume() {
        super.onResume();
        upload();
    }

    public void upload() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView nameText = (TextView)findViewById(R.id.nameText);
        TextView locationText = (TextView)findViewById(R.id.locationText);
        ImageView profileImageView = (ImageView)findViewById(R.id.profileImageView);

        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("name");
                    String address_dong = documentSnapshot.getString("address_dong");
                    String address_gu = documentSnapshot.getString("address_gu");
                    String photo_url = documentSnapshot.getString("photo_url");

                    nameText.setText(name);
                    locationText.setText(address_gu + " " + address_dong);

                    Bitmap bmp = BitmapFactory.decodeFile(photo_url);
                    profileImageView.setImageBitmap(bmp);
                    Log.d("name", name);
                }
                else {
                    Log.d("error", "error");
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.infoChangeButton :
                    myStartActivity(ModifyInfoActivity.class);
                    break;

              /*  case R.id.reviewCheclButton :
                    break;

                case R.id.commentCheckButton :
                    break;*/

                case R.id.logoutButton2 :
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
            }
        }
    };

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
