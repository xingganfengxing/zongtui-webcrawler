package com.zongtui.webcrawler.sourceer.crawler;

import java.util.List;

import com.zongtui.webcrawler.sourceer.Page;
import com.zongtui.webcrawler.sourceer.Site;
import com.zongtui.webcrawler.sourceer.Spider;
import com.zongtui.webcrawler.sourceer.processor.PageProcessor;

public class VodPageProcessor implements PageProcessor{

    private Site site = Site.me().setDomain("vod.cnzol.com");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().xpath("//[@id='list']/dl/dt/a[@class='img']/@href").regex("http://vod\\.cnzol\\.com/html/dongzuo/\\d+/\\d+\\.html").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//[@id='show']/h3/text()").toString());
        if (page.getResultItems().get("title") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("content", page.getHtml().smartContent().toString());
        page.putField("tags", page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
    	Spider spider = Spider.create(new VodPageProcessor());
    	//ResultItems resultItems = spider.<ResultItems>get("https://my.oschina.net/flashsword/blog");
        //System.out.println(resultItems);
    	spider.addUrl("http://vod.cnzol.com/html/dongzuo/").run();
    }
}
