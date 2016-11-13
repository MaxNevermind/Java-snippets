package org.maxkons.java_snippets.test;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileSystemHelper {

    @Test
    public void testFileStructure() {
        File tempDir            = Files.createTempDir();

        String[] inFileStructure = new String[] {
                tempDir           + "/1/dataFile1.parquet",
                tempDir           + "/2/3/dataFile2.parquet"
        };
        String[] outFileStructure = new String[] {
                tempDir           + "/2/dataFile2.parquet",
                tempDir           + "/2/3/dataFile3.parquet"
        };

        FileSystemHelper.createFileWithParents(inFileStructure);
        FileSystemHelper.ensureFileStructure(tempDir.toString(), outFileStructure);
    }

    /**
     * Make sure passed files(and no other) are presented in specified directory.
     * @param parentDir directory to check.
     * @param files files that should be presented in parentDir.
     */
    public static void ensureFileStructure(String parentDir, String[] files) {
        try {
            for (int i = 0; i < files.length; i++) {
                files[i] = Paths.get(files[i]).normalize().toString();
                assertTrue("File " + files[i] + " should exist.", new File(files[i]).exists());
            }
            java.nio.file.Files
                    .walk(Paths.get(parentDir))
                    .filter(currPath -> {
                        for (String file : files)
                            if (file.contains(currPath.toString()))
                                return false;
                        return true;
                    })
                    .forEach(path -> fail("File " + path.toString() + " should not exist."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFileWithParents(String[] files) {
        try {
            for (String filePath: files) {
                File file = new File(filePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
