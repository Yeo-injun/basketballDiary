package com.threeNerds.basketballDiary.mvc.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserRepository {
    Map<String,Object> findUser(Long id);
    void saveUser(Map<String,Object> sqlParam);
}
