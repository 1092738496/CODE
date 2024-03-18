import com.meditation.dao.bookDao;
import com.meditation.pojo.book;
import com.meditation.utils.MybatisUtils;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/1/5 2:35
 */

public class test {
    @Test
    public void test(){
        SqlSession sqlsession = MybatisUtils.getSqlsession();

        System.out.println(sqlsession);
        bookDao mapper = sqlsession.getMapper(bookDao.class);

        List<book> getbook = mapper.getbook();
        for (book book : getbook) {
            System.out.println(book);
        }
    }
    @Test
    public void test2(){


        DataSource dataSource = new PooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/fiction?serverTimezone=Asia" +
                "/Shanghai&amp;useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory,
                dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(bookDao.class);
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(configuration).openSession();
        sqlSession.selectList("com.meditation.dao.bookDao");

    }

}
