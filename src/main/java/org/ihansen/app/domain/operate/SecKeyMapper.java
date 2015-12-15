package org.ihansen.app.domain.operate;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ihansen.app.domain.SecKey;
import org.ihansen.app.domain.SecKeyEX;

public interface SecKeyMapper {
    public static final String DATA_SOURCE_NAME = "DEFAULT";

    int countByExample(SecKeyEX example);

    int deleteByExample(SecKeyEX example);

    int deleteByPrimaryKey(Long id);

    int insert(SecKey record);

    int insertSelective(SecKey record);

    List<SecKey> selectByExample(SecKeyEX example);

    SecKey selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SecKey record, @Param("example") SecKeyEX example);

    int updateByExample(@Param("record") SecKey record, @Param("example") SecKeyEX example);

    int updateByPrimaryKeySelective(SecKey record);

    int updateByPrimaryKey(SecKey record);

    int insertBatch(List<SecKey> records);
}