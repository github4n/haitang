package com.bmn.haitang.demo.pig.service.impl;

import com.bmn.haitang.demo.pig.service.AuthorityService;
import com.bmn.haitang.demo.pig.service.ExplorerService;
import com.bmn.haitang.demo.pig.vo.ui.UIAccordion;
import com.bmn.haitang.demo.pig.vo.ui.UIBranch;
import com.bmn.haitang.demo.pig.vo.ui.UIComponent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private ExplorerService explorerService;

    @Override
    public List<UIComponent> getExplorer(long roleId) throws CloneNotSupportedException {
        List<UIAccordion> list = explorerService.getAccordions();

        List<Integer> authorities = new ArrayList<>();
        authorities.add(100102);
        authorities.add(10000000);
        authorities.add(30010100);
        authorities.add(200);
        authorities.add(300100);
        authorities.add(300101);
        authorities.add(300102);

        return findBranch(authorities, list, new UIComponent.UIComponentFilter() {
            @Override
            public void doFilter(List<UIComponent> components) {
                Iterator<UIComponent> it = components.iterator();
                while (it.hasNext()) {
                    UIComponent component = it.next();
                    if (component instanceof UIBranch) {
                        UIBranch branch = (UIBranch) component;
                        if (branch.getType().equals("btn")) {
                            it.remove();
                        }
                    }
                }
            }
        });
    }

    /**
     * 根据权限id列表，返回权限树
     *
     * @param authorities 权限id列表
     * @param children 原树
     * @return 权限树
     */
    private List<UIComponent> findBranch(List<Integer> authorities,
        List<? extends UIComponent> children, UIComponent.UIComponentFilter filter)
        throws CloneNotSupportedException {
        if (children.isEmpty()) {
            return null;
        }

        List<UIComponent> result = new ArrayList<>();
        for (UIComponent child : children) {
            if (authorities.contains(child.getId())) {
                UIComponent cl = child.clone();
                cl.getChildren().clear();
                result.add(cl);
            } else {
                List<UIComponent> branches = findBranch(authorities, child.getChildren(), filter);
                if (branches != null && !branches.isEmpty()) {
                    UIComponent cl = child.clone();
                    cl.setChildren(branches);
                    cl.filter(filter);
                    result.add(cl);
                }
            }
        }
        return result;
    }
}
