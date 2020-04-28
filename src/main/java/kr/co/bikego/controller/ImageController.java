package kr.co.bikego.controller;

import kr.co.bikego.service.ImageService;
import kr.co.bikego.test.service.CrudService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@AllArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;

    @GetMapping(
            value = "/imgView.do"
    )
    public void imgView(HttpServletResponse response) throws IOException {
        String orgFileNm = "C:\\Users\\user\\Pictures\\IMG_4199.jpg";
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition","filename=\"" + orgFileNm + "\"");

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(new File(orgFileNm)));
            out = new BufferedOutputStream(response.getOutputStream());

            FileCopyUtils.copy(in, out);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(in!=null){try{in.close();}catch(Exception e){}}
            if(out!=null){try{out.close();}catch(Exception e){}}
        }
    }

    @GetMapping(value = "/resizeImgView.do", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] resizeImgView() throws Exception {
        try {
            String orgFileNm = "C:\\Users\\user\\Pictures\\우체국마스크.PNG";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( imageService.getResizeImg(orgFileNm, 0)  , "JPEG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
