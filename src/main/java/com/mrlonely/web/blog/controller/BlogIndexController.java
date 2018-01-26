package com.mrlonely.web.blog.controller;

import com.mrlonely.web.blog.entity.Blog;
import com.mrlonely.web.blog.entity.PageBean;
import com.mrlonely.web.blog.service.BlogService;
import com.mrlonely.web.blog.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BlogIndexController{
    private Logger logger = Logger.getLogger(BlogIndexController.class);

    @Resource
    private BlogService blogService;

    @RequestMapping("/")
    public String login(@RequestParam(value="page",required=false)String page,
                        @RequestParam(value="typeId",required=false)String typeId,
                        @RequestParam(value="releaseDateStr",required=false)String releaseDateStr,
                        HttpServletRequest request){

        if(StringUtils.isEmpty(page)){
            page="1";
        }
        PageBean pageBean=new PageBean(Integer.parseInt(page),10);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<Blog> blogList=blogService.list(map);
        for(Blog blog:blogList){
            List<String> imagesList=blog.getImagesList();
            String blogInfo=blog.getContent();
            Document doc= Jsoup.parse(blogInfo);
            Elements jpgs=doc.select("img[src$=.jpg]"); //　查找扩展名是jpg的图片
            for(int i=0;i<jpgs.size();i++){
                Element jpg=jpgs.get(i);
                imagesList.add(jpg.toString());
                if(i==2){
                    break;
                }
            }
        }
        request.setAttribute("blogList", blogList);
        StringBuffer param=new StringBuffer(); // 查询参数
        if(StringUtils.isNotEmpty(typeId)){
            param.append("typeId="+typeId+"&");
        }
        if(StringUtils.isNotEmpty(releaseDateStr)){
            param.append("releaseDateStr="+releaseDateStr+"&");
        }
        request.setAttribute("pageCode", PageUtil.genPagination(request.getContextPath()+"/", blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
        request.setAttribute("mainPage", "foreground/blog/list.jsp");
        request.setAttribute("pageTitle","BlueBlog");

        return "blog/mainTemp";
    }

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request){

        request.setAttribute("id", "123123123123");


        return "login/login";
    }

}
