package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class FileUtilTest {

    @Test
    public void crudFileTest() throws IOException {
        //given
        File parentDirectory = new File(System.getProperty("user.dir"));
        File crudTestDirectory = FileUtil.buildFile(parentDirectory, "crud");
        File crudChildDirectory = FileUtil.buildFile(crudTestDirectory, "my", "child", "directory");
        File crudChildFile = FileUtil.buildFile(crudChildDirectory, "myFile.txt");
        File crudRenamedChildFile = FileUtil.buildFile(crudChildDirectory, "myRenamedFile.txt");

        //file existence initial verification
        Assertions.assertTrue(FileUtil.exists(parentDirectory));
        Assertions.assertFalse(FileUtil.exists(crudTestDirectory));
        Assertions.assertFalse(FileUtil.exists(crudChildDirectory));
        Assertions.assertFalse(FileUtil.exists(crudChildFile));

        //file creation
        FileUtil.createDirectory(crudTestDirectory);
        Assertions.assertTrue(FileUtil.exists(crudTestDirectory));

        FileUtil.createFile(crudChildFile);
        Assertions.assertTrue(FileUtil.exists(crudChildDirectory));
        Assertions.assertTrue(FileUtil.exists(crudChildFile));
        Assertions.assertFalse(FileUtil.exists(crudRenamedChildFile));

        //file renaming
        boolean renamingSucceeded = FileUtil.renameFile(crudChildFile, "myRenamedFile.txt");
        Assertions.assertTrue(renamingSucceeded);
        Assertions.assertFalse(FileUtil.exists(crudChildFile));
        Assertions.assertTrue(FileUtil.exists(crudRenamedChildFile));

        //file deletion
        Assertions.assertThrows(IOException.class, () -> FileUtil.deleteFile(crudTestDirectory));

        FileUtil.deleteDirectory(crudTestDirectory);
        Assertions.assertFalse(FileUtil.exists(crudTestDirectory));
        Assertions.assertFalse(FileUtil.exists(crudChildDirectory));
        Assertions.assertFalse(FileUtil.exists(crudChildFile));
        Assertions.assertFalse(FileUtil.exists(crudRenamedChildFile));
    }

    @ParameterizedTest
    @MethodSource("buildFileTestSource")
    public void buildFileTest(String expectedPath, String[] actualPathElements) {
        Assertions.assertEquals(expectedPath, FileUtil.buildFile(actualPathElements).getPath());
    }

    private static Stream<Arguments> buildFileTestSource() {
        return Stream.of(
                Arguments.of("root" + File.separator + "temp", new String[] {"root", "temp"}),
                Arguments.of("root" + File.separator + "temp" + File.separator + "myFile.txt", new String[] {"root", "temp", "myFile.txt"})
        );
    }

    @Test
    public void buildFileExceptionsTest() {
        Assertions.assertThrows(NullPointerException.class, () -> FileUtil.buildFile(null));
        Assertions.assertThrows(NullPointerException.class, () -> FileUtil.buildFile((File) null, "temp"));
    }

    @ParameterizedTest
    @MethodSource("parentDirectoryTestSource")
    public void parentDirectoryTest(File expectedParentDirectory, File file) {
        if (expectedParentDirectory == null) {
            Assertions.assertNull(FileUtil.getParentDirectory(file));
        } else {
            Assertions.assertEquals(expectedParentDirectory.toPath().normalize(), FileUtil.getParentDirectory(file).toPath().normalize());
        }
    }

    private static Stream<Arguments> parentDirectoryTestSource() {
        return Stream.of(
                Arguments.of(new File("root" + File.separator + "temp"), FileUtil.buildFile("root", "temp", "myFile.txt")),
                Arguments.of(new File("root" + File.separator + "temp"), FileUtil.buildFile("root", "temp", "childDir")),
                Arguments.of(new File("root"), FileUtil.buildFile("root", "temp"))
        );
    }

    @Test
    public void fileSizeTest() {
        File file = new File("C:\\Users\\dariu\\Desktop\\exejar");
        System.out.println(FileUtil.getFileSize(file, FileSizeUnit.KILOBYTE));
    }

}
