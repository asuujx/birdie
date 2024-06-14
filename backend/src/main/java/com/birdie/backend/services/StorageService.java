package com.birdie.backend.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    Map<String, Object> store(MultipartFile[] files, int userId, int taskId);

    Stream<Path> loadAll();

    Path load(String filename);

    //Resource loadAsResource(String filename);

    void deleteAll();
}
