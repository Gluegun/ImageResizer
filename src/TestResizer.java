import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestResizer implements Runnable {

    private int targetWidth;
    private File[] files;
    private long start;
    private String dstFolder;
    private Scalr.Method method;


    public TestResizer(File[] files, String dstFolder, int targetWidth, Scalr.Method method, long start) {
        this.targetWidth = targetWidth;
        this.files = files;
        this.start = start;
        this.dstFolder = dstFolder;
        this.method = method;
    }

    @Override
    public void run() {

        try {

            for (File image : files) {

                BufferedImage img = ImageIO.read(image);

                BufferedImage thumbnail;
                int targetHeight = (int) Math.round(
                        img.getHeight() / (img.getWidth() / (double) targetWidth)
                );

                String typeOfMethod;

                typeOfMethod = method.toString();
                thumbnail = Scalr.resize(img, method, Scalr.Mode.FIT_TO_HEIGHT, targetWidth, targetHeight);
                File newFile = new File(dstFolder + "/" + typeOfMethod + "_" + targetWidth + "_" + targetHeight + "_"
                        + image.getName());
                ImageIO.write(thumbnail, "jpeg", newFile);

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
