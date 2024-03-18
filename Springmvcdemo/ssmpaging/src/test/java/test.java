import com.meditation.controller.BookController;
import com.meditation.dao.BookDao;
import com.meditation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 15:11
 */


@ContextConfiguration(locations = "classpath:applicationcontext.xml" )
public class test {
    @Autowired
    BookDao bookDao;

    @Autowired
    BookService bookService;

    @Autowired
    BookController bookController;

    public void test(){


    }
}
