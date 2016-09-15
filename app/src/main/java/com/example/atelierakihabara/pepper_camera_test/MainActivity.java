package com.example.atelierakihabara.pepper_camera_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private String TAG = "pepper_camera";
    private int camera_req = 1888;

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView)findViewById(R.id.imageView1);
        button = (Button)findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, camera_req);
            }
        });
    }

    protected void onActivityResult(int request_code, int result_code, Intent intent){
        if (request_code == camera_req && result_code == RESULT_OK) {
            Bitmap bitmap = (Bitmap)intent.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    private File savePhoto(Bitmap photo_bmp){
        String store = Environment.getExternalStorageDirectory().toString();
        OutputStream outputStream = null;
        File file = new File(store, "photo.png");

        if (file.exists()) {
            Log.d(TAG, "file exist");
            file.delete();
            file = new File(store, "photo.png");
        }

        try {
            outputStream = new FileOutputStream(file);
            photo_bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            try {
                outputStream.flush();
                outputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Log.d(TAG, store);
        return file;
    }
}
