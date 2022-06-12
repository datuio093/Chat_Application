package com.example.demoapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.demoapp.databinding.ActivityAudioBinding;
import com.facebook.cache.common.CacheEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends AppCompatActivity {
    ActivityAudioBinding binding;
    private  MediaRecorder recorder;
    private String fileName = null;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAudioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        binding.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    startRecording();
                   // Toast.makeText(AudioActivity.this, fileName, Toast.LENGTH_SHORT).show();
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    stopRecording();
                }
                return false;
            }
        });
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            Toast.makeText(this, "Dang Ghi Am", Toast.LENGTH_SHORT).show();
            recorder.prepare();

        } catch (Exception e) {
            Toast.makeText(this, "Loi", Toast.LENGTH_SHORT).show();
        }
        recorder.start();
    }

    private void stopRecording() {

        try {
            recorder.stop();
            recorder.release();
            recorder = null;
        } catch (Exception e) {
            Toast.makeText(this, "Loi haha", Toast.LENGTH_SHORT).show();
        }
     //   updateRecording();
    }

    private void updateRecording() {
        StorageReference filepath = storageReference.child("Audio").child("new.mp3");
        Uri uri = Uri.fromFile(new File(fileName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AudioActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

