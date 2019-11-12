package com.oy.informationmanagement.service;

import com.oy.informationmanagement.entity.News;
import com.oy.informationmanagement.utils.PageQueryUtil;
import com.oy.informationmanagement.utils.PageResult;

public interface NewsService {
    String saveNews(News news);

    PageResult getNewsPage(PageQueryUtil pageUtil);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 根据id获取详情
     *
     * @param newsId
     * @return
     */
    News queryNewsById(Long newsId);

    /**
     * 后台修改
     *
     * @param news
     * @return
     */
    String updateNews(News news);
}
