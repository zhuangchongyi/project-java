package com.zcy.common.utils.image;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.zcy.common.constants.FileSuffixTypeConstant;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageTransformPdfUtil {
    public static void imagesToPdf(String imageFolderPath, String savePdfPath) {
        try {
            File pdfFile = new File(savePdfPath);
            if (!pdfFile.exists())
                pdfFile.createNewFile();
            OutputStream pdfOut = new FileOutputStream(savePdfPath);
            // 创建文档
            Document doc = new Document();
            doc.setMargins(0, 0, 0, 0);
            // 写入PDF文档
            PdfWriter.getInstance(doc, pdfOut);
            // 打开文档
            doc.open();
            //获取图片
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            int width = 800;
            for (File file1 : files) {
                if (file1.getName().toLowerCase().endsWith(FileSuffixTypeConstant.DOT_JPG)
                        || file1.getName().toLowerCase().endsWith(FileSuffixTypeConstant.DOT_PNG)
                        || file1.getName().toLowerCase().endsWith(FileSuffixTypeConstant.DOT_GIF)
                        || file1.getName().toLowerCase().endsWith(FileSuffixTypeConstant.DOT_JPEG)
                        || file1.getName().toLowerCase().endsWith(FileSuffixTypeConstant.DOT_TIF)) {
                    String imgPath = file1.getAbsolutePath();
                    // 读取图片流
                    BufferedImage bImg = ImageIO.read(new File(imgPath));
                    int height = width * bImg.getHeight() / bImg.getWidth();
                    // 实例化图片
                    Image image = Image.getInstance(imgPath);
                    image.setAlignment(Image.ALIGN_CENTER);
                    image.scaleAbsolute(width, height);
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(width, height));
                    // 新建页面
                    doc.newPage();
                    // 添加图片到文档
                    doc.add(image);
                }
            }
            doc.close();
            pdfOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf 导出图片
     *
     * @param pdfPath   pdf文件路径
     * @param savePath  导出图片名称或者保存路径
     * @param fileType  图片类型
     * @param isLongImg 是否导出长图
     * @throws IOException
     */
    public static void pdfToImage(String pdfPath, String savePath, String fileType, boolean isLongImg) throws IOException {
        // 读取PDF文件
        PDDocument pdDocument = PDDocument.load(new File(pdfPath));
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        int pages = pdDocument.getNumberOfPages();
        File file = new File(savePath);
        if (!file.exists() && !isLongImg) {
            file.mkdirs();
        }
        BufferedImage image = null;
        for (int i = 2; i < 10; i++) {
            BufferedImage img = //pdfRenderer.renderImage(i);
            pdfRenderer.renderImageWithDPI(i, 500, ImageType.RGB);
            image = getPdfImage(image, img);
            if (!isLongImg) {
                String fileName = "image" + i + FileSuffixTypeConstant.DOT_PNG;
                ImageIO.write(img, fileType, new FileOutputStream(savePath + File.separator + fileName));
            }
        }
        // 导出长图
        if (isLongImg)
            ImageIO.write(image, fileType, file);
    }

    private static BufferedImage getPdfImage(BufferedImage image, BufferedImage img) {
        if (image == null) {
            return img;
        } else {
            BufferedImage resultImg;
            int height = img.getHeight() + image.getHeight();
            if (img.getWidth() > image.getWidth()) {
                resultImg = new BufferedImage(img.getWidth(), height, BufferedImage.TYPE_INT_RGB);
            } else {
                resultImg = new BufferedImage(image.getWidth(), height, BufferedImage.TYPE_INT_RGB);
            }
            Graphics2D g2d = resultImg.createGraphics();
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            g2d.drawImage(img, 0, image.getHeight() + 1, img.getWidth(), img.getHeight(), null);
            g2d.dispose();
            resultImg.flush();
            return resultImg;
        }
    }


    public static void main(String[] args) throws IOException {

        String imageFolderPath = "C:\\Users\\DELL\\Pictures\\Saved Pictures\\";
        String path = System.getProperty("user.dir");
        String savePdfPath = path.concat("/source/img.pdf");
        // imagesToPdf(imageFolderPath, savePdfPath);

        String pdfToimg = path.concat("/source/pdfToimg.png");
        String savePath = path.concat("/source/img");
//        pdfToImage(savePdfPath, savePath, FileSuffixTypeConstant.PNG, false);


        String pdfPath2 = "F:\\我的笔记\\book\\JVM高级特性与最佳实践（最新第二版）.pdf";
        String savePath2 = "F:\\我的笔记\\book\\jvm";
        pdfToImage(pdfPath2, savePath2, FileSuffixTypeConstant.PNG, false);
//        imagesToPdf(savePath2, savePath2 + "\\jvm.pdf");
    }
}
