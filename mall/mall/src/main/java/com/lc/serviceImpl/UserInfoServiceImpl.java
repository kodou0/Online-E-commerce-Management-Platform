package com.lc.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.common.result.PageResult;
import com.lc.common.result.Result;
import com.lc.mapper.UserInfoMapper;
import com.lc.mapper.UserMapper;
import com.lc.mapper.UserRoleMapper;
import com.lc.model.entity.User;
import com.lc.model.entity.UserInfo;
import com.lc.model.vo.UserCreateVO;
import com.lc.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public PageResult<UserInfo> selectUserInfoPage(Integer pageNum, Integer pageSize, String name,Integer roleId) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserInfo> list = userInfoMapper.selectUserPage(roleId, name);
        PageInfo<UserInfo> pageInfo = PageInfo.of(list);
        PageResult<UserInfo> result = new PageResult<>();
        result.setRecords(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        result.setSize((long) pageInfo.getSize());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result createUser(UserCreateVO userCreateVO) {
        UserInfo userInfo = new UserInfo();
        User user = new User();
        if(userMapper.selectByUserName(userCreateVO.getUsername()) != 0){
            return Result.error("用户名已经存在");
        }
        user.setUserName(userCreateVO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateVO.getPassword()));
        user.setEnabled(1);
        userMapper.insert(user);
        userInfo.setUserId(user.getId());
        userInfo.setGender(userCreateVO.getGender());
        userInfo.setName(userCreateVO.getName());
        userInfo.setPhone(userCreateVO.getPhone());
        if(userInfoMapper.insert(userInfo) >= 1) {
            return Result.created();
        }
        else{
            return Result.error("更新失败");
        }
    }

    @Override
    public Result deleteUserById(Integer id) {
        if(userInfoMapper.deleteJoinById(id) >= 2){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }

    }

    @Override
    public Result updateUser(UserInfo userInfo) {
        if(userInfoMapper.updateById(userInfo) >= 1){
            return Result.ok("更新成功");
        }
        else{
            return Result.error("更新失败");
        }

    }

    @Override
    public Result getUserDetails(Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        if(userInfo == null){
            return Result.error("查询失败");
        }
        userInfo.setUsername(userMapper.selectUserName(userInfo.getUserId()));
        return Result.ok(userInfo);
    }

    @Override
    public Result getUserRole(Integer userId) {
        return Result.ok(userRoleMapper.selectRoleName(userId));
    }
}
