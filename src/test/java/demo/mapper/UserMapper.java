package demo.mapper;

import demo.domain.User;
import demo.domain.UserEX;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    public static final String DATA_SOURCE_NAME = "db6";

    int countByExample(UserEX example);

    int deleteByExample(UserEX example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserEX example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserEX example);

    int updateByExample(@Param("record") User record, @Param("example") UserEX example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int insertBatch(List<User> records);
}