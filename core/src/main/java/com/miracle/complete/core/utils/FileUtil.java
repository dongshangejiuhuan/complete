package com.miracle.complete.core.utils;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.miracle.complete.core.controller.file.FileController;
import com.miracle.complete.entities.DocType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件工具类
 *
 * @author 徐宏坤
 */
public class FileUtil {

    private static final transient Logger LOG = LoggerFactory.getLogger(FileController.class);

    /**
     * 保存上传的文件
     *
     * @param file
     * @return
     */
    public static String saveFile(MultipartFile file, String path, String fileName) {
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }


    /**
     * 文件下载
     *
     * @param response
     * @return
     */
    public static String fileDownload(HttpServletResponse response, String filePath) {
        String filename = "2.jpg";
        File file = new File(filePath + "/" + filename);
        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "下载成功";
    }

    public static int cgiDataExcel(MultipartFile file) throws IOException {
        InputStream input = file.getInputStream();
        //读取工作簿
        XSSFWorkbook workBook = new XSSFWorkbook(input);

        //读取工作表
        XSSFSheet sheet1 = workBook.getSheet("KPI指标（月度填报）");
        XSSFSheet sheet2 = workBook.getSheet("常压储罐（月度填报）");
        XSSFSheet sheet3 = workBook.getSheet("长输管道（月度填报）");
        XSSFSheet sheet4 = workBook.getSheet("故障与检维修管理（月度填报）");
        XSSFSheet sheet5 = workBook.getSheet("维修成本标准化（月度填报）");
        if (sheet1 != null || sheet2 != null || sheet3 != null || sheet4 != null || sheet5 != null) {
            return 1;//表示月度
        }
        XSSFSheet ysheet1 = workBook.getSheet("KPI指标（年度填报）");
        XSSFSheet ysheet2 = workBook.getSheet("生产装置可靠度（年度填报）");
        XSSFSheet ysheet3 = workBook.getSheet("风险管理（年度填报）");
        if (ysheet1 != null || ysheet2 != null || ysheet3 != null) {
            return 2;//表示年度
        }
        return 0;
    }

    /**
     * word转Pdf
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     */
    public void word2Pdf(String sourcePath, String targetPath) throws IOException {

        LOG.info("转换开始-----------------------");
        String docPath = "E:\\奇迹无限\\l炼化交接账号及密码.docx";
        String pdfPath = "E:\\奇迹无限\\l炼化交接账号及密码.pdf";

        XWPFDocument document;
        InputStream doc = new FileInputStream(docPath);
        document = new XWPFDocument(doc);
        PdfOptions options = PdfOptions.create();
        OutputStream out = new FileOutputStream(pdfPath);
        PdfConverter.getInstance().convert(document, out, options);

        doc.close();
        out.close();

        LOG.info("word转Pdf完成------------------------");
    }

    /**
     * 获取license
     * @param doc 表示文档类型，不同的文档获取license的方法不同
     * @return
     */

    public static boolean getLicense(DocType doc) {
        boolean result = false;
        try {
            InputStream is = FileUtil.class.getClassLoader().getResourceAsStream("license.xml");
            if (doc == DocType.EXCEL) {
                com.aspose.cells.License aposeLic = new com.aspose.cells.License();
                aposeLic.setLicense(is);
            } else if (doc == DocType.PPT) {
                com.aspose.slides.License aposeLic = new com.aspose.slides.License();
                aposeLic.setLicense(is);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  EXCEL转换PDF
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     */
    public void excel2Pdf(String sourcePath, String targetPath) {
        // 验证License
        if (!getLicense(DocType.EXCEL)) {
            return;
        }

        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook("E:\\奇迹无限\\标准数据模板（年度）- 201811.xlsx");// 原始excel路径
            File pdfFile = new File("E:\\奇迹无限\\标准数据模板（年度）- 201811test.pdf");// 输出路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);

            wb.save(fileOS, SaveFormat.PDF);

            long now = System.currentTimeMillis();
            LOG.info("共耗时:{}秒", ((now - old) / 1000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  将PPT转成PDF
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     */
    public void ppt2pdf(String sourcePath, String targetPath) {
        // 验证License
        if (!getLicense(DocType.PPT)) {
            return;
        }
        String address = "E:\\奇迹无限\\3333.pptx";
        try {
            long old = System.currentTimeMillis();
            File file = new File("E:\\奇迹无限\\3333.pdf");// 输出pdf路径
            Presentation pres = new Presentation(address);//输入pdf路径
            FileOutputStream fileOS = new FileOutputStream(file);
            pres.save(fileOS, com.aspose.slides.SaveFormat.Pdf);
            fileOS.close();

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + file.getPath()); //转化过程耗时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
