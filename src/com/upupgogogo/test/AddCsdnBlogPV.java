package com.upupgogogo.test;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by upupgogogo on 2018/3/27.下午3:53
 */
public class AddCsdnBlogPV
{
    private String csdnBlogUrl = "http://blog.csdn.net/";
    private String firstBlogListPageUrl = "https://blog.csdn.net/qq_34780061/";               //博客主页
    private String nextPagePanner = "<a href=\"qq_34780061/article/list/[0-9]{1,10}\">下一页</a>";    //下一页的正则表达式
    private String nextPageUrlPanner = "qq_34780061/article/list/[0-9]{1,10}";             //下一页Url的正则表达式
    private String artlUrl = "qq_34780061/article/details/[0-9]{8,8}";                 //博客utl的正则表达式

    private Set<String> blogListPageUrls = new TreeSet<>();
    private Set<String> blogUrls = new TreeSet<>();

    @Test
    public void visitBlog() throws IOException {
        addBlogUrl();
        for(String blogUrl : blogUrls) {
            String artlUrl = csdnBlogUrl + blogUrl;
            InputStream is = HttpUtil.doGet(artlUrl);
            if (is != null) {
                System.out.println(artlUrl + "访问成功");
            }
            is.close();
        }
    }

    /**
     * @throws IOException
     * 加载所有的bolg地址
     */
    @Test
    public void addBlogUrl() throws IOException {
        blogListPageUrls.add(firstBlogListPageUrl);
        addBlogListPageUrl(firstBlogListPageUrl, blogListPageUrls);
        for (String bolgListUrl : blogListPageUrls) {
            addBlogUrl(bolgListUrl, blogUrls);
        }
    }

    /**
     * 通过下一页，遍历所有博客目录页面链接
     * @param pageUrl
     * @param pagelistUrls
     * @throws IOException
     */
    public void addBlogListPageUrl(String pageUrl, Set<String> pagelistUrls) throws IOException {
        InputStream is = HttpUtil.doGet(pageUrl);
        String pageStr = StreamUtil.inputStreamToString(is, "UTF-8");
        is.close();
        Pattern nextPagePattern = Pattern.compile(nextPagePanner);
        Matcher nextPagematcher = nextPagePattern.matcher(pageStr);
        if (nextPagematcher.find()) {
            nextPagePattern = Pattern.compile(nextPageUrlPanner);
            nextPagematcher = nextPagePattern.matcher(nextPagematcher.group(0));
            if (nextPagematcher.find()) {
                pagelistUrls.add(csdnBlogUrl + nextPagematcher.group(0));
                System.out.println("成功添加博客列表页面地址：" + csdnBlogUrl + nextPagematcher.group(0));
                addBlogListPageUrl(csdnBlogUrl + nextPagematcher.group(0), pagelistUrls);
            }
        }
    }

    /**
     * 添加搜索博客目录的博客链接
     * @param blogListURL 博客目录地址
     * @param artlUrls    存放博客访问地址的集合
     * @throws IOException
     */
    public void addBlogUrl(String blogListURL, Set<String> artlUrls) throws IOException {
        InputStream is = HttpUtil.doGet(blogListURL);
        String pageStr = StreamUtil.inputStreamToString(is, "UTF-8");
        is.close();
        Pattern pattern = Pattern.compile(artlUrl);
        Matcher matcher = pattern.matcher(pageStr);
        while (matcher.find()) {
            String e = matcher.group(0);
            System.out.println("成功添加博客地址：" + e);
            artlUrls.add(e);
        }
    }
}
