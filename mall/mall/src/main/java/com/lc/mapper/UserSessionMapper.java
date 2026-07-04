package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


public interface UserSessionMapper extends BaseMapper<UserSession> {
    UserSession selectByUserId(Integer userId);
}
