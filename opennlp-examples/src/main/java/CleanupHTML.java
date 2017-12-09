import org.jsoup.Jsoup;

import java.io.*;

public class CleanupHTML {

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


    public static String readAll(String filename) throws Exception {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data);
    }

    public static void writeAll(String filename, String text) throws Exception {
        PrintWriter out = new PrintWriter( filename );
            out.println( text );

    }
    public static void main(String [] args) throws Exception {


        String page0 = readAll("/tmp/pages/page0");
        //String page1 = readAll("/tmp/pages/page1");

        writeAll("/home/purbon/work/careers/careers/indexer/scripts/locations-indexer/page0.txt", html2text(page0));
        //writeAll("/home/purbon/work/careers/careers/indexer/scripts/locations-indexer/page1.txt", html2text(page1));
    }

}
