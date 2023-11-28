package com.twojin.wooritheseday.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUserService {

    public String uploadProfileImage(MultipartFile file, String userId);

    public void downloadProfileImage(String fileName);
}
