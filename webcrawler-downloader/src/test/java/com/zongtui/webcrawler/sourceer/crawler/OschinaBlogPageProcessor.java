package com.zongtui.webcrawler.sourceer.crawler;

import java.util.List;

import com.zongtui.webcrawler.sourceer.Page;
import com.zongtui.webcrawler.sourceer.ResultItems;
import com.zongtui.webcrawler.sourceer.Site;
import com.zongtui.webcrawler.sourceer.Spider;
import com.zongtui.webcrawler.sourceer.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 */
public class OschinaBlogPageProcessor implements PageProcessor {

    private Site site = Site.me().setDomain("my.oschina.net");

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("https://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='blog-content']/div[@class='blog-heading']/div[@class='title']/text()").toString());
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
    	Spider spider = Spider.create(new OschinaBlogPageProcessor());
    	//ResultItems resultItems = spider.<ResultItems>get("https://my.oschina.net/flashsword/blog");
        //System.out.println(resultItems);
    	spider.addUrl("https://my.oschina.net/flashsword/blog").run();
    }
}
