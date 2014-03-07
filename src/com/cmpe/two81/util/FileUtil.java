package com.cmpe.two81.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: jue
 * Date: 11/9/13
 * Time: 12:16 AM
 */

public class FileUtil {


    public static void storeObject(Object object, String path, String fileName) throws IOException {
        FileOutputStream fileOS = null;
        ObjectOutputStream objectOS = null;
        try {
            new File(path).mkdirs();
            File file = new File(path, fileName);
            System.out.println(file.getAbsolutePath());
            file.createNewFile();
            fileOS = new FileOutputStream(file);
            objectOS = new ObjectOutputStream(fileOS);
            objectOS.writeObject(object);
            objectOS.flush();
        } finally {
            if (fileOS != null) {
                fileOS.close();
            }
            if (objectOS != null) {
                objectOS.close();
            }
        }
    }

    public static void removeObject(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        if (file.exists())
            file.delete();
    }

//	public static Object loadObject(String fileName) throws IOException {
//		return loadObject(null, fileName);
//	}

    public static Object loadObject(String path, String fileName) throws IOException {
        ObjectInputStream objectIS = null;
        try {
            objectIS = new ObjectInputStream(path == null ? FileUtil.class.getClassLoader().getResourceAsStream(fileName) :
                    new FileInputStream(new File(path, fileName)));
            return objectIS.readObject();
        } catch (Exception e) {
            return null;
        } finally {
            if (objectIS != null) {
                objectIS.close();
            }
        }
    }

}
