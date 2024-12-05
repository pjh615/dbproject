package com.example.dbproject.service;

import com.example.dbproject.domain.Images.Images;
import com.example.dbproject.domain.Images.ImagesRepository;
import com.example.dbproject.domain.Posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
                i.setPost(post);
                i.setCreateDate(LocalDateTime.now());
                iRepo.save(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String save(MultipartFile image, String uploadDir) throws IOException {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
        String filePath = uploadDir + fileName;
        String DbFilePath = "/uploads/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return DbFilePath;
    }
}
