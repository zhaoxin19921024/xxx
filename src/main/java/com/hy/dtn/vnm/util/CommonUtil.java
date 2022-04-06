package com.hy.dtn.vnm.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author yjz
 * @version 1.0
 * @className CommonUtil
 * @date 2021/02/06 10:42
 * @description 公共方法
 * @note 说明
 */
public class CommonUtil {

    /**
     * 颜色的元素
     */
    private static final String COLOR_ELEMENT = "0123456789abcdef";

    /**
     * 颜色的长度值
     */
    private static final Integer COLOR_LENGTH = 6;

    /**
     * @return java.lang.String
     * @description 获取随机颜色并拼接‘#’
     * @methodName getRandomColor
     * @author yjz
     * @date 2021/02/06 10:54
     * @note 修改说明
     */
    public static String getRandomColor() {
        return "#" + randomColor("");
    }

    /**
     * @param color 原色
     * @return java.lang.String
     * @description 获取随机颜色
     * @methodName randomColor
     * @author yjz
     * @date 2021/02/06 10:54
     * @note 修改说明
     */
    private static String randomColor(String color) {
        int index = (int) Math.floor(Math.random() * 16);
        color += COLOR_ELEMENT.substring(index, index + 1);
        if (color.length() == COLOR_LENGTH) {
            return color;
        }
        return randomColor(color);
    }

    /**
     * @param base64Img base64图片
     * @return java.io.InputStream
     * @description Base64转换成InputStream
     * @methodName BaseToInputStream
     * @author yjz
     * @date 2021/03/01 13:56
     * @note 修改说明
     */
    public static InputStream baseToInputStream(String base64Img) {
        ByteArrayInputStream stream;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64Img);
        stream = new ByteArrayInputStream(bytes);
        return stream;
    }

    /**
     * @param bufferedImage bufferedImage
     * @return java.lang.String
     * @description BufferedImage转为base
     * @methodName imageToBase64
     * @author yjz
     * @date 2021/03/01 14:05
     * @note 修改说明
     */
    private static String imageToBase64(BufferedImage bufferedImage, String imgType) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, imgType, byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * @param base64Img base64图片
     * @return java.awt.image.BufferedImage
     * @description Base64转换为BufferedImage
     * @methodName base64StringToBufferedImage
     * @author yjz
     * @date 2021/03/01 13:57
     * @note 修改说明
     */
    private static BufferedImage base64StringToBufferedImage(String base64Img) {
        BufferedImage image = null;
        try {
            InputStream stream = baseToInputStream(base64Img);
            image = ImageIO.read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * @param base64Img 图片的Base64数据
     * @param imgType   图片类型
     * @return java.lang.String
     * @description 压缩图片大小
     * @methodName resizeImage
     * @author yjz
     * @date 2021/03/01 14:10
     * @note 修改说明
     */
    public static String resizeImage(String base64Img, String imgType) {
        try {
            BufferedImage src = base64StringToBufferedImage(base64Img);
            return getBase64Img(imgType, src);
        } catch (Exception e) {
            return base64Img;
        }
    }

    /**
     * @param bytes   源文件
     * @param imgType 图片类型
     * @return java.lang.String
     * @description byte[]源文件压缩
     * @methodName resizeImage
     * @author yjz
     * @date 2021/03/01 16:19
     * @note 修改说明
     */
    public static String resizeImage(byte[] bytes, String imgType) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            BufferedImage src = ImageIO.read(stream);
            return getBase64Img(imgType, src);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param imgType 图片类型
     * @param src     源文件
     * @return java.lang.String
     * @description 获取Base64的图片数据
     * @methodName getBase64Img
     * @author yjz
     * @date 2021/03/01 16:22
     * @note 修改说明
     */
    private static String getBase64Img(String imgType, BufferedImage src) throws IOException {
        BufferedImage output = Thumbnails.of(src).size(50, 30).asBufferedImage();
        String base64 = imageToBase64(output, imgType);
        if (base64.length() - base64.length() / 16 > 40000) {
            double temp = 1 / (base64.length() / 40000D);
            output = Thumbnails.of(output).scale(temp).asBufferedImage();
            base64 = imageToBase64(output, imgType);
        }
        return base64;
    }
}
