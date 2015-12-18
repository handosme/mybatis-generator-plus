package org.ihansen.app.domain.operate;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ihansen.app.domain.BusLog;
import org.ihansen.app.domain.BusLogEX;

public interface BusLogMapper {
    public static final String DATA_SOURCE_NAME = "db5";

    int countByExample(BusLogEX example);

    int deleteByExample(BusLogEX example);

    int deleteByPrimaryKey(Long id);

    int insert(BusLog record);

    int insertSelective(BusLog record);

    List<BusLog> selectByExample(BusLogEX example);

    BusLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BusLog record, @Param("example") BusLogEX example);

    int updateByExample(@Param("record") BusLog record, @Param("example") BusLogEX example);

    int updateByPrimaryKeySelective(BusLog record);

    int updateByPrimaryKey(BusLog record);

    int insertBatch(List<BusLog> records);
}