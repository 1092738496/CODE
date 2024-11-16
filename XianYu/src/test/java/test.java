import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.Test;
import utils.httpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @time: 2024/10/31 0:45
 * @description:
 */

public class test {
    @Test
    public void test1(){
        String j = String.valueOf(System.currentTimeMillis());
        List<Header> headers  = new ArrayList<>();
        String Cookie = "cookie2=103cf0d127438c3bad1af93bb983daf4; _samesite_flag_=true; t=1d7c57ad5ab1ff5591169aac262b0ba5; _tb_token_=f5f6e558859e8; sgcookie=E100ZSYKZDiSroTEjDWAZL9W0AIKyK6QRJL50%2BnfdYxgk%2BWXv8YTwGYWMJymfcIiepvVfD6L3P3XU%2FRD3bkUYMV2Q2Dv31xXJVqTgegtdg7Wunk%3D; csg=dfda9921; unb=2456556960; mtop_partitioned_detect=1; _m_h5_tk=74c789721156a03d35df0bec08f6c441_1730316593547; _m_h5_tk_enc=54c458aa0924a5a71c64993f6749db9e; isg=BHx8i8cQNy00YAPxKlCX34cRTRoudSCfWtHcpFb9sWdKIRyrfobeL_KYBUlZaVj3";
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        headers.add(new BasicHeader("Cookie", Cookie));
        httpUtils httpUtils = new httpUtils(headers);
        List<NameValuePair> formParams = new ArrayList<>();
        String data = "%7B%22pageNumber%22%3A1%2C%22keyword%22%3A%22%E5%AE%89%E5%8D%93%E8%8B%B9%E6%9E%9C%E5%8F%8C%E7%AB%AF%E6%90%AD%E5%BB%BA%22%2C%22fromFilter%22%3Afalse%2C%22rowsPerPage%22%3A30%2C%22sortValue%22%3A%22%22%2C%22sortField%22%3A%22%22%2C%22customDistance%22%3A%22%22%2C%22gps%22%3A%22%22%2C%22propValueStr%22%3A%7B%7D%2C%22customGps%22%3A%22%22%2C%22searchReqFromPage%22%3A%22pcSearch%22%7D";
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        formParams.add(new BasicNameValuePair("data", data));
        String post = httpUtils.doPost("https://h5api.m.goofish.com/h5/mtop.taobao.idlemtopsearch.pc.search/1.0/?jsv=2.7.2&appKey=34839810&t=1730307539389&sign=2d6fe07bd52f8e3634eb8824bae10488&v=1.0&type=originaljson&accountSite=xianyu&dataType=json&timeout=20000&api=mtop.taobao.idlemtopsearch.pc.search&sessionOption=AutoLoginOnly&spm_cnt=a21ybx.search.0.0&spm_pre=a21ybx.search.searchInput.0", map);
        System.out.println(post);
    }
}
