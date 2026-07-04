package com.lc.service;

import com.lc.common.result.PageResult;
import com.lc.model.entity.Resource;
import com.lc.model.vo.ResourcePageReqVO;

import java.util.List;

public interface ResourceService {
    Integer create(Resource resource);

    Integer update(Resource resource);

    Integer delete(Integer id);

    PageResult<Resource> getPage(ResourcePageReqVO pageReqVO);

    List<Resource> getList(Integer roleId);
}
