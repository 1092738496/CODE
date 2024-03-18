package tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @description:
 * @author: Andy
 * @time: 2023-8-7 08:13
 */

public class Props {

    public static String getStr(String key) {
        /*String property = System.getProperty("user.dir");
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(property + "\\Settings.properties");
*/
        String path = Props.class.getClassLoader().getResource("Settings.properties").getPath();
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(path);
        return props.getStr(key);
    }

    public static void in_file(String cookie) {
        /*String Path = System.getProperty("user.dir")+ "\\Settings.properties";
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(Path);*/

        String path = Props.class.getClassLoader().getResource("Settings.properties").getPath();
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(path);

        Properties properties = props.toProperties();
        properties.setProperty("cookie", cookie);
        try {
             properties.store(new FileOutputStream(path), null); // 【存储的是汉字的unicode编码】
            //properties.store(new FileOutputStream(Path), null); // 【存储的是汉字的unicode编码】
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
