package com.lc.serviceImpl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.common.result.PageResult;
import com.lc.mapper.ResourceMapper;
import com.lc.model.entity.Resource;
import com.lc.model.vo.ResourcePageReqVO;
import com.lc.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceImpl implements ResourceService {

    @jakarta.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public Integer create(Resource resource) {

        return resourceMapper.insert(resource);
    }

    @Override
    public Integer update(Resource resource) {
        return resourceMapper.updateById(resource);
    }

    @Override
    public Integer delete(Integer id) {
        return resourceMapper.deleteById(id);
    }

    @Override
    public PageResult<Resource> getPage(ResourcePageReqVO pageReqVO) {
        Page<Resource> page = new Page<>(pageReqVO.getPageNum(),pageReqVO.getPageSize());
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(pageReqVO.getName())){
            queryWrapper.like("name",pageReqVO.getName());
        }
        if(!StringUtils.isEmpty(pageReqVO.getPath())){
            queryWrapper.like("permission",pageReqVO.getPath());
        }
        if(pageReqVO.getCategoryId() != null){
            queryWrapper.eq("category_id",pageReqVO.getCategoryId());
        }
        resourceMapper.selectPage(page,queryWrapper);
        return new PageResult<>(page);
    }

    @Override
    public List<Resource> getList(Integer roleId) {
        return resourceMapper.getListByRole(roleId);
    }
}
