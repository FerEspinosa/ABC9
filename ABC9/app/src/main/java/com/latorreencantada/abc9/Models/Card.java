package com.latorreencantada.abc9.Models;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Card {

    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;
    public static final int COL_WORD = 1;
    public static final int COL_SYL1 = 2;
    public static final int COL_SYL2 = 3;
    public static final int COL_SYL3 = 4;
    public static final int COL_SYL4 = 5;
    public static final int COL_LEVEL = 6;
    public static final int COL_IMAGE = 7;


    String word;
    String syl1;
    String syl2;
    String syl3;
    String syl4;
    int level;
    String image;

    public Card(Cursor cursor) {
        this.word = cursor.getString(COL_WORD);
        this.syl1 = cursor.getString(COL_SYL1);
        this.syl2 = cursor.getString(COL_SYL2);
        this.syl3 = cursor.getString(COL_SYL3);
        this.syl4 = cursor.getString(COL_SYL4);
        this.level = cursor.getInt(COL_LEVEL);
        this.image = cursor.getString(COL_IMAGE);
    }

    public Card(String word, String syl1, String syl2, String syl3, String syl4, int level, String image) {
        this.word = word;
        this.syl1 = syl1;
        this.syl2 = syl2;
        this.syl3 = syl3;
        this.syl4 = syl4;
        this.level = level;
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public String getSyl(int syl) {

            switch (syl){
                case 1:
                    return syl1;
                case 2:
                    return syl2;
                case 3:
                    return syl3;
                case 4:
                    return syl4;
                default:
                    return null;
            }
    }

    public int getLevel() {
        return level;
    }

    public Bitmap getImage() {
        return stringToBitmap(this.image);
    }

    public String getImageAsString() {
        return this.image;
    }


    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }
}
