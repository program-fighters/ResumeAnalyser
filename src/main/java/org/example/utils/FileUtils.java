package org.example.utils;


import org.example.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class FileUtils {
    private static final String[] ALLOWED_RESUME_FILE_EXT = new String[]{"pdf", "doc", "docx"};
    private static final Predicate<String> isValidExtension = input ->
            Arrays.stream(ALLOWED_RESUME_FILE_EXT).anyMatch(allowedExt ->
                    allowedExt.equalsIgnoreCase(input));
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static final int BUFFER_SIZE = 4096;
    private static final byte[] WRITE_BUFFER = new byte[BUFFER_SIZE];

    private FileUtils() {
        throw new UnsupportedOperationException("Support only static methods.");
    }


    public static String getMultipartOriginalFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(File.separator)) {
            String[] split = originalFilename.split(Pattern.quote(File.separator));
            originalFilename = split[split.length - 1];
        }
        return originalFilename;
    }

    public static String findFileExtensionFromMultipart(MultipartFile file) {
        if (NullSafeUtils.ifAmNotNull(file)) {
            return findFileExtensionFromFileName(file.getOriginalFilename());
        }
        return null;
    }

    public static String findFileExtensionFromFileName(String fileName) {
        log.debug("FileUtils::findFileExtensionFromMultipart() with fileName::{}", fileName);
        if (NullSafeUtils.ifAmNotNull(fileName) && fileName.contains(".")) {
            if (fileName.endsWith((".tar.gz"))) {
                return "tar.gz";
            }
            int lastDotIndex = fileName.lastIndexOf(".");
            log.warn("lastDotIndex is::{}", lastDotIndex);
            //cut all string after last dot
            String finalExt = fileName.substring(lastDotIndex + 1);
            log.warn("file ext is::{}", finalExt);
            return finalExt;
        }
        return null;
    }

    public static boolean isFileValidPdfOrDoc(MultipartFile file) {
        log.debug("FileUtils::isFileValidPdfOrDoc() with file::{}", file.getName());
        return isValidExtension.test(findFileExtensionFromMultipart(file));
    }

    /**
     * This method converts a MultipartFile type
     * into File type
     *
     * @param file MultipartFile Object
     * @return File type
     */
    public static String readTextFromPdfOrDocMultipartRequest(MultipartFile file) {
        try {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("IOException occurred while read content from multipart", e);
            return "";
        }
    }


    public static boolean createNewFile(File file) throws IOException {
        if (file != null) {
            //create new file
            if (!file.exists()) {
                boolean res = file.createNewFile();
                log.debug("file create result::{}", res);
            } else {
                log.debug("file already exits name::{}.", file.getName());
            }
        }
        return false;
    }


    public static void createFolder(String propFolder) {
        log.info("Going to check folder with full path  ::{}", new File(propFolder).getAbsoluteFile());
        Path folder = Paths.get(propFolder);
        if (!Files.isDirectory(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (Exception e) {
                throw new AppException(String.format("%s should be accessible.", propFolder));
            }
        }
    }


}
