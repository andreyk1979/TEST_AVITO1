package com.amr.project.service.abstracts;

import org.springframework.stereotype.Component;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.AuthenticationProvider;
import com.amr.project.model.entity.User;


public interface UserService extends ReadWriteService<User, Long> {

    boolean activate(String secret);

    boolean addUser(UserDto userDto);

    User findByUsername(String username);

    boolean verifyUserBySecret(User user);

    boolean existByUserName(String userName);

    User findByEmail(String email);

    void createNewUserAfterOAuthLoginSuccess(String email, String name,
                                             AuthenticationProvider provider);

    void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider provider);
}
