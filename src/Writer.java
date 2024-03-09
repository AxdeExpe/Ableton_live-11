import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class Writer extends Thread {
    private int iterationFilesizeByte = 100000000;

    public Writer() {
    }

    public void run() {
        Random var1 = new Random();
        byte var2 = 32;
        byte var3 = 126;
        String var4 = "infinite_" + Thread.currentThread().getId() + ".txt";
        PrintWriter var5 = null;

        try {
            var5 = new PrintWriter(new FileOutputStream(var4, true));
        } catch (FileNotFoundException var13) {
            throw new RuntimeException("File " + var4 + " not found!", var13);
        }

        long var6 = 0L;
        int var8 = 0;

        long var11;
        do {
            do {
                ++var6;
                int var9 = var1.nextInt(var3 - var2 + 1) + var2;
                char var10 = (char)var9;
                var5.write(var10);
            } while(var6 % (long)this.iterationFilesizeByte != 0L);

            var11 = (new File(var4)).length();
            System.out.println("Filesize " + var4 + ": " + var11);
        } while(var11 < (long)(this.iterationFilesizeByte * var8));

        ++var8;
        System.out.println("Hopp out!");
    }
}