package com.miracle.complete.core.service.file.impl;

import com.miracle.complete.core.controller.file.FileController;
import com.miracle.complete.core.service.file.FileHandlerService;
import com.miracle.complete.core.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文件上传处理
 *
 * @author 徐宏坤
 */
@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    private static final transient Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Override
    public String fileHandler(List<MultipartFile> files) {
        String result = "";
        String path = getPath();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            LOG.info("文件名是:{}", fileName);
            int size = (int) file.getSize()/1024;
            LOG.info("大小是" + "-->" + size + "KB");
            String fileType = file.getContentType();
            //判断文件类型
            if (fileType.endsWith("sheet")) {
//                analysisExcel(file);
            }
            LOG.info("文件类型是：{}", file.getContentType());
            if (file.isEmpty()) {
                return "failed";
            }
            result = FileUtil.saveFile(file, path, fileName);
        }
        return result;
    }

    /**
     * 解析excel
     * @param file
     */
    private void analysisExcel(MultipartFile file) {
        int code = 0;
        try {
            code = FileUtil.cgiDataExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1.年 2.月
        if(code==1){

        } else if (code == 2){

        }


    }

    /**
     * 获取文件需要存放的路径
     * @return
     */
    private String getPath() {
        //获取static目录的路径
        String path = this.getClass().getResource("/static").getPath();
        LOG.info("路径是:{}", path);
        return path + "/document";
    }
}
