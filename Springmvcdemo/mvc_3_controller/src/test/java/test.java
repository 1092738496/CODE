import org.junit.Test;

import java.io.File;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/18 23:22
 */

public class test {
    public static void main(String[] args) {

    }

    @Test
    public void test() {
        File file = new File("F:\\Download\\女士");
        File[] nvshi1 = file.listFiles();
        for (File nvshi : nvshi1) {
          /*  if (!nvshi.isDirectory()) {
                String[] split = nvshi.getName().split("-");
                String[] split1 = split[1].split("\\W");
                String s = split1[0];
                System.out.println(nvshi.renameTo(new File(nvshi.getParent()+"\\"+s+".jpg")));
            }*/
            String[] split = nvshi.getName().split("-");
            String[] split1 = split[1].split("\\W");
            System.out.println("sada阿斯顿dsf sd撒旦sd");
            System.out.println(nvshi.renameTo(new File(nvshi.getParent() + "\\" + split1[0] + ".jpg")));
        }
    }
}
