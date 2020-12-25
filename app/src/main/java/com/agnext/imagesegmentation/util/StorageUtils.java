package com.agnext.imagesegmentation.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class StorageUtils {

    public static void storeImageForJUnitTest(Mat mat, String imageName){
        Imgcodecs.imwrite("testimages\\verification\\"+imageName+".jpg", mat);
    }
}
