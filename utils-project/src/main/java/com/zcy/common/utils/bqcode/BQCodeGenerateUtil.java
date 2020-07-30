package com.zcy.common.utils.bqcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class BQCodeGenerateUtil {
    /**
     * 二维码大小
     */
    private static final int QR_CODE_SIZE = 430;
    /**
     * 二维码加文字高度
     */
    private static final int QR_HEIGHT = 325;
    /**
     * 条形码+文字高度
     */
    private static final int BAR_HEIGHT = 100;
    /**
     * 条形码高度
     */
    private static final int HEIGHT = 75;
    /**
     * 宽度
     */
    private static final int LOGO_SIZE = 80;
    /**
     * 生成参数
     */
    private static Hashtable hints = new Hashtable() {
        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级
            put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8); // 指定显示编码
            put(EncodeHintType.MARGIN, 1); // 边缘
        }
    };
    private static MultiFormatWriter multiFormatWriter = new MultiFormatWriter();


    /**
     * 生成二维码
     *
     * @param content 源内容
     * @return 返回一张二维码图片
     * @throws Exception
     */
    private static BufferedImage createQrImage(String content, Integer size) throws Exception {
        size = size == null || size == 0 ? QR_CODE_SIZE : size;
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        //绘制二维码,logo无颜色
        // BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        //绘制二维码,logo有颜色
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x00000000 : 0xFFFFFFFF);
            }
        }

//        int[] pixels = new int[width * height];
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                // 二维码颜色（RGB）
//                int r = (int) (50 - (50.0 - 13.0) / height * (y + 1));
//                int g = (int) (165 - (165.0 - 72.0) / height * (y + 1));
//                int b = (int) (162 - (162.0 - 107.0) / height * (y + 1));
//                Color color = new Color(r, g, b);
//                int colorInt = color.getRGB();
//                // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
//                pixels[y * width + x] = bitMatrix.get(x, y) ? colorInt : 0x00ffffff;// 0x000000:0xffffff
//            }
//        }
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        image.getRaster().setDataElements(0, 0, width, height, pixels);
        // 返回二维码图片
        return image;
    }

    /**
     * 生成条形码
     *
     * @param content 内容
     * @param width   宽度
     * @param height  高度
     * @return
     * @throws WriterException
     */
    private static BufferedImage createBarCodeImage(String content, int width, int height) throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.CODE_128, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return image;
    }

    /**
     * 插入图片
     *
     * @param source   二维码
     * @param logoPath logo图片
     * @return
     * @throws Exception
     */
    private static BufferedImage insertLogo(BufferedImage source, String logoPath, Integer size) throws Exception {
        File file = new File(logoPath);
        // 判断文件是否存在
        if (!file.exists())
            return source;
        Image src = ImageIO.read(file);
        // 插入logo
        if (size == null || size == 0)
            size = LOGO_SIZE;
        // 获取画笔根据
        Graphics2D graph = source.createGraphics();
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        graph.drawImage(src, x, y, size, size, null);
        Shape shape = new RoundRectangle2D.Float(x, y, size, size, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
        return source;
    }

    /**
     * 插入文字
     *
     * @param image
     * @param text
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage insertText(BufferedImage image, String text, int width, int height) {
        if (null == text || "".equals(text))
            return image;
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outImage.createGraphics();
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        // 设置白色
        g2d.setColor(Color.WHITE);
        //填充整个屏幕
        g2d.fillRect(0, 0, 600, 600);
        //设置笔刷
        g2d.setColor(Color.BLACK);
        // 画条形码到新的面板
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        // 画文字到新的面板
        g2d.setColor(new Color(0, 0, 0));
        // 字体、字型、字号
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        //文字长度
        int strWidth = g2d.getFontMetrics().stringWidth(text);
        //总长度减去文字长度的一半  （居中显示）
        int wordStartX = (width - strWidth) / 2;
        int wordStartY = image.getHeight() + 18;
        // 画文字
        g2d.drawString(text, wordStartX, wordStartY);
        g2d.dispose();
        outImage.flush();
        return outImage;
    }

    /**
     * 图片插入二维码
     *
     * @param content     内容
     * @param startWidth  起始x坐标
     * @param startHeight 起始y坐标
     * @param size        二维码大小
     * @param file        背景图片
     * @return
     * @throws IOException
     * @throws WriterException
     */
    private static BufferedImage insertImage(String content, File file, Integer startWidth, Integer startHeight, Integer size) throws IOException, WriterException {
        // 读取背景图片
        BufferedImage outImage = ImageIO.read(file);
        int width = outImage.getWidth();
        int height = outImage.getHeight();
        // 生成二维码，参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        size = size == null || size == 0 ? width / 2 : size;
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        // 开始绘制的中心位置
        startWidth = startWidth == null ? (width - size) / 2 : startWidth;
        startHeight = startHeight == null ? (height - size) / 2 : startHeight;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int x1 = x + startWidth;
                int y1 = y + startHeight;
                outImage.setRGB(x1, y1, bitMatrix.get(x, y) ? 0x00000000 : outImage.getRGB(x1, y1));
            }
        }
        outImage.flush();
        return outImage;
    }

    /**
     * 组合二维码和背景图片
     *
     * @param codeImage       二维码
     * @param bbackgroundFile 背景图片
     * @return
     * @throws IOException
     */
    private static BufferedImage groupImage(BufferedImage codeImage, File bbackgroundFile) throws IOException {
        BufferedImage resImage = ImageIO.read(bbackgroundFile);
        Graphics2D g2d = resImage.createGraphics();
        int width = resImage.getWidth();
        int height = resImage.getHeight();
        int codeWidth = codeImage.getWidth();
        int codeHeight = codeImage.getHeight();
        int x = (width - codeWidth) / 2;
        int y = (height - codeHeight) / 2;
        g2d.drawImage(codeImage, x, y, codeWidth, codeHeight, null);
        g2d.dispose();
        resImage.flush();
        return resImage;
    }

    /**
     * 从二维码/条形码中解析数据
     *
     * @param imgPath 文件路径
     * @return
     * @throws IOException
     * @throws NotFoundException
     */
    private static String decodeCode(String imgPath) throws IOException, NotFoundException {
        BufferedImage image = ImageIO.read(new File(imgPath));

        if (image == null)
            throw new RuntimeException("未找到资源路径, " + imgPath);
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }


//    public static void main(String[] args) throws Exception {
//        String logoPath = BQCodeGenerateUtil.class.getResource("/").getPath().concat("logo.png");
//        String path = System.getProperty("user.dir").concat("/source/qrcode.png");
//        String path2 = System.getProperty("user.dir").concat("/source/qr.png");
//        BufferedImage qrimage = createQrImage("DDASD0ASDASVW3108130052123512",200);
////        qrimage = insertLogo(qrimage, logoPath, LOGO_SIZE);
////        qrimage = insertText(qrimage, "中国制造", QR_CODE_SIZE, QR_HEIGHT);
//        ImageIO.write(qrimage, FileSuffixTypeConstant.PNG, new File(path2));
//
//        String pathname = System.getProperty("user.dir").concat("/source/girl.jpg");
//        ImageIO.write(groupImage(qrimage, new File(pathname)), FileSuffixTypeConstant.PNG, new File(path));
//        // 背景图片加二维码
////        String path3 = System.getProperty("user.dir").concat("/source/img.jpg");
////        String img = BQCodeGenerateUtil.class.getResource("/").getPath().concat("b1.jpg");
////        File file = new File(img);
////        BufferedImage image = insertImage("12348653212314DSFAFASF84AS1D65", file, 0, 800, 400);
////        image = insertLogo(image, logoPath, 60);
////        ImageIO.write(image, ImageFormatNameConstant.JPG, new File(path3));
//        //条形码
////        String path2 = System.getProperty("user.dir").concat("/source/barcode.jpg");
////        BufferedImage barCodeImage = createBarCodeImage("008130052123512", SIZE, HEIGHT);
////        barCodeImage = insertText(barCodeImage, "008130052123512", SIZE, BAR_HEIGHT);
////        ImageIO.write(barCodeImage, ImageFormatNameConstant.JPG, new File(path2));
//
//
//    }
}
