package com.oy.informationmanagement.dao;

import com.oy.informationmanagement.entity.News;
import com.oy.informationmanagement.utils.PageQueryUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsMapper {
    int deleteByPrimaryKey(Long newsId);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long newsId);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    List<News> findNewsList(PageQueryUtil pageUtil);

    int getTotalNews(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);
}
