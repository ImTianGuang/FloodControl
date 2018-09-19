package com.tian.cloud.service.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author tianguang
 * 2018/9/19 下午5:32
 **/
public class ZipUtil {

    /**
     * zip操作帮助类
     * bylijinnan
     */
    final static Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 打包指定目录下所有文件，包括子文件夹
     * @param sourceFolder  要打包的文件目录
     * @param outputFolder  生成的压缩文件存放的目录
     * @param zipFileName   生成的压缩文件名
     * @throws IOException
     */
    public static void zipFolder(String sourceFolder, String outputFolder,String zipFileName, String encoding) throws IOException {
        if (isEmptyStr(sourceFolder) || isEmptyStr(outputFolder) || isEmptyStr(zipFileName)) {
            logger.error("invalid parameters.");
            return;
        }
        sourceFolder = formatFilePath(sourceFolder);
        outputFolder = formatFilePath(outputFolder);
        List<String> filelist = generateFileList(sourceFolder);
        zipFileList(filelist, sourceFolder, outputFolder, zipFileName, encoding);
    }


    /**
     * 打包指定的文件。要打包的文件在文件列表中指定
     * @param filelist  要打包的文件列表，这些文件是绝对路径
     * @throws IOException
     */
    public static void zipFileList(List<String> filelist, String sourceFolder, String outputFolder, String zipFileName, String encoding) throws IOException {
        if (filelist == null || filelist.isEmpty()) {
            logger.error("no files to be zip. filelist is null or empty.");
            return;
        }
        if (isEmptyStr(outputFolder) || isEmptyStr(zipFileName)) {
            logger.error("outputFolder and zipFileName are unspecified.");
            return;
        }

        sourceFolder = formatFilePath(sourceFolder);
        outputFolder = formatFilePath(outputFolder);
        if (isEmptyStr(encoding)) {
            encoding = "UTF-8";
        }

        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        byte[] buffer = new byte[1024];

        String outputFullFileName = (outputFolder + "/" + zipFileName);
        FileOutputStream fos = new FileOutputStream(outputFullFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.setEncoding(encoding);
        logger.info("Output to Zip : " + outputFullFileName);

        for (String file : filelist) {
            if (isEmptyStr(file)) {
                continue;
            }
            logger.info("File Added : " + file);
            ZipEntry ze = new ZipEntry(file);   //这里用的是相对路径
            zos.putNextEntry(ze);

            FileInputStream in = new FileInputStream(sourceFolder + "/" + file);        //这里是绝对路径

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
        }

        zos.closeEntry();
        // remember close it
        zos.close();

        logger.info("Compress Done");

    }

    /**
     * Traverse a directory and get all files(include the files in sub directory), add the file into fileList and return it.
     *
     * @param sourceFolder
     *            file or directory
     * @return filelist
     */
    public static List<String> generateFileList(String sourceFolder) {
        List<String> filelist = null;
        if (!isEmptyStr(sourceFolder)) {
            sourceFolder = formatFilePath(sourceFolder);
            filelist = new ArrayList<String>();
            File node = new File(sourceFolder);
            generateFileListHelper(sourceFolder, node, filelist);
        }
        return filelist;
    }

    private static void generateFileListHelper(String sourceFolder, File node, List<String> filelist) {

        // add file only
        if (node.isFile()) {
            String absoluteFile = node.getAbsoluteFile().toString();
            String filepath = generateZipEntry(sourceFolder, absoluteFile);
            filelist.add(filepath);
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                File subFile = new File(node, filename);
                generateFileListHelper(sourceFolder, subFile, filelist);
            }
        }

    }

    /**
     * Format the file path for zip.
     * Delete the "SOURCE_FOLDER" directory info,e.g.
     * d:\ziptest\tmpty.txt         --> tmpty.txt
     * d:\ziptest\sub\t.xls     -->  sub\t.xls
     *
     * @param file
     *            file path
     * @return Formatted file path.
     */
    private static String generateZipEntry(String sourceFolder, String file) {
        logger.debug("sourceFolder=" + sourceFolder);
        logger.debug("file=" + file);
        String formattedPath = file.substring(sourceFolder.length() + 1);
        formattedPath = formatFilePath(formattedPath);
        logger.debug("formattedPath=" + formattedPath);
        return formattedPath;
    }

    /**
     * 将文件路径中的分隔符转换成"/"，并去掉最后的分隔符（如果有）
     * @param str   文件路径
     * @return 格式化后的文件路径
     */
    public static String formatFilePath(String str) {
        if (str != null && str.length() !=0) {
            str = str .replaceAll("\\\\", "/");
        }
        if (str.endsWith("/")) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    private static boolean isEmptyStr(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 解压zip文件，支持子文件夹和中文
     * @param zipFileFullName .zip文件的完整名字，包括文件夹路径
     * @param outputFolder  解压到指定的文件夹。完整路径，如果不指定或者为null，则默认解压到压缩文件所在的当前文件夹
     * @param encoding 编码格式
     */
    @SuppressWarnings("rawtypes")
    public static void unzip(String zipFileFullName, String outputFolder, String encoding) {
        if (zipFileFullName == null || zipFileFullName.length() == 0) {
            return;
        }
        if (!zipFileFullName.endsWith(".zip")) {
            logger.error(zipFileFullName + " is not a zip file.");
            return;
        }
        //change file separator to "/"
        zipFileFullName = zipFileFullName.replaceAll("\\\\", "/");
        //find outputFolder
        String inputFolder = zipFileFullName.replaceAll("/[^/]+\\.zip", "");
        if (outputFolder == null || outputFolder.length() == 0) {
            outputFolder = inputFolder;
        }
        outputFolder = outputFolder.replaceAll("\\\\", "/");

        File outputFolderFile = new File(outputFolder);
        if (!outputFolderFile.exists()) {
            outputFolderFile.mkdirs();
        }
        try {
            ZipFile zip = new ZipFile(zipFileFullName, encoding);
            Enumeration zipFileEntries = zip.getEntries();

            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry =  (ZipEntry) zipFileEntries.nextElement();
                String entryName = entry.getName();
                logger.debug("Extracting,entryName=" + entryName);

                /*用本程序中ZipUtil.zipFolder或者ZipUtil.zipFileList生成的zip文件，如果有子文件夹，entry.getName()会直接得到文件而略过了子文件夹：
                 * 程序生成，解压时输出：
                 * Extracting,entryName=sub/subsub/test.txt
                 * 压缩软件7-Zip生成，解压时输出：
                 * Extracting,entryName=sub/subsub/
                 * Extracting,entryName=sub/subsub/test.txt
                 * 因此要区别对待
                 */

                int lastSlashPos = entryName.lastIndexOf("/");
                if (lastSlashPos != -1 ){
                    String folderStr = outputFolder + "/" + entryName.substring(0, lastSlashPos);
                    File folder = new File(folderStr);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                }
                if (!entryName.endsWith("/")) {     //this entry is not a directory.
                    File outFile = new File(outputFolder + "/" + entryName);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    Writer bw = new BufferedWriter(new OutputStreamWriter(fos, encoding));

                    InputStream in = zip.getInputStream(entry);
                    Reader reader = new InputStreamReader(in, encoding);
                    BufferedReader br =  new BufferedReader(reader);

                    String line;
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                    }
                    bw.close();
                }
            }

        } catch (Exception e) {
            logger.error("errors occur when decompressing.");
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) {

        String sourceFolder = "d:/ziptest";
        String outputFolder = "d:/ziptest";
        String outputFileName = "ziptest.zip";
        try {
            zipFolder(sourceFolder, outputFolder, outputFileName, "GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String zipFileFullName = "d:\\ziptest\\ziptest.zip";
        unzip(zipFileFullName, "d:\\ziptest\\unzip", "GB2312");

    }


}
