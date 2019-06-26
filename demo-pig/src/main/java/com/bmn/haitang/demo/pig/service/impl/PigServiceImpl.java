package com.bmn.haitang.demo.pig.service.impl;

import com.alibaba.fastjson.JSON;
import com.bmn.haitang.demo.pig.service.PigService;
import com.bmn.haitang.demo.pig.vo.PageVO;
import com.bmn.haitang.demo.pig.vo.PigInfoVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/26
 */
@Service
public class PigServiceImpl implements PigService {

    @Override
    public String grid() {

        PigInfoVO vo = new PigInfoVO();
        vo.setId("123");
        vo.setType("普通");
        vo.setIcon("");
        vo.setNour("0.5");
        vo.setAdoptTime("10:00-11:00");
        vo.setPrice("10");
        vo.setProfit("0.2%");
        vo.setScore("100");

        List<PigInfoVO> rows = new ArrayList<>();
        rows.add(vo);

        PageVO page = new PageVO();

        page.setTotal(1);
        page.setRows(rows);

        return JSON.toJSONString(page);
    }
}
