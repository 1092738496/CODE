package test;

import com.meditation.conf.personConfig;
import com.meditation.pojo.person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @time: 2024/7/11 15:08
 * @description:
 */

public class test {

    @Test
    public void test(){
        ApplicationContext  context = new AnnotationConfigApplicationContext(personConfig.class);
        person person = context.getBean("Person", person.class);
        System.out.println(person.getName());
    }
}
