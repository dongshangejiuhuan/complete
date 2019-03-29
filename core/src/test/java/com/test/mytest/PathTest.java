package com.test.mytest;

import com.aspose.cells.Workbook;
import com.aspose.slides.License;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import com.miracle.complete.entities.DocType;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class PathTest {

    private static final transient Logger LOG = LoggerFactory.getLogger(PathTest.class);

    @Before
    public void setUp() throws Exception {
    }

    /**
     * word转Pdf
     * @throws IOException
     */
    @Test
    public void word2Pdf() throws IOException {
        LOG.info("转换开始-----------------------");
        String docPath = "E:\\奇迹无限\\11111.doc";
        String pdfPath = "E:\\奇迹无限\\11111.pdf";

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
            InputStream is = PathTest.class.getClassLoader().getResourceAsStream("license.xml");
            if (doc == DocType.EXCEL) {
                com.aspose.cells.License aposeLic = new com.aspose.cells.License();
                aposeLic.setLicense(is);
            } else if (doc == DocType.PPT) {
                License aposeLic = new License();
                aposeLic.setLicense(is);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 支持DOC, DOCX, OOXML, RTF, HTML, OpenDocument, PDF, EPUB, XPS, SWF等相互转换<br>
     *
     */
    @Test
    public void excel2Pdf() {
        // 验证License
        if (!getLicense(DocType.EXCEL)) {
            return;
        }

        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook("E:\\奇迹无限\\标准数据模板（年度）- 201811.xlsx");// 原始excel路径
            File pdfFile = new File("E:\\奇迹无限\\标准数据模板（年度）- 201811test.pdf");// 输出路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);

            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param
     */
    @Test
    public void ppt2pdf() {
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
            pres.save(fileOS, SaveFormat.Pdf);
            fileOS.close();

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + file.getPath()); //转化过程耗时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @After
    public void tearDown() throws Exception {
    }
}