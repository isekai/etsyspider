package com.company.pipeline;

import com.company.constant.LogType;
import com.company.entity.Category;
import com.company.entity.Log;
import com.company.service.CategoryService;
import com.company.service.LogService;
import lombok.Setter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author doctor
 * @date 2019/9/21
 **/
public class CategoryPipeline implements Pipeline {
    @Setter
    private CategoryService categoryService;
    @Setter
    private LogService logService;

    public CategoryPipeline(CategoryService categoryService, LogService logService) {
        this.categoryService = categoryService;
        this.logService = logService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry entry : resultItems.getAll().entrySet()) {
            Category category = (Category) entry.getValue();
            try {
                categoryService.saveCategory(category);
            } catch (Exception e) {
                Log log = new Log();
                log.setUrl(category.getUrl());
                log.setType(LogType.CATEGORY_LOG.getCode());
                log.setMessage(e.getMessage());
                logService.save(log);
            }
        }
    }
}
