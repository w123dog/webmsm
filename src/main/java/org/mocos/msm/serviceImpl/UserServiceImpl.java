/**
 * Copyright (c) 2012,　六动力（福建）网络科技有限公司  All rights reserved。
 *
 * UserServiceImpl.java
 */

package org.mocos.msm.serviceImpl;

import org.mocos.msm.entity.User;
import org.mocos.msm.mapper.UserMapper;
import org.mocos.msm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 * @date 2013-2-5 上午10:56:08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    protected UserMapper userMapper;

    /**
     * 获取所有的用户信息
     */

    public List<User> queryAllUsers() {
        return userMapper.getUsers();
    }

    /**
     * 用户登录
     */
    public boolean login(User user) {
        if (userMapper.getUserByName(user.getName()) != null
                && userMapper.getUserByName(user.getName()).getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     *
     * @return
     */
    public boolean register(User user) {
        userMapper.saveUser(user);
        if (userMapper.getUserByName(user.getName()) != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户名查找用户
     */
    public boolean getUserByName(String name) {
        User user = userMapper.getUserByName(name);
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     */
    public boolean delUsers(int id) {
        userMapper.deleteUser(id);
        if (userMapper.getUserById(id) == null) {
            return true;
        }
        return false;
    }

    /**
     * 根据id查找用户
     */
    @Cacheable(value = "userCache", key = "#id")
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    /**
     * 修改用户
     */
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }


}
