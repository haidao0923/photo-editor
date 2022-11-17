package com.example.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 3);
            }
        });
    }

    private Bitmap loadBitmapFromView(View v) {
        final int w = v.getWidth() * 2;
        final int h = v.getHeight() * 2;
        final Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas c = new  Canvas(b);
        v.layout(0, 0, w, h);
        v.draw(c);
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageURI(selectedImage);

                Bitmap bitmap = loadBitmapFromView(imageView);
                String testFileName = "testFileName";
                String testDescription = "testDescription";
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, testFileName , testDescription);
            }
        }
    }

}

