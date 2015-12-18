package org.ihansen.app.domain.operate;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ihansen.app.domain.MSBusLog;
import org.ihansen.app.domain.MSBusLogEX;

public interface MSBusLogMapper {
    public static final String DATA_SOURCE_NAME = "db5";

    int countByExample(MSBusLogEX example);

    int deleteByExample(MSBusLogEX example);

    int deleteByPrimaryKey(Long id);

    int insert(MSBusLog record);

    int insertSelective(MSBusLog record);

    List<MSBusLog> selectByExample(MSBusLogEX example);

    MSBusLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MSBusLog record, @Param("example") MSBusLogEX example);

    int updateByExample(@Param("record") MSBusLog record, @Param("example") MSBusLogEX example);

    int updateByPrimaryKeySelective(MSBusLog record);

    int updateByPrimaryKey(MSBusLog record);

    int insertBatch(List<MSBusLog> records);
}