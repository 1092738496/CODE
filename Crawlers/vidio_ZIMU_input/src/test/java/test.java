import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import org.junit.Test;

import java.io.File;

/**
 * @time: 2024/3/29 4:07
 * @description:
 */

public class test {
    @Test
    public void test1() {
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);


        File file = new File("E:\\Downloads\\五步陷阱\\自尊摧毁");
        for (File listFile : file.listFiles()) {
            OcrResult ocrResult = engine.runOcr(listFile.getPath());
            System.out.println(ocrResult.getStrRes().trim());
        }
    }
}
