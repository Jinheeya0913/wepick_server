package com.twojin.wooritheseday.common.utils;

import com.twojin.wooritheseday.user.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtil {

    public static String createSaveName(MultipartFile file, String uploadPath ,String folderName) {

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String foloderPath = folderName;
        String uuid = StringUtils.createRandomUUid();

        String saveName = uploadPath + File.separator + foloderPath + File.separator + uuid + fileExtension;
        return saveName;
    }

    public static String getFullPathFromEntity(String uploadPath, String folderName, String fileName, String extension) {
         String fullPath = uploadPath + File.separator + folderName + File.separator + fileName + extension;
        return fullPath;
    }

}
