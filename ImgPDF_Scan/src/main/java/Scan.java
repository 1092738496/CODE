import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import utils.tool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @description:
 * @author: Andy
 * @time: 2023-8-26 15:37
 */

public class Scan {
    public File[] PDFPath(String path) {

        File file = new File(path);
        File[] files = file.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String s = dir + name;
                String[] split = name.split("\\.");
                if (split[1].equals("pdf") || split[1].equals("PDF")) {
                    return true;
                }

                return false;
            }
        });
        return files;
    }


    public LinkedList<LinkedList<String>> getlist(File file) {
        LinkedList<LinkedList<String>> lists = new LinkedList<LinkedList<String>>();
        try {
            // 1、加载指定PDF文档
            PDDocument document = PDDocument.load(file);
            int pageNumber = document.getNumberOfPages();
            // String[] coordinates = tool.getstr("coordinates").split(","); //坐标
            //String[] width_and_heights = tool.getstr("width_and_height").split(","); //宽高
            // 2、获取图片
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            String property = System.getProperty("user.dir");
            String imgpath = property+"\\images\\";
            /*String imgpath = "D:\\images\\";*/
            HardwareConfig hardwareConfig = new HardwareConfig(12, -1);
            InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4, hardwareConfig);
            //PDFTextStripper stripper =new PDFTextStripper();
            for (int i = 0; i < pageNumber; i++) {
                System.out.println(i);
                BufferedImage Image = pdfRenderer.renderImageWithDPI(i, 300, ImageType.GRAY);
                BufferedImage srcImage = resize(Image, 3000, 4000);
                ImgUtil.cut(srcImage, FileUtil.file(imgpath + "\\" + i + ".png"), new Rectangle(0, 630, 1450, 98));
                // 语言库位置（修改为跟自己语言库文件夹的路径）
                /*String result = this.cmd("cmd.exe", "/c", "cd", property + "\\RapidOCR-json_v0.2.0", "&&", "RapidOCR-json" +
                                ".exe",
                        "--image_path=" + imgpath + "\\" + i + ".png");*/
                // 开始识别
                OcrResult ocrResult = engine.runOcr(imgpath + "\\" + i + ".png");
                String result = ocrResult.getStrRes();
                System.out.println(result);
                String[] imgformats = tool.imgformats(result);
                LinkedList<String> list = new LinkedList<String>(Arrays.asList(imgformats));
                lists.add(list);
                System.out.println("--------------------------");
            }

            // 4、关闭
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }

    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public String cmd(String... strings){
        StringBuilder ret = new StringBuilder();
        try {
            // 构建命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(strings);
            // 启动命令
            Process process = processBuilder.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                ret.append(line);
            }
            // 等待命令执行完成
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
