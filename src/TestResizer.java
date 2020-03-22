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
    private Scalr.Method method;
    private static int size = 300;


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

                int targetHeight = (int) Math.round(
                        img.getHeight() / (img.getWidth() / (double) targetWidth)
                );

//                String typeOfMethod = method.toString();
//                BufferedImage thumbnail = Scalr.resize(img, method, Scalr.Mode.FIT_TO_HEIGHT, targetWidth, targetHeight);
//
//
//                File newFile = new File(dstFolder + "/" + typeOfMethod + "_" + targetWidth + "_" + targetHeight + "_"
//                        + image.getName());
//                ImageIO.write(thumbnail, "jpeg", newFile);


                Scalr.Method firstMethod = Scalr.Method.SPEED;
                Scalr.Method secondMethod = Scalr.Method.ULTRA_QUALITY;

                BufferedImage firstStep;
                BufferedImage secondStep;

//                firstStep = Scalr.resize(img, firstMethod, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);
                firstStep = Scalr.resize(img, firstMethod, size * 2);
                secondStep = Scalr.resize(firstStep, secondMethod, size);

                File newFile1 = new File(dstFolder + "/" + size + image.getName());

                ImageIO.write(secondStep, "jpeg", newFile1);


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
