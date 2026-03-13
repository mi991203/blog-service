package com.blog.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 图形验证码工具类
 */
@Component
public class CaptchaUtil {

    @Autowired
    private RedisUtils redisUtils;

    private static final int WIDTH = 120;  // 验证码图片宽度
    private static final int HEIGHT = 40;  // 验证码图片高度
    private static final int CODE_LENGTH = 4;  // 验证码长度
    private static final int LINE_COUNT = 20;  // 干扰线数量
    private static final int OVAL_COUNT = 20;  // 干扰椭圆数量

    /**
     * 生成随机验证码
     * @return 验证码
     */
    public String generateCaptchaCode() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // 去除容易混淆的字符
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    /**
     * 生成图形验证码
     * @param code 验证码文本
     * @return Base64编码的图片字符串
     * @throws IOException IO异常
     */
    public String generateCaptchaImage(String code) throws IOException {
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        // 生成随机字体和颜色
        Random random = new Random();
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // 绘制验证码字符
        for (int i = 0; i < code.length(); i++) {
            // 设置随机颜色
            g.setColor(getRandomColor(0, 100));

            // 计算字符位置
            int x = (i * WIDTH) / code.length() + 5;
            int y = HEIGHT / 2 + random.nextInt(10) - 2;

            // 添加旋转效果
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(random.nextInt(60) - 30)); // 随机旋转-30到30度

            // 绘制字符
            g2d.drawString(String.valueOf(code.charAt(i)), 0, 0);
            g2d.dispose();
        }

        // 绘制干扰线
        for (int i = 0; i < LINE_COUNT; i++) {
            g.setColor(getRandomColor(100, 200));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制干扰椭圆
        for (int i = 0; i < OVAL_COUNT; i++) {
            g.setColor(getRandomColor(150, 250));
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int w = random.nextInt(10);
            int h = random.nextInt(10);
            g.drawOval(x, y, w, h);
        }

        // 绘制波浪线
//        drawWaveLine(g, random);

        g.dispose();

        // 将图片转换为Base64字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 保存验证码到Redis，有效期1分钟
     * @param key 验证码key（如手机号、邮箱等）
     * @param code 验证码
     */
    public void saveVerifyCode(String key, String code) {
        redisUtils.setString("verify_code:" + key, code, 300L); // 300秒有效期
    }

    /**
     * 验证验证码是否正确
     * @param key 验证码key
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    public boolean validateVerifyCode(String key, String code) {
        String storedCode = redisUtils.getString("verify_code:" + key);
        if (storedCode == null) {
            return false;
        }
        return storedCode.equals(code);
    }

    /**
     * 删除验证码
     * @param key 验证码key
     */
    public void removeVerifyCode(String key) {
        redisUtils.deleteToken("verify_code:" + key);
    }

    /**
     * 获取随机颜色
     * @param fc 前景色
     * @param bc 背景色
     * @return 颜色
     */
    private Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制波浪线
     * @param g Graphics对象
     * @param random 随机数生成器
     */
    private void drawWaveLine(Graphics g, Random random) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getRandomColor(100, 200));
        g2d.setStroke(new BasicStroke(2));

        // 绘制正弦波浪线
        int period = random.nextInt(10) + 5; // 周期
        int amplitude = random.nextInt(5) + 3; // 振幅

        for (int i = 0; i < WIDTH - 1; i++) {
            int y1 = (int) (HEIGHT / 2 + amplitude * Math.sin(i * 2 * Math.PI / period));
            int y2 = (int) (HEIGHT / 2 + amplitude * Math.sin((i + 1) * 2 * Math.PI / period));
            g2d.drawLine(i, y1, i + 1, y2);
        }
    }
}