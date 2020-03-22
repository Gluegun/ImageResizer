import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.imgscalr.*;

public class Main {


    static int amountToBeSplit = Runtime.getRuntime().availableProcessors();
    static int newWidth = 300;
    static String srcFolder = "/users/sunpu/Desktop/src";
    static String dstFolder = "/users/sunpu/Desktop/dst";
    static Scalr.Method method = Scalr.Method.QUALITY;

    public static void main(String[] args) {

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();

        int initialLength = files.length;

        List<Integer> arrayOfFileLengths = createListWithLengths(initialLength, amountToBeSplit);

        List<File[]> filesList = createListOfFileArrays(arrayOfFileLengths, files);

        List<TestResizer> testResizers = createTestResizerList(filesList);

        testResizers.forEach(Thread::start);

//        List<ImageResizer> imageResizers = createImageResizerList(filesList);
//        imageResizers.forEach(imageResizer -> new Thread(imageResizer).start());

    }

    public static List<Integer> createListWithLengths(int initialLength, int amountToBeSplit) {

        ArrayList<Integer> lengths = new ArrayList<>();
        int sum = 0;

        for (int i = 0; i < amountToBeSplit; i++) {
            int divide = initialLength / amountToBeSplit;
            lengths.add(divide);
            sum += divide;
        }
        lengths.set(lengths.size() - 1, initialLength - sum + (lengths.get(lengths.size() - 1)));

        return lengths;
    }

    public static List<File[]> createListOfFileArrays(List<Integer> arrayOfFileLengths, File[] files) {
        List<File[]> filesList = new ArrayList<>();

        for (Integer arrayOfFileLength : arrayOfFileLengths) {
            filesList.add(new File[arrayOfFileLength]);
        }

        int numberToBeAdded = 0;
        for (File[] value : filesList) {

            System.arraycopy(files, numberToBeAdded, value, 0, value.length);
            numberToBeAdded += value.length;
        }

        return filesList;

    }

    public static List<ImageResizer> createImageResizerList(List<File[]> files) {
        List<ImageResizer> imageResizers = new ArrayList<>();

        for (File[] file : files) {
            imageResizers.add(new ImageResizer(file, dstFolder, newWidth, System.currentTimeMillis()));
        }

        return imageResizers;
    }

    public static List<TestResizer> createTestResizerList(List<File[]> files) {
        List<TestResizer> testResizers = new ArrayList<>();

        for (File[] file : files) {
            testResizers.add(new TestResizer(file, dstFolder, newWidth, method, System.currentTimeMillis()));
        }

        return testResizers;
    }

}
