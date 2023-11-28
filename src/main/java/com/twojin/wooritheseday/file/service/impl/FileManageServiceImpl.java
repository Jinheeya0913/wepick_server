package com.twojin.wooritheseday.file.service.impl;

import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.file.dto.ProfileImgEntity;
import com.twojin.wooritheseday.file.repository.ProfileImgInfoRepository;
import com.twojin.wooritheseday.file.service.FileManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("fileManageService")
@Slf4j
public class FileManageServiceImpl implements FileManageService {

    @Autowired
    ProfileImgInfoRepository profileImgRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadProfileImage(MultipartFile file, String userId) {

        log.debug("[FileManageService] >> uploadFile >> uploadPaht : " + uploadPath);

        if (!file.getContentType().startsWith("image")) {
            log.error("[FileManageService] >> uploadFile >> 이미지 파일이 아닙니다");
            log.error("[FileManageService] >> uploadFile >> getContentTyep : " + file.getContentType());
            // 이미지가 아닐 경우 실패 처리
            return null;
        } else {
            log.debug("[FileManageService] >> uploadFile >> 정상");
            log.error("[FileManageService] >> uploadFile >> getContentTyep : " + file.getContentType());
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String folderPath = "userProfileImg";
        String saveName = uploadPath+File.separator+ folderPath + File.separator+userId+fileExtension;
        Path savePath = Paths.get(saveName);

        log.debug("[FileManageService] >> folderPath >> " + folderPath );
        log.debug("[FileManageService] >> saveName >> " + saveName );
//        log.debug("[FileManageService] >> uploadFile >> " + folderPath );

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            log.debug("[FileManageService] >> IOException ");
            e.printStackTrace();
            return null;
        }

        log.debug("[FileManageService] >> 이미지 정보 저장 >> ");

        ProfileImgEntity profileImgEntity = null;

        try {
            profileImgEntity = profileImgRepository.save(ProfileImgEntity.builder()
                    .userId(userId)
                    .filePath(folderPath)
                    .userId("Y")
                    .build());
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.USER_PROFILE_IMG_UPLOAD.getMessage(), ErrorCode.USER_PROFILE_IMG_UPLOAD);
        }

        return saveName;
    }
}
