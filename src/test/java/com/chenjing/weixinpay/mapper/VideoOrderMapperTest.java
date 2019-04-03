package com.chenjing.weixinpay.mapper;

import com.chenjing.weixinpay.domain.VideoOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoOrderMapperTest {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    public void insert() throws Exception {

        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setDel(0);
        videoOrder.setTotalFee(111);
        videoOrder.setHeadImg("gbhdefgf");
        videoOrder.setVideoTitle("SpringBoot高级视频教程");
        videoOrderMapper.insert(videoOrder);

    }

    @Test
    public void findById() throws Exception {

        VideoOrder videoOrder = videoOrderMapper.findById(1);
        assertNotNull(videoOrder);
    }

    @Test
    public void findByOutTradeNo() throws Exception {
    }

    @Test
    public void del() throws Exception {
    }

    @Test
    public void findMyOrderList() throws Exception {
    }

    @Test
    public void updateVideoOrderByOutTradeNo() throws Exception {
    }

}