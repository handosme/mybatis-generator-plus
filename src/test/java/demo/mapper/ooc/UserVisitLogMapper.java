package demo.mapper.ooc;

import demo.domain.ooc.UserVisitLog;
import demo.domain.ooc.UserVisitLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserVisitLogMapper {
    public static final String DATA_SOURCE_NAME = "db6";

    int countByExample(UserVisitLogExample example);

    int deleteByExample(UserVisitLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserVisitLog record);

    int insertSelective(UserVisitLog record);

    List<UserVisitLog> selectByExample(UserVisitLogExample example);

    UserVisitLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserVisitLog record, @Param("example") UserVisitLogExample example);

    int updateByExample(@Param("record") UserVisitLog record, @Param("example") UserVisitLogExample example);

    int updateByPrimaryKeySelective(UserVisitLog record);

    int updateByPrimaryKey(UserVisitLog record);

    int insertBatch(List<UserVisitLog> records);

    List<UserVisitLog> selectByBigOffset(UserVisitLogExample example);
}