package com.twojin.wooritheseday.file.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.nio.file.Path;

@Data
@AllArgsConstructor
public class FileVo {

    Resource resource;
    Path path;
    String filePath;

}
