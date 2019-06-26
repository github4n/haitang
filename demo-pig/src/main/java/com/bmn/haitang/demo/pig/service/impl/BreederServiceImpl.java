package com.bmn.haitang.demo.pig.service.impl;

import com.alibaba.fastjson.JSON;
import com.bmn.haitang.demo.pig.service.BreederService;
import com.bmn.haitang.demo.pig.vo.BreederVO;
import com.bmn.haitang.demo.pig.vo.PageVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/26
 */
@Service
public class BreederServiceImpl implements BreederService {

    @Override
    public String grid() {
        BreederVO breederVO = new BreederVO();
        breederVO.setId("123");
        breederVO.setName("1830922922");
        breederVO.setIcon("image");
        breederVO.setContractGain("10");
        breederVO.setDogCoin("100");
        breederVO.setLevel("10");
        breederVO.setNour("199");
        breederVO.setOnlineState("在线");
        breederVO.setPromotionBenefit("333");
        breederVO.setTotalGain("1000");
        breederVO.setScore("99");

        List<BreederVO> rows = new ArrayList<>();
        rows.add(breederVO);

        PageVO vo = new PageVO();
        vo.setTotal(1);
        vo.setRows(rows);

        return JSON.toJSONString(vo);
    }
}
