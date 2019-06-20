package com.bmn.haitang.demo.pig.service.impl;

import com.bmn.haitang.demo.pig.service.ExplorerService;
import com.bmn.haitang.demo.pig.vo.ui.UIAccordion;
import com.bmn.haitang.demo.pig.vo.ui.UIBranch;
import com.bmn.haitang.demo.pig.vo.ui.UIComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/20
 */

@Service
public class ExplorerServiceImpl implements ExplorerService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ExplorerServiceImpl.class);
    private Map<Integer, UIAccordion> accordions = new HashMap<>();


    @Value("${explorer.file.path}")
    private String filePath;

    @Override
    public List<UIAccordion> getAccordions() {
        List<UIAccordion> result = new ArrayList<>();
        result.addAll(accordions.values());
        Collections.sort(result);
        return Collections.unmodifiableList(result);
    }


    private void loadExplorer() throws ConfigurationException {
        XMLConfiguration conf = new XMLConfiguration();
        conf.setFileName(filePath);
        conf.setDelimiterParsingDisabled(true);     //取消内容自动分隔
        conf.load();

        List<HierarchicalConfiguration> accordionConfig = conf.configurationsAt("accordions.accordion");
        if(accordions == null) {
            throw new NullPointerException("explorer config error: need accordion");
        }

        Map<Integer, UIAccordion> accordionMap = new HashMap<>();
        for(HierarchicalConfiguration sub : accordionConfig) {
            UIAccordion accordion = new UIAccordion(sub.getInt("[@id]"));
            accordion.setOrder(sub.getInt("[@order]"));
            accordion.setTitle(sub.getString("[@title]"));
            accordion.setSelected(sub.getBoolean("[@selected]"));
            accordion.setContent(sub.getString("content"));
            accordion.setContentId(sub.getString("content.[@id]"));
            accordion.setContentType(sub.getString("content.[@type]"));
            String type = sub.getString("[@type]");
            if(type != null) {
                accordion.setType(type);
            }
            accordion.setChildren(loadBranch(sub, accordion.getId()));

            accordionMap.put(accordion.getId(), accordion);
        }

        accordions = accordionMap;
    }

    private List<UIComponent> loadBranch(HierarchicalConfiguration config, int parentId) {
        List<UIComponent> branchList = new ArrayList<>();
        List<HierarchicalConfiguration> branches = config.configurationsAt("children.branch");
        for (HierarchicalConfiguration sub : branches) {
            UIBranch branch = new UIBranch();
            branch.setId(sub.getInt("[@id]"));
//            branch.setParentId(parentId);
            branch.setText(sub.getString("[@text]"));
            String url = sub.getString("[@url]");
            if(url != null) {
                branch.setAttributeUrl(url);
            }
            String type = sub.getString("[@type]");
            if(type != null) {
                branch.setAttributeType(type);
            }
            branch.setChildren(loadBranch(sub, branch.getId()));

            branchList.add(branch);
        }

        return branchList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadExplorer();
    }
}
