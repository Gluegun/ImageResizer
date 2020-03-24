import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestResizer extends Thread {

    private int targetWidth;
    private File[] files;
    private long start;
    private String dstFolder;
    private static int size = 300;


    public TestResizer(File[] files, String dstFolder, int targetWidth, long start) {
        this.targetWidth = targetWidth;
        this.files = files;
        this.start = start;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {

        try {

            for (File image : files) {

                BufferedImage img = ImageIO.read(image);

                img = Scalr.resize(img, Scalr.Method.SPEED, size * 2);

                img = Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, size);

                File newFile1 = new File(dstFolder + "/" + size + image.getName());

                ImageIO.write(img, "jpeg", newFile1);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Время выполнения программы: " + (System.currentTimeMillis() - start) / 1000 + " сек");
    }

    public static void compareSizeOfResizedFile(String directory) {

        File finalDirectory = new File(directory);

        List<File> fileList = new ArrayList<>(Arrays.asList(Objects.requireNonNull(finalDirectory.listFiles())));

        fileList.sort(Comparator.comparing(File::length));

        fileList.forEach(file -> System.out.println(file.getName() + ": " + file.length() + " б"));

    }
}
