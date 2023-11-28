package com.twojin.wooritheseday.file.service;

import com.twojin.wooritheseday.user.entity.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileManageService {

    public String uploadProfileImage(MultipartFile file, String userId);
}
