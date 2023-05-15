package com.example.diplom;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    private TextView name,fam,tel,email;
    AppCompatButton signOutButton;
    private FirebaseUser user;
    private StorageReference storageRef;
    /*
    private String selectedImagePath;
    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImageUri = result.getData().getData();
            selectedImagePath = getRealPathFromURI(selectedImageUri);
        }
    });*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) view.findViewById(R.id.username);
        fam = (TextView) view.findViewById(R.id.fam);
        tel = (TextView) view.findViewById(R.id.tel);
        email = (TextView) view.findViewById(R.id.email);
        signOutButton=view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(v -> {
            SharedPreferences preferences =getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isSignedIn",false);
            editor.apply();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://veterinarka-86522-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = db.getReference("Users");
        if (user != null) {
            ref.child(user.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Получаем значение
                    Object value = snapshot.getValue();
                    name.setText(value.toString());

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }

            });

            ref.child(user.getUid()).child("fam").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Получаем значение
                    Object value = snapshot.getValue();
                    fam.setText(value.toString());

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }

            });

            ref.child(user.getUid()).child("tel").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Получаем значение
                    Object value = snapshot.getValue();
                    tel.setText(value.toString());

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }

            });

            ref.child(user.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // Получаем значение
                    Object value = snapshot.getValue();
                    email.setText(value.toString());

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }

            });

        }
/*
        storageRef = FirebaseStorage.getInstance().getReference().child("Images");

        File image = new File("test.txt");
        try {
            image.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("File", image.getAbsolutePath() + "");*/
        return view;
    }
/*
    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }*/
}