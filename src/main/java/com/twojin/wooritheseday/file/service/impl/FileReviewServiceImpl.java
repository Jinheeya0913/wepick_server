package com.twojin.wooritheseday.file.service.impl;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.file.dto.ProfileImgEntity;
import com.twojin.wooritheseday.file.dto.ReviewImgEntity;
import com.twojin.wooritheseday.file.dto.vo.FileVo;
import com.twojin.wooritheseday.file.repository.ReviewImgInfoRepository;
import com.twojin.wooritheseday.file.service.FileReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("fileReviewService")
@Slf4j
public class FileReviewServiceImpl implements FileReviewService {

    @Autowired
    ReviewImgInfoRepository reviewImgInfoRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public FileVo getReviewThumbnail(String fileName) {
        FileSystemResource resource = null;
        Path filePath = null;
        FileVo fileVo = null;
        String fullPath = "";

//
//        log.debug("[downloadProfileImage] >> 이미지 가져오기 시작");
//
        try {
            ReviewImgEntity imgEntity = reviewImgInfoRepository.findByFileName(fileName)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.FILE_IMG_NOT_FOUND.getMessage(), ErrorCode.FILE_IMG_NOT_FOUND));

            String folderPath = imgEntity.getFilePath();

            fullPath = uploadPath + File.separator + folderPath + File.separator + fileName + imgEntity.getFileExtension();

            log.debug("[downloadProfileImage] >> fullPath : " + fullPath);

            resource = new FileSystemResource(fullPath);
            filePath = Paths.get(fullPath);


            if (!resource.exists()) {
                log.error("[downloadProfileImage] >> 파일 못찾음");
                throw new FileNotFoundException();
            } else {
                log.debug("[downloadProfileImage] >> 파일 찾음");
                log.debug("[downloadProfileImage] >> filePath : " + filePath.toString());
            }

        } catch (FileNotFoundException e) {
            log.error("[downloadProfileImage] >> FileNotFoundException");
            throw new BusinessExceptionHandler(ErrorCode.FILE_IMG_NOT_FOUND.getMessage(), ErrorCode.FILE_IMG_NOT_FOUND);
        } catch (Exception e) {
            log.error("[downloadProfileImage] >> Exception");
            throw new BusinessExceptionHandler(ErrorCode.FILE_IMG_DOWNLOAD.getMessage(), ErrorCode.FILE_IMG_DOWNLOAD);
        }


        fileVo = new FileVo(resource, filePath, fullPath);

        return fileVo;
    }
}
