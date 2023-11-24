package com.example.project.admin.homepage;

import org.springframework.web.multipart.MultipartFile;

public interface HomePageService {

    HomePageInfo updateHomePage(HomePageInfo homePageInfo, MultipartFile logo, MultipartFile aboutImage);

    HomePageInfo getHomePage();


}
