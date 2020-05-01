package kr.co.bikego.controller;

import com.google.gson.JsonObject;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.service.AttachService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/attach")
public class AttachController {
    private AttachService attachService;

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

    @GetMapping(value = "/resizeImgView.do/{idAttach}/{snFileAttach}")
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

    @PostMapping(value="/ckImgUpload.do"
            , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] ckImgUpload(HttpServletRequest req, HttpServletResponse resp,
                                      MultipartHttpServletRequest multiFile) throws Exception {
        JsonObject json = new JsonObject();
        PrintWriter printWriter = null;
        OutputStream out = null;
        System.out.println("upload ::: " + req.getParameter("upload"));
        MultipartFile file = multiFile.getFile("upload");
        System.out.println("file ::: " + file);

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
                        for(AttachEntity test : attachEntities) {
                            System.out.println(test.getAttachId());
                        }

                        // json 데이터로 등록
                        // {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
                        // 이런 형태로 리턴이 나가야함.
                        json.addProperty("uploaded", 1);
                        json.addProperty("fileName", file.getOriginalFilename());
                        json.addProperty("url", "http://localhost:8080/attach/resizeImgView.do/" + attachEntities.get(0).getAttachId() + "/" + 1); //todo: 도메인 변수처리

                        System.out.println("json :: " + json);
                        printWriter = new PrintWriter(file.getOriginalFilename());
                        printWriter.println(json);
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
        return null;
    }


}
