package com.company;

import com.company.entity.Category;
import com.company.entity.Product;
import com.company.processor.CategoryPageProcessor;
import com.company.processor.ListPageProcessor;
import com.company.processor.ProductProcessor;
import com.company.service.CategoryService;
import com.company.service.LogService;
import com.company.service.ProductService;
import com.company.webmagic.http.HttpClientDownloader;
import com.company.pipeline.CategoryPipeline;
import com.company.pipeline.ProductPipeline;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.HashMap;
import java.util.List;

/**
 * @author doctor
 * @date 2019/9/21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EtsyTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;
    private HttpClientDownloader httpClientDownloader;

    @Before
    public void setUp() {
        httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("localhost", 8000)));
    }

    @Test
    public void testCategory() {
        Spider.create(new CategoryPageProcessor()).setDownloader(httpClientDownloader).addUrl("https://www.etsy.com/c")
                .addPipeline(new CategoryPipeline(categoryService, logService)).run();
    }

    @Test
    public void testList() {
        List<Category> categoryList = categoryService.findAllByLeaf();
        String[] urls = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            urls[i] = categoryList.get(i).getUrl();
        }
        Spider.create(new ListPageProcessor()).setDownloader(httpClientDownloader)
                .addUrl(urls)
                .addPipeline(new ProductPipeline(productService, logService)).run();
    }

    @Test
    public void testProduct() {
        List<Product> productList = productService.findAllByModificationTimeNull();
        String[] urls = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            urls[i] = productList.get(i).getUrl();
        }
        Spider.create(new ProductProcessor()).setDownloader(httpClientDownloader).addUrl(urls)
                .thread(8).run();
    }

    @Test
    public void testSetParent() {
        categoryService.setParent();
    }
}
