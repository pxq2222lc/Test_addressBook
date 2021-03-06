package com.driving.mapper;

import com.driving.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * created by wk on 2017-11-24
 * UserMapper 映射，方法对应 UserMapper.xml 的 id 名
 */
@Component
public interface UserMapper {

    /**
     * 插入用户信息
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 获取当前用户信息
     * @param id
     * @return
     */
    User getUser(@Param("id")String id);


    void updateUser(@Param("id")String id,@Param("username")String username,@Param("image")String image,@Param("district")String district);
}
