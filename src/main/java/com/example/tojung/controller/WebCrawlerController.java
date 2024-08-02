package com.example.tojung.controller;

import com.example.tojung.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    private static final String DEFAULT_URL = "https://shinhanlife.sinbiun.com/unse/saju/saju.php?unse_code=A104"; // 자동으로 크롤링할 URL
    private static final List<String> TARGET_CLASSES = Arrays.asList("unse_info", "inp_area", "btn_area"); // 크롤링할 클래스 이름들

    @GetMapping("/")
    public String crawl(Model model) {
        try {
            String elementsHtml = webCrawlerService.getElementsByClasses(DEFAULT_URL, TARGET_CLASSES);

            // HTML 파일로 저장
            try (FileWriter fileWriter = new FileWriter("crawled_page.html")) {
                fileWriter.write(elementsHtml);
            }

            // 모델에 URL 및 HTML 추가
            //model.addAttribute("url", DEFAULT_URL);
            model.addAttribute("htmlContent", elementsHtml);

            return "results";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error occurred while fetching data.");
            return "error";
        }
    }
}