package com.gutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class containing utility methods for file system.
 * @author Dariusz Gren
 * @version 1.0
 */
public class FileUtil {

    /**
     * Checks if there is an actual file in file system with given path.
     * @param filePath path of the file
     * @return true if file exists, false otherwise
     */
    public static boolean exists(String filePath) {
        return filePath != null && exists(new File(filePath));
    }

    /**
     * Checks if there is an actual file in file system with given path.
     * @param file file representing the actual file in file system
     * @return true if file exists, false otherwise
     */
    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    /**
     * Creates given file in file system. If parent directories do not exist, they will be created as well. Method
     * will not correctly create a directory. For that {@link #createDirectory(File)} method should be used.
     * @param file file to be created
     * @throws IOException if file already exists
     */
    public static void createFile(File file) throws IOException {
        if (file.exists()) {
            throw new IOException("Cannot create a file - already exists.");
        }

        File directory = getParentDirectory(file);
        if (!directory.exists()) {
            createDirectory(directory);
        }

        file.createNewFile();
    }

    /**
     * Creates given directory in file system. If parent directories do not exist, they will be created as well.
     * @param directory directory to be created
     * @throws IOException if directory already exists
     */
    public static void createDirectory(File directory) throws IOException {
        if (directory.exists()) {
            throw new IOException("Cannot create a directory - already exists.");
        }

        directory.mkdirs();
    }

    /**
     * Deletes given file from the file system.
     * @param file file to be deleted
     * @throws IOException if file does not exist or is not a file
     */
    public static void deleteFile(File file) throws IOException {
        if (!file.isFile()) {
            throw new IOException("Cannot delete " + file + " - not a file.");
        }

        Files.delete(file.toPath());
    }

    /**
     * Deletes given directory (and all nested directories and files inside) from the file system.
     * @param directory directory to be deleted
     * @throws IOException if file does not exist or is not a directory
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.isDirectory()) {
            throw new IOException("Cannot delete " + directory + " - not a directory.");
        }

        File[] subFiles = directory.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    deleteDirectory(subFile);
                    continue;
                }

                deleteFile(subFile);
            }
        }

        Files.delete(directory.toPath());
    }

    /**
     * Creates instance of the File class based on given parameters (directories and file names). The actual file will
     * not be created in the file system.
     * @param files elements of file path (directories and/or file names)
     * @throws NullPointerException if parameter is null
     * @return instance of File class (cannot be null)
     */
    public static File buildFile(String... files) {
        return new File(String.join(File.separator, files));
    }

    /**
     * Creates instance of the File class based on given parameters (directories and file names). The actual file will
     * not be created in the file system.
     * @param parentDirectory the parent directory
     * @param files elements of file path (directories and/or file names)
     * @throws NullPointerException if any of parameters is null
     * @return instance of File class (cannot be null)
     */
    public static File buildFile(File parentDirectory, String... files) {
        if (parentDirectory == null) {
            throw new NullPointerException("Parent directory cannot be null.");
        }

        return new File(parentDirectory, String.join(File.separator, files));
    }

    /**
     * Returns parent directory of given file (or given subdirectory). File do not need to exist.
     * @param file File instance representing file or directory
     * @return File instance representing the parent directory
     */
    public static File getParentDirectory(File file) {
        if (file.toPath().getNameCount() == 0) {
            return null;
        }

        return file.toPath().getParent().toFile();
    }

}
