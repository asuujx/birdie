package com.birdie.backend.services;

import com.birdie.backend.config.StorageConfig;
import com.birdie.backend.handlers.StorageException;
import com.birdie.backend.models.*;
import com.birdie.backend.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final SolutionRepository solutionRepository;
    private final CourseMemberRepository courseMemberRepository;
    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileSystemStorageService(StorageConfig properties, SolutionRepository solutionRepository, CourseMemberRepository courseMemberRepository, TaskRepository taskRepository, FileRepository fileRepository) {

        if(properties.getLocation().trim().isEmpty()){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
        this.solutionRepository = solutionRepository;
        this.courseMemberRepository = courseMemberRepository;
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Map<String, Object> store(MultipartFile[] files, int userId, int taskId) {
        if (files.length == 0) {
            throw new StorageException("Failed to store empty file.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new StorageException("Task not found."));
        CourseMember courseMember = courseMemberRepository.findByTaskAndUser(taskId, userId)
                .orElseThrow(() -> new StorageException("Course member not found."));
        Date date = new Date();

        Solution solution = Solution
                .builder()
                .courseMember(courseMember)
                .task(task)
                .dateAdded(date)
                .build();

        Solution dbSolution = solutionRepository.save(solution);
        solutionRepository.flush();

        List<File> savedFiles = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String randomFilename = UUID.randomUUID() + "-" + originalFilename;
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                File dbFile = new File();
                dbFile.setUrl("http://localhost:8080/" + randomFilename);
                dbFile.setOriginal_name(originalFilename);
                dbFile.setDate_added(date);
                dbFile.setSolution(dbSolution);

                fileRepository.save(dbFile);
                savedFiles.add(fileRepository.save(dbFile));
            } catch (IOException e) {
                throw new StorageException("Failed to store file.", e);
            }
        });

        // Create a map to return both Task and Solution objects
        Map<String, Object> response = new HashMap<>();
        response.put("solution", dbSolution);
        response.put("files", savedFiles);

        return response;
    }

    @Override
    public Stream<Path> loadAll() {
        try (Stream<Path> stream = Files.walk(this.rootLocation, 1)) {
            return stream
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize)
                    .toList()
                    .stream();
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}
