import com.meditation.dao.ActorMapper;
import com.meditation.pojo.Actor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @time: 2023/12/28 3:22
 * @description:
 */

public class test {
    @Test
    public void test1(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);
        Actor actor = mapper.selectByPrimaryKey(new Short("1"));
        System.out.println(actor);
    }
    @Test
    public void test2(){
       Logger logger = LoggerFactory.getLogger(test.class);
        logger.error("sss");
        System.out.println("普通输出的白色");
        System.out.format("\33[30;1m30:黑色%n");
        System.out.format("\33[31;1m31:红色%n");
        System.out.format("\33[32;1m32:绿色%n");
        System.out.format("\33[33;1m33:不知道什么黄的黄色%n");
        System.out.format("\33[34;1m34:蓝色%n");
        System.out.format("\33[35;1m35:紫色%n");
        System.out.format("\33[36;1m36:蒂芙尼蓝%n");
        System.out.format("\33[37;1m37:深灰色%n");
        System.out.format("\33[38;1m38:浅灰色%n");
    }

}
