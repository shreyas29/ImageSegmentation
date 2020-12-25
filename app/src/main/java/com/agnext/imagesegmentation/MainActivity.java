package com.agnext.imagesegmentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.agnext.imagesegmentation.imagesegmentation.ImageSegmentation;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.vansuita.pickimage.util.Util;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class MainActivity extends AppCompatActivity {

    private ImageView ivOriginal;
    private ImageView ivSegmented;

    private Bitmap bmpOriginal;
    private Bitmap bmpSegmented;

    private static final String TAG = "MainActivity";

    static{
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG,"OpenCV not loaded");
        }else{
            Log.d(TAG,"OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivOriginal = findViewById(R.id.originalImageView);
        ivSegmented = findViewById(R.id.sementedImageView);
    }

    public void openGallery(View view) {
        PickImageDialog.build(new PickSetup().setPickTypes(EPickType.GALLERY))
                .setOnPickResult(r -> {
                    //TODO: do what you have to...
                    Mat mat = Imgcodecs.imread(r.getPath());
                    bmpOriginal = r.getBitmap();
                    ivOriginal.post(() -> ivOriginal.setImageBitmap(bmpOriginal));

                    new Thread(() -> {
                            Mat segmentedMat = ImageSegmentation.segmentUsingWatershed(mat);
                            bmpSegmented = Bitmap.createBitmap(segmentedMat.cols(), segmentedMat.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(segmentedMat,bmpSegmented);
                            ivSegmented.post(() -> ivSegmented.setImageBitmap(bmpSegmented));
                    }).start();
                })
                .setOnPickCancel(() -> {
                    //TODO: do what you have to if user clicked cancel
                }).show(getSupportFragmentManager());
    }
}