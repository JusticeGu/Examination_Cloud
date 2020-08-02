package com.exam.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaogu
 * @date 2020/7/18 12:58
 **/
public interface FileService {
    public String fileupload(MultipartFile multipartFile);
}
