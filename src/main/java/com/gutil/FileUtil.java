package com.gutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
     * Creates given file in file system (if parent directories do not exist, they will be created as well). See
     * also {@link #createDirectory(File)} method dedicated for creation of directories.
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
     * @throws IOException if {@code File} does not exist or is not a directory
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
     * Renames given file. Cannot be used to change file's directory. For that {@link java.io.File#renameTo(File)}
     * method should be used instead.
     * @param file {@code File} instance to be renamed
     * @param newName new name of the file (cannot be blank); notice that changing file format can damage the file
     * @return {@code true} if and only if the renaming succeeded; {@code false} otherwise
     */
    public static boolean renameFile(File file, String newName) {
        newName = newName.strip();
        if (newName.isBlank()) {
            throw new IllegalArgumentException("Name of renamed file cannot be blank.");
        }

        if (newName.contains(File.separator)) {
            throw new IllegalArgumentException("Name of renamed file cannot contains file separators.");
        }

        Path path = file.toPath().getParent().resolve(newName);
        return file.renameTo(path.toFile());
    }

    /**
     * Creates instance of the {@code File} class based on given parameters (directories names and file names).
     * The actual file will not be created in the file system.
     * @param files elements of file path (directories and/or file names)
     * @throws NullPointerException if parameter is null
     * @return instance of {@code File} class (cannot be null)
     */
    public static File buildFile(String... files) {
        return new File(String.join(File.separator, files));
    }

    /**
     * Creates instance of the {@code File} class based on given parameters (directories and file names). The actual file will
     * not be created in the file system.
     * @param parentDirectory the parent directory
     * @param files elements of file path (directories and/or file names)
     * @throws NullPointerException if any of parameters is null
     * @return instance of {@code File} class (cannot be null)
     */
    public static File buildFile(File parentDirectory, String... files) {
        if (parentDirectory == null) {
            throw new NullPointerException("Parent directory cannot be null.");
        }

        return new File(parentDirectory, String.join(File.separator, files));
    }

    /**
     * Returns parent directory of given {@code File} (or given subdirectory). File does not need to exist in
     * file system.
     * @param file {@code File} instance representing file or directory
     * @return {@code File} instance representing the parent directory (can be null if there is no parent directory)
     */
    public static File getParentDirectory(File file) {
        if (file.toPath().getNameCount() == 0) {
            return null;
        }

        return file.toPath().getParent().toFile();
    }

    /**
     * Returns size of the file using given unit from {@link com.gutil.FileSizeUnit} enum. If {@code File} represents
     * a directory, summarize size of subdirectories and files inside will be returned.
     * @param file {@code File} instance representing a file or a directory in the file system
     * @param unit unit of file size (e.g. bytes, kilobytes...)
     * @return size of the file based on given unit
     */
    public static double getFileSize(File file, FileSizeUnit unit) {
        long sizeInBytes = 0;
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                sizeInBytes += getFileSizeRoundUp(subFile, FileSizeUnit.BYTE);
            }
        } else if (file.isFile()) {
            sizeInBytes = file.length();
        }

        return FileSizeUnit.convert(sizeInBytes, FileSizeUnit.BYTE, unit);
    }

    /**
     * Returns size of the file using given unit from {@link com.gutil.FileSizeUnit} enum. Size will be rounded up to
     * the closest integer. If {@code File} represents a directory, summarize size of subdirectories and files inside
     * will be returned.
     * @param file {@code File} instance representing a file or a directory in the file system
     * @param unit unit of file size (e.g. bytes, kilobytes...)
     * @return size of the file based on given unit (rounded up)
     */
    public static long getFileSizeRoundUp(File file, FileSizeUnit unit) {
        return (long) Math.ceil(getFileSize(file, unit));
    }

}
