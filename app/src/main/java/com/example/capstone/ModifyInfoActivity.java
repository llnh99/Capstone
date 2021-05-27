package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ModifyInfoActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String address;
    private ImageView profileImageView;
    private RelativeLayout loaderLayout;
    private EditText nameText;
    private TextView locationText;
    private TextView emailText;
    private String new_address_gu;
    private String new_address_dong;
    private String new_photo_url;
    private String profilePath;

    private String name;
    private String address_dong;
    private String address_gu;
    private String photo_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        findViewById(R.id.galleryButton).setOnClickListener(onClickListener);
        findViewById(R.id.goToPasswordResetButton).setOnClickListener(onClickListener);
        findViewById(R.id.locationAuthButton).setOnClickListener(onClickListener);
        findViewById(R.id.modifyButton).setOnClickListener(onClickListener);
        loaderLayout = findViewById(R.id.loaderLayout);
        profileImageView = findViewById(R.id.profileImageView);

        nameText = (EditText) findViewById(R.id.nameEditText);
        locationText = (TextView)findViewById(R.id.locationText);
        profileImageView = (ImageView)findViewById(R.id.profileImageView);
        emailText = (TextView)findViewById(R.id.emailText);
        upload();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void upload() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        emailText.setText(user.getEmail());

        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    name = documentSnapshot.getString("name");
                    address_dong = documentSnapshot.getString("address_dong");
                    address_gu = documentSnapshot.getString("address_gu");
                    photo_url = documentSnapshot.getString("photo_url");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = resultIntent.getStringExtra("profilePath");
                    new_photo_url = profilePath;
                    Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImageView);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    address = resultIntent.getStringExtra("address");

                    String split_address[] = address.split(" ");
                    for (int i = 0; i < split_address.length; i++) {
                        System.out.println(split_address[i]);
                    }
                    new_address_gu = split_address[2];
                    new_address_dong = split_address[3];
                    locationText.setText(new_address_gu + " " + new_address_dong);
                }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.galleryButton:
                    myStartActivity(GalleryActivity.class, "image");
                    break;

                case R.id.locationAuthButton:
                    myStartActivity(LocationAuthActivity.class, 1);
                    break;

                case R.id.goToPasswordResetButton:
                    myStartActivity(PasswordResetActivity.class);
                    break;

                case R.id.modifyButton :
                    updateInfo();
                    break;
            }
        }
    };

    private void updateInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //이름, 사진, 위치 변경 구현
        String new_name = nameText.getText().toString();

        if(new_address_dong == null && new_address_gu == null){
            new_address_dong = address_dong;
            new_address_gu =address_gu;
        }
        db.collection("users").document(user.getUid()).update("name", new_name, "address_dong", new_address_dong, "address_gu", new_address_gu, "photo_url", new_photo_url)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startToast("회원정보 수정을 성공하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("회원정보 수정에 실패하였습니다.");
                    }
                });

        /*
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
        }*/
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void myStartActivity(Class c, int requestCode) {

        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }

    private void myStartActivity(Class c, String media) {
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivityForResult(intent, 0);
    }
}

