package com.oy.informationmanagement.service;

import com.oy.informationmanagement.entity.NewsComment;
import com.oy.informationmanagement.utils.PageQueryUtil;
import com.oy.informationmanagement.utils.PageResult;

public interface CommentService {
    /**
     * 添加评论
     *
     * @param newsComment
     * @return
     */
    Boolean addComment(NewsComment newsComment);

    /**
     * 后台管理系统中评论分页功能
     *
     * @param pageUtil
     * @return
     */
    PageResult getCommentsPage(PageQueryUtil pageUtil);

    /**
     * 批量审核
     *
     * @param ids
     * @return
     */
    Boolean checkDone(Integer[] ids);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);
}
