package cn.mrian22.validate.entity;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author 22
 * 多了一个BufferedImage属性，存放图片
 */
public class ImageCode extends Code {

    private BufferedImage image;

    public ImageCode(BufferedImage image,String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image,String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
