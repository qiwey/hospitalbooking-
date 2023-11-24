package com.example.project.admin.homepage;

import com.example.project.util.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class HomePageServiceImpl implements HomePageService {

    private final HomePageRepository homePageRepository;
    private final StorageService storageService;
    private final ModelMapper mapper;

    @Override
    public HomePageInfo updateHomePage(HomePageInfo updateHomePage, MultipartFile logo, MultipartFile aboutImage) {
        HomePageInfo homePageInfo = homePageRepository.findTopBy();
        if (logo != null && !logo.isEmpty()) {
            String oldLogo = homePageInfo.getLogoUrl();
            updateHomePage.setLogoUrl(storageService.store(logo));
            storageService.delete(oldLogo);
        }
        if (aboutImage != null && !aboutImage.isEmpty()) {
            String oldAboutUsImg = homePageInfo.getAboutUsImg();
            updateHomePage.setAboutUsImg(storageService.store(aboutImage));
            storageService.delete(oldAboutUsImg);
        }
        mapper.map(updateHomePage, homePageInfo);
        return homePageRepository.save(homePageInfo);
    }

    @Override
    public HomePageInfo getHomePage() {
        return homePageRepository.findTopBy();
    }
}
