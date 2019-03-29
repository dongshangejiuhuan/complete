package com.miracle.complete.core.controller.file;

import com.miracle.complete.core.service.file.impl.FileHandlerServiceImpl;
import com.miracle.complete.core.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件控制器
 * @author 徐宏坤
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static  final transient Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileHandlerServiceImpl fileService;
    /**
     * 上传文件
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String fileUpload(HttpServletRequest request){
        LOG.info("上传开始");
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        LOG.info("本次上传{}个文件", files.size());
        if (files.isEmpty()) {
            return "failed";
        }
//
        String result = fileService.fileHandler(files);


        LOG.info("上传结束,结果:{}",result );
        return result;
    }

    /**
     * 下载文件
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response){
        String path = "F:/test";
        FileUtil.fileDownload(response, path);
        return null;
    }
}
