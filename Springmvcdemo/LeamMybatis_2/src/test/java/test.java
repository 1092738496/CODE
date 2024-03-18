import com.meditation.dao.bookmapper;
import com.meditation.pojo.book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/5 21:09
 */

public class test {
    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        bookmapper mapper = sqlSession.getMapper(bookmapper.class);
        //按id查询
        book getbook = mapper.getbook(10);
        System.out.println(getbook);
        //查询所有
        List<book> selectbook = mapper.selectbook();
        //修改
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        byte[] bytes = new byte[23];
        book book = new book(1, "张三",bytes,"1","1" ,timestamp,"1",1.0,1.0);
        mapper.updatebook(book);
        //删除
        mapper.deletebook(1);
        mapper.intsetbook(book);
        sqlSession.commit();

    }
}
