package com.bmn.haitang.demo.pig.service;

import com.bmn.haitang.demo.pig.vo.ui.UIComponent;
import java.util.List;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/20
 */
public interface AuthorityService {

    List<UIComponent> getExplorer(long roleId) throws CloneNotSupportedException;

}
