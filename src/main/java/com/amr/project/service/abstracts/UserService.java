package com.amr.project.service.abstracts;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;

public interface UserService extends ReadWriteService<User, Long>{

    boolean activate(String secret);
    boolean addUser(UserDto userDto);
    User findByUsername(String username);
    boolean verifyUserBySecret(User user);
    boolean existByUserName(String userName);
}
