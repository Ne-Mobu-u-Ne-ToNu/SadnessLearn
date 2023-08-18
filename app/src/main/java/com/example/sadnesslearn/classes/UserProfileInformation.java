package com.example.sadnesslearn.classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.ImageViewCompat;
import com.bumptech.glide.Glide;
import com.example.sadnesslearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.StorageReferenceUri;

import java.io.IOException;

public class UserProfileInformation {
    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReference(Constants.PROFILE_PHOTOS_FOLDER);
    private static final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(Constants.USERS_KEY).child(UserAuthentification.getUID());
    private static Uri uploadUri;
    public static void uploadProfilePhoto(Uri photo, Activity context, AlertDialog dialog) throws IOException {
        deleteProfilePhoto(dialog, Constants.UPDATE_PROFILE_PHOTO);
        StorageReference mRef = storageRef.child(System.currentTimeMillis() + "_profile_photo");
        UploadTask uploadTask = mRef.putFile(photo);
        uploadTask.continueWithTask(task1 -> mRef.getDownloadUrl()).addOnCompleteListener(task12 -> {
            if(task12.isSuccessful()) {
                uploadUri = task12.getResult();
                databaseRef.child(Constants.PROFILE_PHOTO_KEY).setValue(new ProfilePhoto(uploadUri.toString(), mRef.toString())).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                    } else {
                        uploadError(context, dialog);
                    }
                });
            } else {
                uploadError(context, dialog);
            }
        });
    }

    public static void setProfilePhoto(ImageView imw_profile_photo, Activity context) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfilePhoto photo = snapshot.getValue(ProfilePhoto.class);
                if (photo != null) {
                    ImageViewCompat.setImageTintList(imw_profile_photo, null);
                    imw_profile_photo.setColorFilter(null);
                    Glide.with(context).load(String.valueOf(photo.link)).into(imw_profile_photo);
                } else {
                    imw_profile_photo.setImageResource(R.drawable.ic_profile);
                    imw_profile_photo.setColorFilter(MaterialColors.getColor(context, androidx.appcompat.R.attr.colorPrimary, Color.BLACK));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseRef.child(Constants.PROFILE_PHOTO_KEY).addValueEventListener(eventListener);
    }

    public static void uploadError(Activity context, AlertDialog dialog) {
        Toast.makeText(context, context.getResources().getString(R.string.error_uploading_photo), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public static void deleteProfilePhoto(AlertDialog dialog, boolean action) {
        databaseRef.child(Constants.PROFILE_PHOTO_KEY).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ProfilePhoto photo = task.getResult().getValue(ProfilePhoto.class);
                    if (photo != null) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(photo.ref);
                        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public static void deleteError(Activity context, AlertDialog dialog) {
        Toast.makeText(context, context.getResources().getString(R.string.error_deleting_photo), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public static class ProfilePhoto {
        public String link, ref;

        public ProfilePhoto() {}

        public ProfilePhoto(String link, String ref) {
            this.link = link;
            this.ref = ref;
        }
    }
}
