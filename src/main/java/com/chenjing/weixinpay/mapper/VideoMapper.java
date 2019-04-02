package com.chenjing.weixinpay.mapper;

import com.chenjing.weixinpay.domain.Video;
import com.chenjing.weixinpay.privider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description：VideoMapper
 *
 * @author:chenjing
 * @version:1.0
 * @time:20:00
 */
/*
* video数据访问层
* */
public interface VideoMapper {

    @Select("SELECT * FROM video")
            /*
            * 复杂的去驼峰方法
            * */
//    @Results({
//            @Result(column = "cover_img",property = "coverImg"),
//            @Result(column = "create_time",property = "createTime")
//    })
    List<Video> findAll();

    @Select("SELECT * FROM video WHERE id = #{id}")
    Video findById(int id);

//    @Update("UPDATE video SET title = #{title} WHERE id = #{id}")
    @UpdateProvider(type = VideoProvider.class, method = "updateVideo")
    int update(Video video);

    @Delete("DELETE FROM video WHERE id = #{id}")
    int delete(int id);

    @Insert("INSERT INTO video(title, summary, cover_img, view_num, " +
            "price, create_time, online, point)" +
            "VALUES" +
            "(#{title}, #{summary}, #{coverImg}, #{viewNum}, #{price}," +
            " #{createTime}, #{online}, #{point})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    //只有通过这种注解的方式才能取到自增的id，其中keyProperty是对象中的id，keyColumn是数据库中的id
    int save(Video video);
}
