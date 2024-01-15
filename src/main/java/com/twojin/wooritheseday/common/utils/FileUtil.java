package com.twojin.wooritheseday.common.utils;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.util.CustomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileUtil {

    @Value("${upload.path}")
    private String uploadPath;

    public static String createSaveName(MultipartFile file, String uploadPath ,String folderName) {

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String foloderPath = folderName;
        String uuid = CustomStringUtils.createRandomUUid();

        String saveName = uploadPath + File.separator + foloderPath + File.separator + uuid + fileExtension;
        return saveName;
    }

    public static String getFullPathFromEntity(String uploadPath, String folderName, String fileName, String extension) {
         String fullPath = uploadPath + File.separator + folderName + File.separator + fileName + extension;
        return fullPath;
    }

    public  Map<String,String> saveMultiFileImg(MultipartFile file, String folderPath) {
        Map<String,String> resultMap = new HashMap<String ,String >();

        String contentType = file.getContentType();

        log.debug("[FileUtil] >> saveMultiFileImg :: contentType >> "+ contentType);

        if (!contentType.startsWith("image") && !contentType.startsWith("png")) {
            throw new BusinessExceptionHandler(ErrorCode.FILE_WRONG_FORMAT.getMessage(), ErrorCode.FILE_WRONG_FORMAT);
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid  = CustomStringUtils.createRandomUUid();

        LocalDate today = LocalDate.now();
        String fileName = "";
        fileName = fileName+today.getYear()+today.getMonth()+uuid;

        // 업로드 경로 / 폴더 / 월+일+uuid.extension
        String saveName = uploadPath+File.separator+ folderPath +File.separator+fileName+fileExtension;
        Path savePath = Paths.get(saveName);

        log.debug("[FileUtil] >> saveMultiFileImg :: folderPath >> " + folderPath);
        log.debug("[FileUtil] >> saveMultiFileImg :: saveName >> " + saveName);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            log.error("[FileUtil] >> saveMultiFileImg :: error ");
            log.error(e.getMessage());
            return  null;
        }

        resultMap.put("fileName", fileName);
        resultMap.put("saveName", saveName);
        resultMap.put("fileExtension", fileExtension);

        return resultMap;
    }

    public static boolean deleteMultiFileImgs(String folderPath, List<String> saveNameList) {
        log.debug("[FileUtil] >> deleteMultiFileImgs :: 파일 삭제 수행");
        for (String saveName : saveNameList) {
            File file = new File(saveName);
            if (file.exists()) {
                if (file.delete()) {
                    log.debug("[FileUtil] >> deleteMultiFileImgs :: 파일 삭제 성공");
                } else {
                    log.error("[FileUtil] >> deleteMultiFileImgs :: 파일 삭제 실패");
                    return false;
                }
            } else {
                log.debug("[FileUtil] >> deleteMultiFileImgs :: 파일이 존재하지 않습니다");
                return false;
            }
        }

        return true;

    }

}
