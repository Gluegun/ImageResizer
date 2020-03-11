import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static {
        threads = new Thread[Runtime.getRuntime().availableProcessors()];
    }

    static Thread[] threads;
    static int amountToBeSplitted = threads.length;
    static int newWidth = 300;
    static String srcFolder = "/users/kvy/Desktop/src";
    static String dstFolder = "/users/kvy/Desktop/dst";

    public static void main(String[] args) {


        try {
            if (!Files.exists(Paths.get(srcFolder))) {
                Files.createDirectory(Paths.get(srcFolder));
            }
            if (!Files.exists(Paths.get(dstFolder))) {
                Files.createDirectory(Paths.get(dstFolder));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        File srcDir = new File(srcFolder);

        File[] files = srcDir.listFiles();

        int initialLength = files.length;

        List<Integer> arrayOfFileLengths = createListWithLengths(initialLength, amountToBeSplitted);

        List<File[]> filesList = createListOfFileArrays(arrayOfFileLengths, files);

        List<ImageResizer> imageResizers = createImageResizerList(filesList);

        imageResizers.forEach(imageResizer -> new Thread(imageResizer).start());

    }

    public static List<Integer> createListWithLengths(int initialLength, int amountToBeSplitted) {

        ArrayList<Integer> lengths = new ArrayList<>();
        int sum = 0;

        for (int i = 0; i < amountToBeSplitted; i++) {
            int divide = initialLength / amountToBeSplitted;
            lengths.add(divide);
            sum += divide;
        }
        lengths.set(lengths.size() - 1, initialLength - sum + (lengths.get(lengths.size() - 1)));

        return lengths;
    }

    public static List<File[]> createListOfFileArrays (List<Integer> arrayOfFileLengths, File[] files) {
        List<File[]> filesList = new ArrayList<>();

        for (int i = 0; i < arrayOfFileLengths.size(); i++) {
            filesList.add(new File[arrayOfFileLengths.get(i)]);
        }

        int numberToBeAdded = 0;
        for (int i = 0; i < filesList.size(); i++) {

            System.arraycopy(files, numberToBeAdded, filesList.get(i), 0, filesList.get(i).length);
            numberToBeAdded += filesList.get(i).length;
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

}
