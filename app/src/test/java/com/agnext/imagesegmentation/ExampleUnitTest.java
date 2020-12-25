package com.agnext.imagesegmentation;

import com.agnext.imagesegmentation.imagesegmentation.ImageSegmentation;

import org.junit.Before;
import org.junit.Test;
import org.opencv.android.Utils;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Before
    public void initOpenCV() {
        System.loadLibrary("opencv_java342");
    }

    @Test
    public void testImageSegmentation() {
        ImageSegmentation.segmentUsingWatershed(Imgcodecs.imread("testimages/test.jpeg"));
    }
}