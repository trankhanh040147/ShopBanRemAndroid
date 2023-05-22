package com.shopeeapp.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

import com.shopeeapp.MainActivity;

public class ImageConverter {
    public static Bitmap byte2Bitmap(byte[] img) {
        if (img == null)
            return null;
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    public static byte[] bitmap2Byte(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap resource2Bitmap(int resourceId) {
        return BitmapFactory.decodeResource(MainActivity.mainResources, resourceId);
    }

    public static Bitmap drawable2Bitmap(Drawable image) {
        return ((BitmapDrawable) image).getBitmap();
    }
}
