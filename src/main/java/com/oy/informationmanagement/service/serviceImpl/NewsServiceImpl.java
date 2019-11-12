package com.oy.informationmanagement.service.serviceImpl;

import com.oy.informationmanagement.dao.NewsMapper;
import com.oy.informationmanagement.entity.News;
import com.oy.informationmanagement.service.NewsService;
import com.oy.informationmanagement.utils.PageQueryUtil;
import com.oy.informationmanagement.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public String saveNews(News news) {
        if (newsMapper.insertSelective(news) > 0) {
            return "success";
        }
        return "保存失败";
    }

    @Override
    public PageResult getNewsPage(PageQueryUtil pageUtil) {
        List<News> newsList = newsMapper.findNewsList(pageUtil);
        int total = newsMapper.getTotalNews(pageUtil);
        PageResult pageResult = new PageResult(newsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return newsMapper.deleteBatch(ids)>0;
    }

    @Override
    public News queryNewsById(Long newsId) {
        return newsMapper.selectByPrimaryKey(newsId);
    }

    @Override
    public String updateNews(News news) {
        News newsForUpdate = newsMapper.selectByPrimaryKey(news.getNewsId());
        if (newsForUpdate == null) {
            return "数据不存在";
        }
        news.setNewsCategoryId(news.getNewsCategoryId());
        news.setNewsContent(news.getNewsContent());
        news.setNewsCoverImage(news.getNewsCoverImage());
        news.setNewsStatus(news.getNewsStatus());
        news.setNewsTitle(news.getNewsTitle());
        news.setUpdateTime(new Date());
        if (newsMapper.updateByPrimaryKeySelective(news) > 0) {
            return "success";
        }
        return "修改失败";
    }
}
