package net.yxy.chukonu.spring.security.dto;

import lombok.Data;

@Data
public class QueryCondition {
    String condition;

    public QueryCondition(String condition) {
        this.condition = condition;
    }

}
