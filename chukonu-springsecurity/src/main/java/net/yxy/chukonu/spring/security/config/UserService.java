package net.yxy.chukonu.spring.security.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.yxy.chukonu.spring.security.entity.UserEntity;
import net.yxy.chukonu.spring.security.mybatis.mapper.SecurityMapper;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private SecurityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = findUserbyUsername(username);

        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(DigestUtils.md5Hex(user.getPassword().toString().getBytes())) ;
            builder.roles(user.getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private UserEntity findUserbyUsername(String username) {
        return mapper.getOne(username) ;
    }

}