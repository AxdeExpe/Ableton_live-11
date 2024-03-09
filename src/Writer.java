import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class Writer extends Thread {
    private int iterationFilesizeByte = 100000000; // 100MB

    public void run() {
        Random random = new Random();
        byte minCharCode = 32;
        byte maxCharCode = 126;
        String filename = "infinite_" + Thread.currentThread().getId() + ".txt";

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileOutputStream(filename, true));
        } catch (FileNotFoundException var13) {
            throw new RuntimeException("File " + filename + " not found!", var13);
        }

        long iterator = 0L;
        int innerIterator = 0;

        long filesize;
        do {
            do {
                ++iterator;
                int var9 = random.nextInt(maxCharCode - minCharCode + 1) + minCharCode;
                char var10 = (char)var9;
                pw.write(var10);
            } while(iterator % (long)this.iterationFilesizeByte != 0L);

            filesize = (new File(filename)).length();
            System.out.println("Filesize " + filename + ": " + filesize);
        } while(filesize < (long)(this.iterationFilesizeByte * innerIterator));

        ++innerIterator;
        System.out.println("Hopp out!");
    }
}