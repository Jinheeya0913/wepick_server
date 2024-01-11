package com.twojin.wooritheseday.file.service;

import com.twojin.wooritheseday.file.dto.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

public interface FileUserService {

    public String uploadProfileImage(MultipartFile file, String userId);

    public String uploadReviewImages(MultipartFile file, String userId);

    public FileVo downloadProfileImage(String fileName);
}
