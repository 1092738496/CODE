import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2024/5/2 18:53
 * @description:
 */

public class test {
    @Test
    public void test() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"));

        httpUtils httpUtils = new httpUtils(headers);
        String s = null;
        try {
            s = httpUtils.post("https://www.okooo.com/soccer/match/1255125/okoooexponent/xmlData/", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    @Test
    public void test1() throws IOException {
        File file = new File("D:\\Game\\iniRePather-Skyrim\\Mod Organizer 2\\mods\\Legacy of the Dragonborn - " +
                "Creation Club Patch Hub Simplified Chinese translation");
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                String aa = file1.getPath().replaceAll(file1.getName(),"")+file1.getName().replaceAll(".esp", "")+1+
                        ".esp";


                //System.out.println(file.getAbsolutePath());
                byte[] bytes = new byte[1024];
                int p = 0;
                FileInputStream inputStream = new FileInputStream(file1.getPath());
                FileOutputStream outputStream = new FileOutputStream(aa);
                while ( (p = inputStream.read(bytes)) != -1){
                    outputStream.write(bytes);
                }

                inputStream.close();
                outputStream.close();
                file1.delete();

            }
        }
    }
}
