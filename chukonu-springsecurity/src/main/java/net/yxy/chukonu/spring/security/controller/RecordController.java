package net.yxy.chukonu.spring.security.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.yxy.chukonu.spring.security.entity.ProductEntity;
import net.yxy.chukonu.spring.security.global.Role;
import net.yxy.chukonu.spring.security.mybatis.mapper.ProductMapper;
import net.yxy.chukonu.spring.security.util.UserUtil;

@RestController
@RequestMapping("/api/v1/record")
public class RecordController {
    @Autowired
    ProductMapper mapper;

    @PostMapping("/update")
    @Secured({ Role.ADMIN, Role.EDITOR })
    public void update(ProductEntity entity) {
        entity.setUpdateTime(new Date());
        entity.setUpdateBy(UserUtil.getCurrentUsername()) ;
        mapper.update(entity);
    }
    

}
