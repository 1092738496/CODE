package dao;

import org.apache.ibatis.annotations.Param;
import pojo.press;

/**
 * @time: 2024/1/23 18:14
 * @description:
 */
public interface pressdao {
    int insert(press record);

    int sum();

    press getpress(@Param("id") int id);

    int updatePress(press press);
}
