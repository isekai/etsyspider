package com.company.processor;

import com.company.constant.EtsyConstant;
import com.company.entity.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * @author doctor
 * @date 2019/9/21
 **/
public class ListPageProcessor implements PageProcessor {
    private Site site = Site.me().setDomain(EtsyConstant.DOMAIN)
            .setTimeOut(EtsyConstant.TIME_OUT)
            .setUserAgent(EtsyConstant.USER_AGENT);
    @Override
    public void process(Page page) {
        Elements links = page.getHtml().getDocument().select("a[data-listing-id]");
        for (Element link : links) {
            Product product = new Product();
            product.setDataId(link.attr("data-listing-id"));
            product.setCategory(page.getUrl().toString());
            product.setUrl(link.attr("href").replaceAll("\\?.*",""));
            page.putField(product.getDataId(), product);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
