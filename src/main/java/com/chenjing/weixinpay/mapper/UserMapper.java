package com.chenjing.weixinpay.mapper;

import com.chenjing.weixinpay.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description：UserMapper
 *
 * @author:chenjing
 * @version:1.0
 * @time:13:12
 */
/*
* User数据访问层
* */
public interface UserMapper {

    /*
    * 根据主键id查找用户
    * */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByid(@Param("id") int userId);

    /*
    * 根据openid查找用户
    * */
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User findByopenid(@Param("openid") String openid);

    /*
    * 保存新用户
    * */
    @Insert("INSERT INTO user(openid, name, head_img, phone, " +
            "sign, sex, city, create_time)" +
            "VALUES" +
            "(#{openid}, #{name}, #{headImg}, #{phone}, #{sign}," +
            " #{sex}, #{city}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    //只有通过这种注解的方式才能取到自增的id，其中keyProperty是对象中的id，keyColumn是数据库中的id
    int save(User user);
}
