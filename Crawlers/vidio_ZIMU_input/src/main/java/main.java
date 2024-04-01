import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @time: 2024/3/29 3:09
 * @description:
 */

public class main {
    public static void main(String[] args)  {
        String filepath = "E:\\Downloads\\五步陷阱";
        String ffmpeg = "D:\\Development_Tool\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffmpeg.exe";
        File file = new File(filepath);
        Runtime runtime = Runtime.getRuntime();
        for (File listFile : file.listFiles()) {
            String[] split = listFile.getName().split("\\.");
            int i = Integer.parseInt(split[0]);
            if (i >= 3) {
                System.out.println(listFile.getPath());
                String s = filepath+"\\"+split[1];
                new File(s).mkdir();
                String dom = ffmpeg+" -i \"" + listFile.getPath() + "\" -vf \"select='not(mod(n,70))'," +
                        "crop=ih:ih*1/8:200:ih-ih*1/10\" -vsync vfr \""+s+"\\%03d" +
                        ".jpg\"";
                System.out.println(dom);
                try {
                    // 执行命令
                    Process process = runtime.exec(dom);

                    // 创建流来读取FFmpeg的输出和错误信息（非必需，但有助于调试）
                    InputStream stdout = process.getInputStream();
                    InputStream stderr = process.getErrorStream();

                    // 新建线程分别读取输出和错误信息
                    new Thread(() -> readStream(stdout)).start();
                    new Thread(() -> readStream(stderr)).start();

                    // 等待进程执行完成
                    int exitCode = process.waitFor();

                    // 检查退出码
                    if (exitCode == 0) {
                        System.out.println("FFmpeg命令执行成功");
                    } else {
                        System.err.println("FFmpeg命令执行失败，退出码: " + exitCode);
                    }
                    boolean b = ocr_zimu(s, "致命");
                    if (b) {
                        System.out.println("找到!");
                        System.out.println(s);
                        System.out.println(listFile.getPath());
                        return ;
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }


            }
        }
    }
    private static void readStream(InputStream stream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
    private static boolean ocr_zimu(String imgpath,String name){
        File file = new File(imgpath);
        for (File listFile : file.listFiles()) {
            OcrResult ocrResult = engine.runOcr(listFile.getPath());
            System.out.println(ocrResult.getStrRes().trim());
            if (ocrResult.getStrRes().trim().contains(name)) {
                return true;
            }
        }
        return false;
    }
}
