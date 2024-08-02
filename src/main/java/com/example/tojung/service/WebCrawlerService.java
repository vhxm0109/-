package com.example.tojung.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebCrawlerService {

    public String getElementsByClasses(String url, List<String> classNames) throws IOException {
        // URL에 접속하여 문서를 가져옵니다.
        Document document = Jsoup.connect(url).get();

        // 모든 클래스 선택자를 조합합니다.
        String cssQuery = classNames.stream()
                .map(className -> "." + className)
                .collect(Collectors.joining(", "));

        // 여러 클래스의 요소들을 선택합니다.
        Elements elements = document.select(cssQuery); // 모든 요소 선택
        return elements.stream()
                .map(Element::html)
                .collect(Collectors.joining("<br>")); // 각 요소를 HTML 문자열로 변환하고, <br>로 구분하여 연결
    }
}