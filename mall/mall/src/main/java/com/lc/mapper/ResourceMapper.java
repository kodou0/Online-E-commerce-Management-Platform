package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> getListByRole(Integer roleId);
}
