package com.oy.informationmanagement.service.serviceImpl;

import com.oy.informationmanagement.dao.NewsCategoryMapper;
import com.oy.informationmanagement.entity.NewsCategory;
import com.oy.informationmanagement.service.CategoryService;
import com.oy.informationmanagement.utils.PageQueryUtil;
import com.oy.informationmanagement.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public List<NewsCategory> getAllCategories() {
        return newsCategoryMapper.findCategoryList(null);
    }

    @Override
    public NewsCategory queryById(Long id) {
        return newsCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult getCategoryPage(PageQueryUtil pageUtil) {
        List<NewsCategory> categoryList = newsCategoryMapper.findCategoryList(pageUtil);
        int total = newsCategoryMapper.getTotalCategories(pageUtil);
        PageResult pageResult = new PageResult(categoryList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean saveCategory(String categoryName) {
        /**
         * 查询是否已存在
         */
        NewsCategory temp = newsCategoryMapper.selectByCategoryName(categoryName);
        if (temp == null) {
            NewsCategory newsCategory = new NewsCategory();
            newsCategory.setCategoryName(categoryName);
            return newsCategoryMapper.insertSelective(newsCategory) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateCategory(Long categoryId, String categoryName) {
        NewsCategory newsCategory = newsCategoryMapper.selectByPrimaryKey(categoryId);
        if (newsCategory != null) {
            newsCategory.setCategoryName(categoryName);
            return newsCategoryMapper.updateByPrimaryKeySelective(newsCategory) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatchByIds(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除分类数据
        return newsCategoryMapper.deleteBatch(ids) > 0;
    }

}
