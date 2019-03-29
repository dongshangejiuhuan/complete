package com.miracle.complete.core.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件处理接口
 * @author 徐宏坤
 */
public interface FileHandlerService {


    /**
     * 文件上传
     * @param files
     * @return
     */
    String fileHandler(List<MultipartFile> files);
}
