package com.oy.informationmanagement.controller.index;

import com.oy.informationmanagement.entity.News;
import com.oy.informationmanagement.entity.NewsComment;
import com.oy.informationmanagement.service.CommentService;
import com.oy.informationmanagement.service.NewsService;
import com.oy.informationmanagement.utils.AntiXssUtils;
import com.oy.informationmanagement.utils.Result;
import com.oy.informationmanagement.utils.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Resource
    private CommentService commentService;
    @Resource
    private NewsService newsService;

    /**
     * 详情页
     *
     * @return
     */
    @GetMapping({"/news/{newsId}"})
    public String detail(HttpServletRequest request, @PathVariable("newsId") Long newsId) {
        News newsDetail = newsService.queryNewsById(newsId);
        if (newsDetail != null) {
            request.setAttribute("newsDetail", newsDetail);
        }
        request.setAttribute("pageName", "详情");
        return "index/detail";
    }

    /**
     * 评论操作
     */
    @PostMapping(value = "/news/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request, HttpSession session,
                          @RequestParam Long newsId, @RequestParam String verifyCode,
                          @RequestParam String commentator, @RequestParam String commentBody) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (!verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult("验证码错误");
        }
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (null == newsId || newsId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        NewsComment comment = new NewsComment();
        comment.setNewsId(newsId);
        comment.setCommentator(AntiXssUtils.cleanString(commentator));
        comment.setCommentBody(AntiXssUtils.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.addComment(comment));
    }
}
