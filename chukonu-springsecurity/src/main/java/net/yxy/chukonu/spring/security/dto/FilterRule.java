package net.yxy.chukonu.spring.security.dto;

import lombok.Data;

/**
 * [{"field":"currentHistory","op":"contains","value":"xue"}]
 * 
 * @author yexianyi
 *
 */

@Data
public class FilterRule {
    String field;
    String op;
    String value;
}
