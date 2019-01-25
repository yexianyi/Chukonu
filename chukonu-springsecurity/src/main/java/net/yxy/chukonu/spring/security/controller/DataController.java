package net.yxy.chukonu.spring.security.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import net.yxy.chukonu.spring.security.dto.FilterRule;
import net.yxy.chukonu.spring.security.entity.ProductEntity;
import net.yxy.chukonu.spring.security.entity.ProductEntity.ProductEntityBuilder;
import net.yxy.chukonu.spring.security.global.Role;
import net.yxy.chukonu.spring.security.mybatis.mapper.ProductMapper;
import net.yxy.chukonu.spring.security.util.JSONUtil;
import net.yxy.chukonu.spring.security.view.ResultView;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DataController {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ProductMapper mapper;

    @PostMapping("/data")
    @Secured({ Role.ADMIN, Role.EDITOR, Role.VIEWER })
    public ResultView getWithRules(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int rows, 
                                   @RequestParam(required = false) String filterRules) throws JsonParseException, JsonMappingException, IOException {
        ResultView view = null;
        if (filterRules != null) {
            List<FilterRule> rules = JSONUtil.parseObjects(filterRules, FilterRule.class);
            ProductEntity queryTemplate = convertFilterRules(rules);
            int total = mapper.countWithCondition(queryTemplate);
            List<ProductEntity> list = mapper.getPageWithCondition(queryTemplate, (page - 1) * rows, rows);
            view = ResultView.builder().total(total).rows(list).build();

        } else {
            int total = mapper.count();
            List<ProductEntity> list = mapper.getPage(page - 1, rows);
            view = ResultView.builder().total(total).rows(list).build();
        }

        return view;
    }

    private ProductEntity convertFilterRules(List<FilterRule> rules) {
        ProductEntityBuilder builder = ProductEntity.builder();
        for (FilterRule rule : rules) {
            switch (rule.getField()) {
                case "name":
                    builder.name(rule.getValue());
                    break;
                case "type":
                    builder.type(rule.getValue());
                    break;
                case "price":
                    builder.price(Float.valueOf(rule.getValue()));
                    break;
                case "quota":
                    builder.quota(Integer.valueOf(rule.getValue()));
                    break;
                case "updateBy":
                    builder.updateBy(rule.getValue());
                    break;
                case "updateTime":
                    try {
                        builder.updateTime(sdf.parse(rule.getValue()));
                    } catch (ParseException e) {
                        log.error(e.getMessage());
                    }
                    break;
                default:
            }
        }

        return builder.build();

    }

}
