package com.chenjing.weixinpay.privider;

import com.chenjing.weixinpay.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * description：VideoProvider
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:56
 */

/*
* video构建动态sql语句
* */
public class VideoProvider {

    /*
    * 跟新video动态语句
    * @param video
    * */
    public String updateVideo(final Video video){
        return new SQL(){{
            UPDATE("video");

            //条件写法
            if(video.getTitle() != null){
                SET("title=#{title}");
            }
            if(video.getSummary() != null){
                SET("summary=#{summary}");
            }
            if(video.getCoverImg() != null){
                SET("cover_image=#{coverImg}");
            }
            if(video.getViewNum() != null){
                SET("view_num=#{viewNum}");
            }
            if(video.getPrice() != null){
                SET("price=#{price}");
            }
            if(video.getOnline() != null){
                SET("online=#{online}");
            }
            if(video.getPoint() != null){
                SET("point=#{point}");
            }

            WHERE("id=#{id}");
        }}.toString();

    }
}
