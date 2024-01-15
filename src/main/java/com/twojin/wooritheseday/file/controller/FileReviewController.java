package com.twojin.wooritheseday.file.controller;

import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.file.dto.vo.FileVo;
import com.twojin.wooritheseday.file.service.FileReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/file/review")
@Slf4j
public class FileReviewController {

    @Autowired
    FileReviewService fileReviewService;

    @RequestMapping("getReviewImg")
    public ResponseEntity<Resource> getReviewThumbnail(@RequestParam String pid) {

        Resource resource = null;
        HttpHeaders headers = new HttpHeaders();

        try {
            FileVo vo = fileReviewService.getReviewThumbnail(pid);
            resource = vo.getResource();

            if (resource != null) {
                log.debug("[getProfileImage] >> 성공 1");
                log.debug("[getProfileImage] >> 성공 1 : Files.probeContentType(vo.getPath()) : " + Files.probeContentType(vo.getPath()) );
                headers.add("Content-Type", Files.probeContentType(vo.getPath()));
            } else {
                log.error("[getProfileImage] >> 실패 1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("[getProfileImage] >> 이미지 가져오기 실패 1");
        }


        return ResponseEntity.ok(resource);


    }



}
