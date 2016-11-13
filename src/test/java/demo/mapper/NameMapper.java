package demo.mapper;

import demo.domain.Name;
import demo.domain.NameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NameMapper {
    public static final String DATA_SOURCE_NAME = "null";

    int countByExample(NameExample example);

    int deleteByExample(NameExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Name record);

    int insertSelective(Name record);

    List<Name> selectByExample(NameExample example);

    Name selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Name record, @Param("example") NameExample example);

    int updateByExample(@Param("record") Name record, @Param("example") NameExample example);

    int updateByPrimaryKeySelective(Name record);

    int updateByPrimaryKey(Name record);

    int insertBatch(List<Name> records);
}