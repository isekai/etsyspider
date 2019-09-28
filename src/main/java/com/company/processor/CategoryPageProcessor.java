package com.company.processor;

import com.company.constant.EtsyConstant;
import com.company.entity.Category;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


/**
 * @author doctor
 * @date 2019/9/21
 **/
public class CategoryPageProcessor implements PageProcessor {
    private Site site = Site.me().setDomain(EtsyConstant.DOMAIN)
            .setTimeOut(EtsyConstant.TIME_OUT)
            .setUserAgent(EtsyConstant.USER_AGENT);

    @Override
    public void process(Page page) {
        Element categoryNav = page.getHtml().getDocument().body().getElementById("search-filter-reset-form");
        Elements links = categoryNav.select("a[data-level]");
        List<String> children = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            if ("self".equals(link.attr("data-level"))) {
                Category category = new Category();
                category.setName(link.text());
                category.setUrl(link.attr("href"));
                category.setParent(links.get(i - 1).attr("href"));
                if (link == links.last()) {
                    category.setLeaf(true);
                }
                page.putField(category.getUrl(), category);
            }
            if ("child".equals(link.attr("data-level"))) {
                children.add(link.attr("href"));
            }
        }
        if (links.size() == (children.size() + 1)) {
            Category category = new Category();
            category.setName(links.get(0).text());
            category.setUrl(links.get(0).attr("href"));
            page.putField(category.getUrl(), category);
        }
        page.addTargetRequests(children);

    }

    @Override
    public Site getSite() {
        return site;
    }
}