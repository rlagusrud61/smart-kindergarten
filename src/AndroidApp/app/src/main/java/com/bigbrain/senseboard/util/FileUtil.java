package com.bigbrain.senseboard.util;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    private Context context;
    private FileOutputStream fos = null;
    private String fileName;

    public FileUtil(Context context) {
        this.context = context;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setup() {
        try {
            this.fos = context.openFileOutput(fileName, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeData(float[] data) {
        try {
            StringBuilder line = new StringBuilder();
            for (float f : data) {
                line.append(f).append(',');
            }
            line.deleteCharAt(line.length()-1);
            line.append("\n");
            fos.write(line.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void saveToFile(String fileName, String data) {
//        try {
//            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//            fos.write(data.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
