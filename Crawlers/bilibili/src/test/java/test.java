import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.junit.Test;
import tools.httpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:
 * @time: 2023-10-10 02:47
 * @description:
 */

public class test {


    @Test
    public void test4() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        httpUtils utils = new httpUtils(headers);
        String s = null;
        try {

                s = utils.get("https://www.okooo.com/soccer/match/1257623/ah/");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
                System.out.println(s);

    }

}
