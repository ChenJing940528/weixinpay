package com.chenjing.weixinpay.mapper;

import com.chenjing.weixinpay.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description：VideoOrderMapper
 *
 * @author:chenjing
 * @version:1.0
 * @time:20:49
 */
/*
* 订单dao层
* */
public interface VideoOrderMapper {

    /*
    * 保存订单，返回包含主键
    * */
    @Insert("INSERT INTO video_order(openid, out_trade_no, state, create_time, " +
            "notify_time, total_fee, nickname, head_img, video_id, video_title," +
            "video_img, user_id, ip, del)" +
            "VALUES" +
            "(#{openid}, #{outTradeNo}, #{state}, #{createTime}, #{notifyTime}," +
            " #{totalFee}, #{nickname}, #{headImg}, #{videoId},  #{videoTitle}," +
            "#{videoImg}, #{userId}, #{ip}, #{del})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(VideoOrder videoOrder);

    /*
   * 根据主键查找订单
   * */
    @Select("SELECT * From video_order WHERE id= #{order_id} AND del = 0")
    VideoOrder findById(@Param("order_id") int id);

    /*
    * 根据订单号获取订单对象
    * */
    @Select("SELECT * From video_order WHERE out_trade_no= #{out_trade_no} AND del = 0")
    VideoOrder findByOutTradeNo(@Param("out_trade_no") String outTradeNo);

    /*
    * 逻辑删除订单
    * */
    @Update("UPDATE video_order SET del = 0 WHERE id = #{id} AND user_id = #{userId}")
    int del(@Param("id") int id, @Param("userId") int userId);

    /*
    * 查找我的全部订单
    * */
    @Select("SELECT * From video_order WHERE user_id= #{userId}")
    List<VideoOrder> findMyOrderList(@Param("userId") int userId);

    /*
    * 根据订单流水号进行更新
    * */
    @Update("UPDATE video_order SET state = #{state}, notify_time= #{notifyTime}," +
            " openid= #{openid}" +
            "WHERE out_trade_no = #{outTradeNo} AND state = 0 AND del = 0")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
