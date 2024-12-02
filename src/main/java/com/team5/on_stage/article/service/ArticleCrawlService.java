package com.team5.on_stage.article.service;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCrawlService {

    public List<ArticleRequestDTO> crawlArticles(String keyword) {
        List<ArticleRequestDTO> articles = new ArrayList<>();
        String url = "https://m.search.naver.com/search.naver?ssc=tab.m_news.all&where=m_news&sm=tab_jum&query=" + keyword + "신곡";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements newsArticles = doc.select("ul.list_news > li");

            for (Element article : newsArticles) {
                String title = article.select("a.news_tit").text();
                String link = article.select("a.news_tit").attr("href");
                String content = article.select("div.news_dsc").text();

                ArticleRequestDTO dto = new ArticleRequestDTO();
                dto.setTitle(title);
                dto.setLink(link);
                dto.setContent(content);
                articles.add(dto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }
}