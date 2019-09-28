package com.company.task;

import com.company.constant.EtsyConstant;
import com.company.entity.Category;
import com.company.entity.Product;
import com.company.pipeline.CategoryPipeline;
import com.company.pipeline.ProductPipeline;
import com.company.processor.CategoryPageProcessor;
import com.company.processor.ListPageProcessor;
import com.company.processor.ProductProcessor;
import com.company.service.CategoryService;
import com.company.service.LogService;
import com.company.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @author doctor
 * @date 2019/9/24
 **/
@Component
public class SpiderTask {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;

    @Scheduled(initialDelay = 10, fixedRate = 36000000)
    public void getCategory() {
        Spider.create(new CategoryPageProcessor())
                .addUrl("https://www.etsy.com/c")
                .addPipeline(new CategoryPipeline(categoryService, logService))
                .thread(EtsyConstant.THREAD_NUM).run();
        categoryService.setParent();
    }


    @Scheduled(initialDelay = 3600000, fixedRate = 36000000)
    public void getList() {
        List<Category> categoryList = categoryService.findAllByLeaf();
        String[] urls = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            urls[i] = categoryList.get(i).getUrl();
        }
        Spider.create(new ListPageProcessor())
                .addUrl(urls)
                .addPipeline(new ProductPipeline(productService, logService))
                .thread(EtsyConstant.THREAD_NUM).run();
    }

    @Scheduled(initialDelay = 7200000, fixedRate = 36000000)
    public void getProduct() {
        List<Product> productList = productService.findAllByModificationTimeNull();
        String[] urls = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            urls[i] = productList.get(i).getUrl();
        }
        Spider.create(new ProductProcessor())
                .addUrl(urls)
                .addPipeline(new ProductPipeline(productService, logService))
                .thread(EtsyConstant.THREAD_NUM).run();

    }

}
