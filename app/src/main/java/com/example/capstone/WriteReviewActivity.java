package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {

    private static final String TAG = "WriteReviewActivity";
    String name, address_gu;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private int pathCount = 0;
    private int successCount = 0;
    private ImageView selectedImageView;
    private RelativeLayout buttonsBackgroundLayout;
    private RelativeLayout loaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        loaderLayout = findViewById(R.id.loaderLayout);
        parent = findViewById(R.id.contentsLayout);
        buttonsBackgroundLayout = findViewById(R.id.buttonsBackgroundLayout);
        TextView locationTextView = findViewById(R.id.locationTextView3);

        findViewById(R.id.registerButton).setOnClickListener(onClickListener);
        findViewById(R.id.imageButton).setOnClickListener(onClickListener);
        findViewById(R.id.videoButton).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.videoModify).setOnClickListener(onClickListener);
        findViewById(R.id.deleteButton).setOnClickListener(onClickListener);
        buttonsBackgroundLayout.setOnClickListener(onClickListener);

        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    name = documentSnapshot.getString("name");
                    address_gu = documentSnapshot.getString("address_gu");

                    locationTextView.setText(address_gu);
                }
                else {
                    Log.d("error", "error");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        super.onActivityResult(requestCode, resultCode, resultIntent);

        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = resultIntent.getStringExtra("profilePath");
                    pathList.add(profilePath);

                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(com.example.capstone.WriteReviewActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    parent.addView(linearLayout);

                    ImageView imageView = new ImageView(com.example.capstone.WriteReviewActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    Glide.with(this).load(profilePath).override(1000).into(imageView);
                    linearLayout.addView(imageView);
                }
                break;

            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = resultIntent.getStringExtra("profilePath");
                    Glide.with(this).load(profilePath).override(1000).into(selectedImageView);

                }
                break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.registerButton :
                    storageUpload();
                    break;

                case R.id.imageButton:
                    myStartActivity(GalleryActivity.class, "image", 0);
                    break;

                case R.id.videoButton:
                    myStartActivity(GalleryActivity.class, "video", 0);
                    break;

                case R.id.buttonsBackgroundLayout:
                    if(buttonsBackgroundLayout.getVisibility() == View.VISIBLE) {
                        buttonsBackgroundLayout.setVisibility(View.GONE);
                    }
                    break;

                case R.id.imageModify:
                    myStartActivity(GalleryActivity.class, "image", 1);
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                    break;

                case R.id.videoModify:
                    myStartActivity(GalleryActivity.class, "video", 1);
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                    break;

                case R.id.deleteButton:
                    parent.removeView((View)selectedImageView.getParent());
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void storageUpload() {
        String title = ((EditText)findViewById(R.id.titleEditText)).getText().toString();
        String contents = ((EditText)findViewById(R.id.contentsEditText)).getText().toString();
        int likes = 0;

        if(title.length() > 0 && contents.length() > 0) {
            loaderLayout.setVisibility(View.VISIBLE);
            user = FirebaseAuth.getInstance().getCurrentUser();
            for (int i = 0; i < parent.getChildCount(); i++) {
                String[] pathArray = pathList.get(pathCount).split("\\.");
                final StorageReference mountainImagesRef = storageRef.child("posts/" + user.getUid() + "/" + pathCount + "." + pathArray[pathArray.length - 1]);
                try {
                    InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));

                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setCustomMetadata("index", "" + pathCount)
                            .build();

                    UploadTask uploadTask = mountainImagesRef.putStream(stream, metadata);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));
                            mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pathList.set(index, uri.toString());
                                    successCount++;
                                    if (pathList.size() == successCount) {
                                        ReviewInfo reviewInfo = new ReviewInfo(user.getUid(), name, title, contents, pathList, address_gu, likes, new Date());
                                        storeUpload(reviewInfo);
                                    }
                                }
                            });
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pathCount++;
            }
            if(pathList.size() == 0) {
                ReviewInfo reviewInfo = new ReviewInfo(user.getUid(), name, title, contents, pathList, address_gu, likes, new Date());
                storeUpload(reviewInfo);
            }
        }
        else {
            startToast("제목과 내용을 입력해주세요");
        }
    }

    private void storeUpload(ReviewInfo review) {
        db = FirebaseFirestore.getInstance();
        String title = ((EditText)findViewById(R.id.titleEditText)).getText().toString();
        db.collection("reviews").document(title).set(review)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loaderLayout.setVisibility(View.GONE);
                        startToast("후기 등록을 성공하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loaderLayout.setVisibility(View.GONE);
                        startToast("후기 등록에 실패하였습니다.");
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void myStartActivity(Class c, String media, int requestCode) {
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivityForResult(intent, requestCode);
    }
}

