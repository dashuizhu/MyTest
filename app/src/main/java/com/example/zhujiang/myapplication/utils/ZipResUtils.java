package com.example.zhujiang.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author zhuj 2018/10/20 下午3:54.
 */
public class ZipResUtils {

    private static final String TAG = ZipResUtils.class.getSimpleName();

    /**
     * 读取文件
     * @throws Exception
     */
    public static Bitmap readGuidePic(String file, String resId) throws Exception {
        String fileName = file.substring(file.length() - 7, file.length() - 4);
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                //Do nothing
            } else {
                Log.i("tag", "file - " + ze.getName() + " : " + ze.getSize() + " bytes");
                if (ze.getName().equals(resId)) {
                    InputStream is = zf.getInputStream(ze);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
            }
        }
        zin.closeEntry();
        return null;
    }

    /**
     * 解压zip到指定的路径
     * @param zipFileString  ZIP的名称
     * @param outPathString   要解压缩路径
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String  szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                Log.e(TAG,outPathString + File.separator + szName);

                //if (!szName.endsWith(filter)) {
                //    continue;
                //}
                File file = new File(outPathString + File.separator + szName);
                if (!file.exists()){
                    Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    /**
     * 解压指定的文件名
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString,String  szName) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String fileName;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            fileName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                //szName = szName.substring(0, szName.length() - 1);
                //File folder = new File(outPathString + File.separator + szName);
                //folder.mkdirs();
            } else {
                if (!fileName.equals(szName)) {
                    continue;
                }
                Log.e(TAG,outPathString + File.separator + szName);
                File file = new File(outPathString + File.separator + fileName);
                if (!file.exists()){
                    Log.e(TAG, "Create the file:" + outPathString + File.separator + fileName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    /**
     * 压缩文件和文件夹
     * @param srcFileString   要压缩的文件或文件夹
     * @param zipFileString   解压完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString)throws Exception {
        //创建ZIP
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        //创建文件
        File file = new File(srcFileString);
        //压缩
        ZipFiles(file.getParent()+File.separator, file.getName(), outZip);
        //完成和关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam)throws Exception{
        if(zipOutputSteam == null)
            return;
        File file = new File(folderString+fileString);
        if (file.isFile()) {
            ZipEntry zipEntry =  new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while((len=inputStream.read(buffer)) != -1)
            {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        }
        else {
            //文件夹
            String fileList[] = file.list();
            //没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry =  new ZipEntry(fileString+File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            //子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString, fileString+ File.separator+fileList[i], zipOutputSteam);
            }
        }
    }

    /**
     * 返回zip的文件输入流
     * @param zipFileString  zip的名称
     * @param fileString     ZIP的文件名
     * @return InputStream
     * @throws Exception
     */
    public static InputStream UpZip(String zipFileString, String fileString)throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }

    /**
     * 返回ZIP中的文件列表（文件和文件夹）
     * @param zipFileString     ZIP的名称
     * @param bContainFolder    是否包含文件夹
     * @param bContainFile      是否包含文件
     * @return
     * @throws Exception
     */
    public static List<File> getFileList(String zipFileString, boolean bContainFolder, boolean bContainFile)throws Exception {
        List<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // 获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (bContainFolder) {
                    fileList.add(folder);
                }
            } else {
                File file = new File(szName);
                if (bContainFile) {
                    fileList.add(file);
                }
            }
        }
        inZip.close();
        return fileList;
    }



}
