package kr.co.bikego.controller;

import com.google.gson.JsonObject;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.service.AttachService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/attach")
public class AttachController {
    private AttachService attachService;
    private AsService asService;

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

    @GetMapping(value = "/resizeImgView.do/{idAttach}/{snFileAttach}") //todo: 이미지사이즈 메소드 분리
    public @ResponseBody
    byte[] resizeImgView(@PathVariable("idAttach") String idAttach, @PathVariable("snFileAttach") int snFileAttach) throws Exception {
        try {
            AttachDto attachDto = attachService.getAttachInfo(idAttach, snFileAttach);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( attachService.getResizeImg(attachDto.getPathFileAttach(), 0)  , "JPEG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/ckImgView.do/{idAttach}/{snFileAttach}")
    public void ckImgView(@PathVariable("idAttach") String idAttach, @PathVariable("snFileAttach") int snFileAttach,
                     HttpServletResponse res) throws Exception {
        ServletOutputStream servletOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            AttachDto attachDto = attachService.getAttachInfo(idAttach, snFileAttach);
            byteArrayOutputStream = new ByteArrayOutputStream();
            servletOutputStream = res.getOutputStream();

            ImageIO.write( attachService.getResizeImg(attachDto.getPathFileAttach(), 0)  , "JPEG", byteArrayOutputStream);
            byte imgBuf[] = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(imgBuf, 0, imgBuf.length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            servletOutputStream.close();
            byteArrayOutputStream.close();
        }
    }

    @PostMapping(value="/ckImgUpload.do")
    public void ckImgUpload(HttpServletRequest req, HttpServletResponse resp,
                                      MultipartHttpServletRequest multiFile) throws Exception {
        JsonObject json = new JsonObject();
        PrintWriter printWriter = null;
        OutputStream out = null;
        MultipartFile file = multiFile.getFile("upload");

        if(file != null){
            if(file.getSize() > 0 && !StringUtils.isEmpty(file.getName())){
                if(file.getContentType().toLowerCase().startsWith("image/")){
                    try{
                        String fileName = file.getName();
                        byte[] bytes = file.getBytes();

                        BASE64Encoder base64Encoder = new BASE64Encoder();
                        String[] image = {base64Encoder.encode(file.getBytes())};
                        String[] imageName = {file.getOriginalFilename()};
                        String[] imageSize = {String.valueOf(file.getSize())};
                        List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "ckEditor");

                        json.addProperty("uploaded", 1);
                        json.addProperty("fileName", file.getOriginalFilename());
                        json.addProperty("url", "/attach/resizeImgView.do/" + attachEntities.get(0).getIdAttach() + "/" + 1); //todo: 도메인 변수처리

                        printWriter = resp.getWriter();
                        printWriter.println(json);
                        printWriter.flush();
                    }catch(IOException e){
                        e.printStackTrace();
                    }finally{
                        if(out != null){
                            out.close();
                        }
                        if(printWriter != null){
                            printWriter.close();
                        }
                    }
                }
            }
        }
    }
}
