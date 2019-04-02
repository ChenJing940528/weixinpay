package com.chenjing.weixinpay.controller.admin;

import com.chenjing.weixinpay.domain.Video;
import com.chenjing.weixinpay.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description：VideoAdminController
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:43
 */
@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {


    @Autowired
    private VideoService videoService;

    /*
   * 根据id删除视频
   * @param videoId
   * */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id", required = true) int videoId){

        return videoService.delete(videoId);
    }

    /*
    * 根据id更新视频
    * @param @RequestBody Video video直接修改整改对象
    * @RequestBody 请求体映射实体类
    *    需要指定http头为 content-type为application/json charset=utf-8
    *    在postman中需要在Body的raw中选择Json格式，就会在Headers添加content-type=application/json
    * */
    @PutMapping("update_by_id")
    public Object update(@RequestBody Video video){

        return videoService.update(video);
    }

    /*
    * 保存视频对象
    * @param video
    * */
    @PostMapping("save")
    public Object save(@RequestBody Video video){

        return videoService.save(video);
    }
}
