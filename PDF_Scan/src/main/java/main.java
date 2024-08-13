import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import utils.tool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * @description:
 * @author: Andy
 * @time: 2023-8-26 14:46
 */

public class main {
    public static void main(String[] args) {


        Scan scan = new Scan();
        String property = System.getProperty("user.dir");
        LinkedList<LinkedList<String>> lists = new LinkedList<LinkedList<String>>();
        File[] files = scan.PDFPath(property+"//PDF");
        for (File file : files) {
            System.out.println(file.getPath());
            lists.addAll(scan.getlist(file));
        }



        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm");
        String dateTimeString = sdf.format(date);
        ExcelWriter writer = ExcelUtil.getWriter(tool.getstr("EcxelPath") + lists.size()+"条-"+dateTimeString + ".xlsx");
        writer.write(lists);
        writer.close();

    }
}
