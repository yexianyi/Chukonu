package net.yxy.chukonu.spring.security.view;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import net.yxy.chukonu.spring.security.entity.ProductEntity;

@Data
@Builder
public class ResultView {
    int total;
    List<ProductEntity> rows;

}
