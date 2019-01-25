package net.yxy.chukonu.spring.security.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.yxy.chukonu.spring.security.entity.ProductEntity;
import net.yxy.chukonu.spring.security.global.Role;
import net.yxy.chukonu.spring.security.mybatis.mapper.ProductMapper;
import net.yxy.chukonu.spring.security.util.JSONUtil;

@Slf4j
@Controller
@RequestMapping("/api/v1/attachment")
public class FileController {
    

    @Autowired
    ProductMapper prodMapper;

    @Secured(Role.ADMIN)
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile multipart, HttpServletRequest request) throws Exception {
        String extension = FilenameUtils.getExtension(multipart.getOriginalFilename());
        if(extension.toLowerCase().equals("json")) {
            readJson(multipart, request);
        } else {
            return "Incorrect file type : " + extension ; 
        }

        return "OK";
    }
    
    private void readJson(MultipartFile multipart, HttpServletRequest request) throws Exception {
        InputStream inputStream = new BufferedInputStream(multipart.getInputStream());
        InputStreamReader is = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(is);
        String str = "";

        while ((str = br.readLine()) != null) {
            ObjectMapper mapper = new ObjectMapper() ;
            ProductEntity product = mapper.readValue(str, ProductEntity.class) ;
            product.setUpdateBy(getCurrentUserName(request));
            product.setUpdateTime(new Date());
            
            try {
                prodMapper.insert(product);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    
    @ResponseBody
    @GetMapping(value = "/download")
    @Secured({ Role.ADMIN, Role.EDITOR })
    public String exportJson(HttpServletResponse response) throws Exception {
        String fileName = "product.json";
        List<ProductEntity> rows = prodMapper.getAll();
        buildJsonDocument(fileName, rows, response);
        
        return "OK";
    }

    
    protected void buildJsonDocument(String filename, List<ProductEntity> records, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        for(ProductEntity record : records) {
            String json = JSONUtil.toJSONString(record);
            json += "\n" ;
            tmp.write(json.getBytes());
        }
        outputStream.write(tmp.toByteArray());
        tmp.close();
        outputStream.flush();
        outputStream.close();
    }
    
    private String getCurrentUserName(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        
        return principal.getName() ;
    }

}
