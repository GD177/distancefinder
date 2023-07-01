package com.geographical.distancefinder.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * Util Class for file reader
 */
@UtilityClass
public final class FileReader {

    /**
     * Reads file from S3 or from different storage
     * Also an option that can be used if we don't want to use local file
     * @param filename'
     * @param location'
     * @return File
     */
    public static File readFileFromS3(String filename, String location) {
        //TODO: Implement
        return null;
    }

    /**
     * Reads file from local storage
     * @param filename'
     * @param location'
     * @return File
     * @throws IOException'
     */
    public static File readFileFromLocal(String filename, String location) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filename);
        if (!file.exists())
            throw new IOException("File {} not found" + filename);

        return file;
    }
}