package id.codigo.seedroid_uikit.sdkutils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Locale;

/**
 * Created by papahnakal on 01/03/18.
 */

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    public FileUtil() {
    }

    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean isIllegalPath(String filePath) {
        return filePath.indexOf("../") != -1 || filePath.indexOf("./") != -1 || filePath.indexOf("..\\") != -1 || filePath.indexOf(".\\") != -1 || filePath.toLowerCase(Locale.getDefault()).indexOf("0x00") != -1 || filePath.toLowerCase(Locale.getDefault()).indexOf("%00") != -1 || filePath.toLowerCase(Locale.getDefault()).indexOf("u+0000") != -1;
    }

    public static boolean isIllegalType(String filePath, String fileType) {
        return !TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(fileType)?!filePath.endsWith(fileType.toLowerCase(Locale.getDefault())):false;
    }

    public static boolean isIllegalSize(String filePath, long maximumSize) {
        File file = new File(filePath);
        return file.exists() && file.isFile()?file.length() > maximumSize << 20:false;
    }

    public static boolean isIllegal(String filePath, String type, long maximumSize) {
        return isIllegalPath(filePath)?true:(isIllegalType(filePath, type)?true:isIllegalSize(filePath, maximumSize));
    }

    public static String readFileByLines(String fileName) {
        StringBuffer result = null;
        BufferedReader reader = null;

        String line;
        try {
            File file = new File(fileName);
            if(file.exists()) {
                reader = new BufferedReader(new FileReader(file));
                //line = null;
                result = new StringBuffer();
                //LineIterator lineIterator = new LineIterator(reader);

                try {
                    line = reader.readLine();
                    while ( line != null ) {
                        // Do something with line
                        result.append(line);
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
               /*
                while(lineIterator.hasNext()) {
                    line = lineIterator.next();
                    result.append(line);
                }*/

                return null != result && result.length() > 0?result.toString():null;
            }

            Log.d(TAG, file.getName() + " is not exist.");
            return null != result && result.length() > 0?result.toString():null;
        } catch (IOException var9) {
            Log.e(TAG, var9.toString());
            line = null;
        } finally {
            closeReader(reader);
        }

        return line;
    }

    public static String getContent(InputStream is) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        /*String line = null;
        LineIterator lineIterator = new LineIterator(br);

        while(lineIterator.hasNext()) {
            line = lineIterator.next();
            sb.append(line);
        }*/
        try {
        String line = br.readLine();
            while ( line != null ) {
                // Do something with line
                sb.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sb.toString();
    }

    public static void saveContentToFile(String filePath, String content) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filePath);
            fos.write(content.getBytes());
        } catch (FileNotFoundException var8) {
            Log.e(TAG, "Invalid file");
        } catch (IOException var9) {
            Log.e(TAG, var9.toString());
        } finally {
            closeOutputStream(fos);
        }

    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if(isExist(fileName)) {
            boolean result = file.delete();
            if(!result) {
                Log.e(TAG, "Fail to delete file, fileName=" + file.getName());
            }
        }

    }

    public static void closeReader(Reader reader) {
        try {
            if(null != reader) {
                reader.close();
            }
        } catch (IOException var2) {
            Log.e(TAG, var2.toString());
        }

    }

    public static void closeOutputStream(OutputStream outputStream) {
        try {
            if(null != outputStream) {
                outputStream.flush();
            }
        } catch (IOException var3) {
            Log.e(TAG, var3.toString());
        }

        try {
            if(null != outputStream) {
                outputStream.close();
            }
        } catch (IOException var2) {
            Log.e(TAG, var2.toString());
        }

    }

    public static void closeInputStream(InputStream inputStream) {
        try {
            if(null != inputStream) {
                inputStream.close();
            }
        } catch (IOException var2) {
            Log.e(TAG, var2.toString());
        }

    }

    public static void copy(String srcFile, String destFile) throws IOException {
        copy(new File(srcFile), new File(destFile));
    }

    public static void copy(File srcFile, File destFile) throws IOException {
        boolean result = destFile.getParentFile().mkdirs();
        Log.d(TAG, "mkdirs result=" + result);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];

            while(true) {
                int len = fis.read(buf);
                if(len < 0) {
                    return;
                }

                fos.write(buf, 0, len);
            }
        } finally {
            closeInputStream(fis);
            closeOutputStream(fos);
        }
    }
}
