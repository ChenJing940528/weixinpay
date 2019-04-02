package com.chenjing.weixinpay.service.impl;

import com.chenjing.weixinpay.domain.Video;
import com.chenjing.weixinpay.mapper.VideoMapper;
import com.chenjing.weixinpay.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description：VideoServiceImpl
 *
 * @author:chenjing
 * @version:1.0
 * @time:9:01
 */
@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video video) {
       return videoMapper.update(video);
    }

    @Override
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {

        int rows = videoMapper.save(video);
        System.out.println("保存对象的id="+video.getId());
        //通过这种方式取不到自增的id，只有VideoMapper.java中注解的方式才能取到自增的id

        return rows;
    }
}
