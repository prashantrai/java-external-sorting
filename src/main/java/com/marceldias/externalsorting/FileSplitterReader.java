package com.marceldias.externalsorting;

import com.marceldias.externalsorting.exception.ExternalSortingException;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class FileSplitterReader implements Callable<Boolean> {
    private FileHandler fileHandler;
    private String filename;

    public FileSplitterReader(FileHandler fileHandler, String filename) {
        this.fileHandler = fileHandler;
        this.filename = filename;
    }

    @Override
    public Boolean call() {
        Path path = Paths.get(filename);
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null ) {
                fileHandler.addLineToQueue(line);
            }
        } catch (NoSuchFileException e) {
            throw new ExternalSortingException("File not Found!", e);
        } catch (Exception e) {
            throw new ExternalSortingException("Unexpected error occured!", e);
        }
        return Boolean.TRUE;
    }
}
