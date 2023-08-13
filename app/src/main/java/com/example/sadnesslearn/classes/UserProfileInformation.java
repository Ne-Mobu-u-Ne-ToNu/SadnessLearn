package com.example.sadnesslearn.classes;

import android.app.Activity;
import android.net.Uri;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class UserProfileInformation {
    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReference(Constants.PROFILE_PHOTOS_FOLDER);
    private static Uri uploadUri;
    public static void uploadProfilePhoto(Uri photo, Activity context) throws IOException {
        StorageReference mRef = storageRef.child(System.currentTimeMillis() + "_profile_photo");
        UploadTask uploadTask = mRef.putFile(photo);
        Task<Uri> task = uploadTask.continueWithTask(task1 -> mRef.getDownloadUrl()).addOnCompleteListener(task12 -> uploadUri = task12.getResult());
    }
}
