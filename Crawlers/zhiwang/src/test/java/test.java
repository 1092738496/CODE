import dao.pressdao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pojo.press;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description:
 * @author: Andy
 * @time: 2023-9-18 16:54
 */

public class test {
    @Test
    public void test1() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        pressdao pressdao = sqlSession.getMapper(pressdao.class);
        press press = new press();
        press.setId(3856);
        press.setDate("qqqqq");
        System.out.println(pressdao.updatePress(press));
        sqlSession.commit();
    }
}
