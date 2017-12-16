package me.khrystal.threesomeandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import java.io.File;

/**
 * usage: intent util
 * author: kHRYSTAL
 * create time: 17/12/16
 * update time:
 * email: 723526676@qq.com
 */

public class IntentUtil {

    public static void intentToSendMail(Context context, String email) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:" + email));
        try {
            context.startActivity(i);
        } catch (Exception e) {

        }
    }

    public static void dialTo(Context context, String num) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + num));
        try {
            context.startActivity(i);
        } catch (Exception e) {

        }
    }


    public static Intent sendSmsWithBody(String number, String body) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + number));
        sendIntent.putExtra("sms_body", body);
        return sendIntent;
    }

    public static void intentToSystemSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    public static Intent intentToSelectVideo() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        innerIntent.setType("video/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        return wrapperIntent;
    }

    public static Intent intentToCaptureImage(String filepath) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(filepath)));
        return cameraIntent;
    }

    public static Intent cropIntent(Uri inUri, int outputX, int outputY, Uri outUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(inUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", outputX);
        cropIntent.putExtra("aspectY", outputY);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        cropIntent.putExtra("return-data", false);
        return cropIntent;
    }
}
