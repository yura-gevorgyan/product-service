package org.example.productservice.util;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;

public class FileUtil {


    private static String uploadDirectory = "C:/Users/Yura/IdeaProjects/project/product-service/images";

    @SneakyThrows
    public static byte[] getPicture(String picName) {
        File file = new File(uploadDirectory, picName);
        if (file.exists()) {
            byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
            if (byteArray.length == 0) {
                return null;
            }
            return byteArray;
        }
        return null;
    }
}
