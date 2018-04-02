package id.codigo.seedroid_retrofit.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SeedroidUploader {
    private static final int IMAGE = 1;
    private static final int VIDEO = 2;
    private static final int FILE = 3;
    private int scale = 100;
    private List<MultipartBody.Part> fileList = new ArrayList<>();
    private Context context;
    public SeedroidUploader init(Context context){
        this.context = context;
        return new SeedroidUploader();
    }
    public SeedroidUploader setScale(int scale){
        this.scale = scale;
        return this;
    }
    public List<MultipartBody.Part> uploadImages(List<Uri> uri, int type){
        MultipartBody.Part filePart;
        Date d = new Date();
        CharSequence s  = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        for (int i = 0; i < uri.size(); i++) {
            File file = FileUtils.getFile(context, uri.get(i));
            if(type == SeedroidUploader.IMAGE){

                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, scale, bos);

                RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), bos.toByteArray());
                filePart = MultipartBody.Part.createFormData("photos[" + s.toString() + "]",
                        file.getName(), photoBody);
                fileList.add(filePart);
            }else if( type == SeedroidUploader.VIDEO){
                RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), file);
                filePart = MultipartBody.Part.createFormData("videos[" + s.toString() + "]",
                        file.getName(), videoBody);
                fileList.add(filePart);
            }else if( type == SeedroidUploader.FILE){
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), readContentIntoByteArray(file));
                filePart = MultipartBody.Part.createFormData("files[" + s.toString() + "]",
                        file.getName(), fileBody);
                fileList.add(filePart);
            }else{
                Log.e("SeedroidUploader", "file not supported");
            }
        }
        //String fileSize = humanReadableByteCount(file.length(), true);
        return fileList;
    }
    private static byte[] readContentIntoByteArray(File file) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            for (int i = 0; i < bFile.length; i++) {
                System.out.print((char) bFile[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }
}
