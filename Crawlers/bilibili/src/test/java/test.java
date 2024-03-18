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
        headers.add(new BasicHeader("Cookie", "PHPSESSID=ojak7lg9gs2oem717kpgfdd21h; 66471c01a4fe486046a7ba8d0ad69a3f=ef994fee29508dec5c71f57804ba0b0c; online-uuid=15D613F7-5D2A-B336-CE48-B5E19D6E45D2"));
        httpUtils utils = new httpUtils(headers);
        String s = null;
        try {
            s = utils.get(
                    "https://docs.opentrons.com/v2/moving_labware.html");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(s);
    }

}
