package com.minhdv.androidunpaper2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onStart(View view) {
        ImagePicker.Companion.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String inputPath = ImagePicker.Companion.getFilePath(data);
            String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            String outputPath = String.format("%s/output.png", downloadPath);
            String command = String.format("-v %s %s",inputPath,outputPath);
            try {
                int result = new MobileUnpaper().execute(command);
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
