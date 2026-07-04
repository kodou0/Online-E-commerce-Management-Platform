package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.UserRoleDO;
import com.lc.model.vo.SimpleRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    /**
     * @param roleId:
     * @author: Charles
     * @date: 2022/7/30 15:55
     * @description: 根据角色id返回用户人数
     */
    Integer selectCountByRoleId(Integer roleId);

    /**
     * @param userId:
     * @author: Charles
     * @date: 2022/7/31 16:42
     * @description: 根据用户id查询权限字符串
     */
    List<String> selectPermsByUserId(Integer userId);

    SimpleRoleVO selectRoleName(Integer userId);

    Integer updateStatus(@Param("status") Integer status, @Param("roleId") Integer roleId);


}
