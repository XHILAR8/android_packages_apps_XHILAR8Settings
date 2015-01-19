
package com.xhilar8.settings;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

public class Util {

    public static void save(Bitmap b, File dest) {
        try {
            new File(dest.getParent()).mkdirs();
            FileOutputStream fos;
            fos = new FileOutputStream(dest);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getDevice(){
        return Build.PRODUCT.toLowerCase();
    }

    public static void restartSystemUI() {
        Log.d("XH8", "RESTARTING SYSTEMUUI");
        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (su != null) {
            try {
                DataOutputStream os = new DataOutputStream(su.getOutputStream());
                os.writeBytes("pkill com.android.systemui \n");
                os.flush();
                os.writeBytes("exit\n");
                os.flush();
                su.waitFor();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reboot(Context c) {
        try {
            Process proc = Runtime.getRuntime().exec(
                    new String[] {
                            "su", "-c", "reboot"
                    });
            proc.waitFor();
        } catch (Exception ex) {
        }
    }

    public static void powerOff(Context c) {
        try {
            Process proc = Runtime.getRuntime().exec(
                    new String[] {
                            "su", "-c", "poweroff"
                    });
            proc.waitFor();
        } catch (Exception ex) {
        }
    }

    public static int getSpinnerColourCode(int pos) {
        // 0 is default
        if (pos == 1)
            return Color.RED;
        else if (pos == 2)
            return Color.YELLOW;
        else if (pos == 3)
            return Color.parseColor("#DF7401");
        else if (pos == 4)
            return Color.GREEN;
        else if (pos == 5)
            return Color.BLUE;
        else if (pos == 6)
            return Color.parseColor("#9A2EFE");
        else if (pos == 7)
            return Color.WHITE;
        else if (pos == 8)
            return Color.BLACK;
        return 0;
    }
}
