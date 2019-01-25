package net.yxy.chukonu.spring.security.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.yxy.chukonu.spring.security.entity.ProductEntity;

public interface ProductMapper {

    @Select("SELECT * FROM PRODUCTS WHERE ID > (#{pageNum} * #{pageSize}) ORDER BY ID ASC LIMIT #{pageSize}")
    @Results({ @Result(property = "updateBy", column = "UPDATE_BY") ,
               @Result(property = "updateTime", column = "UPDATE_TIME") })
    List<ProductEntity> getPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    
    
    @Select("SELECT * FROM PRODUCTS ORDER BY ID")
    @Results({ @Result(property = "updateBy", column = "UPDATE_BY") ,
               @Result(property = "updateTime", column = "UPDATE_TIME") })
    List<ProductEntity> getAll();

    
    
    @Select("SELECT * FROM PRODUCTS WHERE REC_ID = #{recordId}")
    @Results({ @Result(property = "updateBy", column = "UPDATE_BY") ,
               @Result(property = "updateTime", column = "UPDATE_TIME") })
    ProductEntity getOne(Long id);

    
    
    @Select("SELECT count(id) FROM PRODUCTS")
    int count();

    
    
    @Insert("INSERT INTO PRODUCTS(NAME,TYPE,PRICE,QUOTA,UPDATE_BY,UPDATE_TIME) "
            + "VALUES(#{name}, #{type}, #{price},#{quota},#{updateBy},#{updateTime})")
    void insert(ProductEntity entity);

    
    
    @Update("UPDATE PRODUCTS SET TYPE=#{type}, UPDATE_TIME=#{updateTime} WHERE ID=#{id}")
    void update(ProductEntity entity);

    
    
    @Delete("DELETE FROM PRODUCTS WHERE ID =#{id}")
    void delete(String id);

    
    @Select("SELECT count(id) FROM PRODUCTS")
    int countWithCondition(ProductEntity entity);

    
    
    @Select("<script>" + "SELECT * FROM PRODUCTS WHERE 1=1 "
            + "<if test='entity.name!=null'> AND NAME LIKE CONCAT('%',#{entity.name},'%') </if>"
            + "<if test='entity.type!=null'> AND TYPE LIKE CONCAT('%',#{entity.type},'%') </if>  "
            + "<if test='entity.price!=null'> AND PRICE LIKE CONCAT('%',#{entity.price},'%') </if>  "
            + "<if test='entity.quota!=null'> AND QUOTA LIKE CONCAT('%',#{entity.quota},'%') </if>  "
            + "<if test='entity.updateBy!=null'> AND UPDATE_BY LIKE CONCAT('%',#{entity.updateBy},'%') </if>  "
            + "<if test='entity.updateTime!=null'> AND UPDATE_TIME LIKE CONCAT('%',#{entity.updateTime},'%') </if>  "
            + "LIMIT #{skip}, #{pageSize} </script>")
    @Results({ @Result(property = "updateBy", column = "UPDATE_BY") ,
        @Result(property = "updateTime", column = "UPDATE_TIME") })
    List<ProductEntity> getPageWithCondition(ProductEntity entity, int skip, int pageSize);
}
