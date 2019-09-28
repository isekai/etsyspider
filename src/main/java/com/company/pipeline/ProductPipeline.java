package com.company.pipeline;

import com.company.constant.LogType;
import com.company.entity.Log;
import com.company.entity.Product;
import com.company.service.LogService;
import com.company.service.ProductService;
import lombok.Setter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author doctor
 * @date 2019/9/22
 **/
public class ProductPipeline implements Pipeline {
    @Setter
    private ProductService productService;
    @Setter
    private LogService logService;

    public ProductPipeline(ProductService productService, LogService logService) {
        this.productService = productService;
        this.logService = logService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry entry : resultItems.getAll().entrySet()) {
            Product product = (Product) entry.getValue();
            try {
                productService.saveProduct(product);
            } catch (Exception e) {
                Log log = new Log();
                log.setUrl(product.getUrl());
                log.setType(LogType.CATEGORY_LOG.getCode());
                log.setMessage(e.getMessage());
                logService.save(log);
            }
        }
    }
}
