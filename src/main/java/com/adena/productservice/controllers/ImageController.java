//package com.adena.productservice.controllers;
//
//import com.adena.productservice.models.Image;
//import com.adena.productservice.service.ImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/file")
//public class ImageController {
//
//    @Autowired
//    private ImageService imageService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            Image image = imageService.uploadImage(file);
//            return ResponseEntity.ok(image);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @PostMapping("/uploads")
//    public ResponseEntity<List<Image>> uploadImages(@RequestParam("files") MultipartFile[] files) {
//        try {
//            List<Image> images = imageService.uploadImages(files);
//            return ResponseEntity.ok(images);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//}
