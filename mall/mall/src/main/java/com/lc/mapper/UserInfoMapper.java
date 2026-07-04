package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    Integer deleteJoinById(Integer id);

    List<UserInfo> selectUserPage(@Param("roleId") Integer roleId, @Param("name") String name);
}
