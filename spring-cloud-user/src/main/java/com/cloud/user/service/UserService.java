package com.cloud.user.service;

import com.cloud.user.mapper.UserMapper;
import com.cloud.user.pojo.User;
import com.cloud.user.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User authUser(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0)
            return users.get(0);
        return new User();
    }
}
