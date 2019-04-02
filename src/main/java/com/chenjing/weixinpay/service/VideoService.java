package com.chenjing.weixinpay.service;

import com.chenjing.weixinpay.domain.Video;

import java.util.List;

/**
 * description：VideoService
 *
 * @author:chenjing
 * @version:1.0
 * @time:9:00
 */
/*
* 视频业务类接口
* */
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video video);

    int delete(int id);

    int save(Video video);
}
