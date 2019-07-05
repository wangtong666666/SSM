package ky.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ky.entity.TSysUser;


@Controller
@RequestMapping("/verifycode")
public class VerifyCodeAction extends BaseAction<TSysUser> {

    public Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    @RequestMapping("/verifyCode")
    //  @Action(value="verifyCode", results={@org.apache.struts2.convention.annotation.Result(name="verifyCode", location="/login.jsp")})
    public String verifyCode() {
        int width = 60;
        int height = 35;
        BufferedImage image = new BufferedImage(width, height,
                1);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Times New Roman", 0, 25));

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String codeList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        String sRand = "";

        for (int i = 0; i < 4; i++) {
            int a = random.nextInt(codeList.length() - 1);
            String rand = codeList.substring(a, a + 1);
            sRand = sRand + rand;

            g.setColor(
                    new Color(20 + random.nextInt(110), 20 + random
                            .nextInt(110), 20 + random.nextInt(110)));
//       g.drawString(rand, 13 * i + 6, 16);
            g.drawString(rand, 13 * i + 6, 30);
        }

        this.session.setAttribute("verifycode", sRand);

        this.response.setHeader("Pragma", "no-cache");
        this.response.setHeader("Cache-Control", "no-cache");
        this.response.setDateHeader("Expires", 0L);
        this.response.setContentType("image/jpeg");

        g.dispose();
        try {
            ImageIO.write(image, "JPEG", this.response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

/* Location:           C:\Users\Administrator\Desktop\ky_pms\WEB-INF\classes\
 * Qualified Name:     ky.action.VerifyCodeAction
 * JD-Core Version:    0.6.0
 */