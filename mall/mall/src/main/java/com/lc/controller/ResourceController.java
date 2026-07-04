package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.model.entity.Resource;
import com.lc.model.vo.ResourcePageReqVO;
import com.lc.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('/resource/create')")
    public Result createResource(@RequestBody Resource resource){
        if(resourceService.create(resource) == 1){
            return Result.ok("添加成功");
        }
        else{
            return Result.error("添加失败");
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('/resource/update')")
    public Result updateResource(@RequestBody Resource resource){
        if(resourceService.update(resource) == 1){
            return Result.ok("更新成功");
        }
        else{
            return Result.error("更新失败");
        }
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('/resource/delete')")
    public Result deleteResource(Integer id){
        if(resourceService.delete(id) == 1){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('/resource/getPage')")
    public Result getResourcePage(@RequestBody ResourcePageReqVO pageReqVO){
        return Result.ok(resourceService.getPage(pageReqVO));
    }

    /**
     * @param roleId:
     * @author: Charles
     * @date: 2022/8/6 10:33
     * @description: 根据roleId返回resource的列表
     */
    @GetMapping("/getList")
    @PreAuthorize("hasAuthority('/resource/getPage')")
    public Result getResourceList(@RequestParam("roleId") Integer roleId){
        return Result.ok(resourceService.getList(roleId));
    }

}
