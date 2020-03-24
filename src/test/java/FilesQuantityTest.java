import junit.framework.TestCase;

import java.io.File;

public class FilesQuantityTest extends TestCase {

    File[] srcFiles;
    File[] dstFiles;

    @Override
    protected void setUp() throws Exception {

        String srcDirPath = "C:/users/sunpu/desktop/src";
        String dstDirPath = "C:/users/sunpu/desktop/dst";
        File scrDir = new File(srcDirPath);
        File dstDir = new File(dstDirPath);
        srcFiles = scrDir.listFiles();
        dstFiles = dstDir.listFiles();


    }

    public void testNotNullAmount() {

        assertNotNull(srcFiles);
        assertNotNull(dstFiles);

    }

    public void testAmount() {

        int expected = dstFiles.length;
        int actual = srcFiles.length;

        assertEquals(expected, actual);

    }

    @Override
    protected void tearDown() throws Exception {

        dstFiles = null;
        srcFiles = null;

    }
}
