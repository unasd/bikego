package kr.co.bikego.controller;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    @PostMapping(value="/imgUpload.do"
                , produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] ckImg(HttpServletRequest req, HttpServletResponse resp,
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
                        String uploadPath = req.getServletContext().getRealPath("C:\\Users\\jsh\\IdeaProjects\\bikego\\upload");
                        File uploadFile = new File(uploadPath);
                        if(!uploadFile.exists()){
                            uploadFile.mkdirs();
                        }
                        fileName = UUID.randomUUID().toString();
                        uploadPath = uploadPath + "/" + fileName;
                        System.out.println("uploadPath ::: " + uploadPath);
                        out = new FileOutputStream(new File(uploadPath));
                        out.write(bytes);

                        printWriter = resp.getWriter();
                        resp.setContentType("text/html");
                        String fileUrl = req.getContextPath() + "/img/" + fileName;

                        // json 데이터로 등록
                        // {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
                        // 이런 형태로 리턴이 나가야함.
                        json.addProperty("uploaded", 1);
                        json.addProperty("fileName", fileName);
                        json.addProperty("url", fileUrl);

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
