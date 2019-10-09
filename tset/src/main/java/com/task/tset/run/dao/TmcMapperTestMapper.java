package com.task.tset.run.dao;

import com.task.tset.run.entity.TmcMapperTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TmcMapperTestMapper {

    List<TmcMapperTest> selectAll();

    List<TmcMapperTest> selectByName(@Param(value = "name") String name);

    List<TmcMapperTest> selectByParameter(@Param(value = "name") String name,
                                          @Param(value = "one") String one,
                                          @Param(value = "two")Integer two);

}