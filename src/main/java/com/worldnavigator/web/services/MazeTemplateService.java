package com.worldnavigator.web.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MazeTemplateService {

    /**
     * Takes a file upload it to S3 and save it to the database.
     */
    public void save(MultipartFile mazeFile) {}
}
