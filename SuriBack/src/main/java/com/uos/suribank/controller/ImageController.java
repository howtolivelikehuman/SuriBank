package com.uos.suribank.controller;

import java.io.InputStream;

import com.uos.suribank.exception.InsertErrorException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {
     //이미지 return
     @GetMapping(value = "/{foldername}/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
     public ResponseEntity<?> getImage(@PathVariable(name="foldername") String foldername, @PathVariable(name="filename") String filename) {
         String path = "/images/"+foldername+"/"+filename;
 
         try{
             ClassPathResource resource = new ClassPathResource(path);
             InputStream in = resource.getInputStream();
             return ResponseEntity.ok(IOUtils.toByteArray(in));
             
         }catch(Exception e){
             e.printStackTrace();
             throw new InsertErrorException("Failed to daaaaaGet Image");
         }
     }
}
