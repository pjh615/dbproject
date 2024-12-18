package com.example.dbproject.service;

import com.example.dbproject.exception.DataNotFoundException;
import com.example.dbproject.model.Images.Images;
import com.example.dbproject.model.Images.ImagesRepository;
import com.example.dbproject.model.Posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ImagesService {

    private final ImagesRepository iRepo;

    public void upload(Posts post, List<MultipartFile> images) {
        try{
            String uploadDir = "src/main/resources/static/uploads/";
            for(MultipartFile image : images){
                String DbFilePath = save(image, uploadDir);
                Images i = new Images();
                i.setImagePath(DbFilePath);
                i.setImageName(image.getOriginalFilename());
                i.setPost(post);
                i.setCreateDate(LocalDateTime.now());
                iRepo.save(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String save(MultipartFile image, String uploadDir) throws IOException {
        String imageName = image.getOriginalFilename();
        if(imageName == null || imageName.trim().isEmpty()){
            return "";
        }
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + imageName;
        String filePath = uploadDir + fileName;
        String DbFilePath = "/uploads/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return DbFilePath;
    }

    public void delete(Posts post){
        Images i = iRepo.findAllByPostId(post.getId());
        iRepo.delete(i);
    }
}
