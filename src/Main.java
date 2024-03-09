import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Thread {
    public Main() {
    }

    public static void main(String[] var0) {
        int var1 = Runtime.getRuntime().availableProcessors() - 1;
        Writer[] var2 = new Writer[var1];

        while(true) {
            int var3;
            for(var3 = 0; var3 < var1; ++var3) {
                var2[var3] = new Writer();
                var2[var3].start();
            }

            for(var3 = 0; var3 < var1; ++var3) {
                try {
                    var2[var3].join();
                } catch (InterruptedException var6) {
                    throw new RuntimeException(var6);
                }
            }

            String var7 = "infinite_merge.txt";
            String[] var4 = new String[var1];

            for(int var5 = 0; var5 < var1; ++var5) {
                var4[var5] = "infinite_" + var2[var5].getId() + ".txt";
            }

            System.out.println("Merging...");
            mergeFiles(var4, var7);
        }
    }

    private static void mergeFiles(String[] var0, String var1) {
        try {
            FileWriter var2 = new FileWriter(var1, true);

            try {
                BufferedWriter var3 = new BufferedWriter(var2);

                try {
                    String[] var4 = var0;
                    int var5 = var0.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        String var7 = var4[var6];

                        try {
                            BufferedReader var8 = new BufferedReader(new FileReader(var7));

                            String var9;
                            try {
                                while((var9 = var8.readLine()) != null) {
                                    var3.write(var9);
                                    var3.newLine();
                                }
                            } catch (Throwable var14) {
                                try {
                                    var8.close();
                                } catch (Throwable var13) {
                                    var14.addSuppressed(var13);
                                }

                                throw var14;
                            }

                            var8.close();
                        } catch (IOException var15) {
                            var15.printStackTrace();
                        }
                    }
                } catch (Throwable var16) {
                    try {
                        var3.close();
                    } catch (Throwable var12) {
                        var16.addSuppressed(var12);
                    }

                    throw var16;
                }

                var3.close();
            } catch (Throwable var17) {
                try {
                    var2.close();
                } catch (Throwable var11) {
                    var17.addSuppressed(var11);
                }

                throw var17;
            }

            var2.close();
        } catch (IOException var18) {
            var18.printStackTrace();
        }

        String[] var19 = var0;
        int var20 = var0.length;

        for(int var21 = 0; var21 < var20; ++var21) {
            String var22 = var19[var21];
            File var23 = new File(var22);
            if (var23.exists()) {
                if (var23.delete()) {
                    System.out.println("File deleted: " + var22);
                } else {
                    System.out.println("Error while deleting file: " + var22);
                }
            }
        }

    }
}
