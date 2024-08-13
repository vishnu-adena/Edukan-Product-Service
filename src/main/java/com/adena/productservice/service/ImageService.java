//package com.adena.productservice.service;
//
//import com.adena.productservice.models.Image;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ImageService {
//
//    @Autowired
//    private Cloudinary cloudinary;
//
//    public Image uploadImage(MultipartFile file) throws IOException {
//        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//
//        String url = uploadResult.get("url").toString();
//        String publicId = uploadResult.get("public_id").toString();
//
//        Image image = new Image();
//        image.setUrl(url);
//        image.setPublicId(publicId);
//
//        return image;
//    }
//
//    public List<Image> uploadImages(MultipartFile[] files) throws IOException {
//        List<Image> images = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            images.add(uploadImage(file));
//        }
//
//        return images;
//    }
//}
