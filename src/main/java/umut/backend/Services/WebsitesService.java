package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.WebsitesRepository;
import umut.backend.Services.Interfaces.IWebsitesService;

@Service
@AllArgsConstructor
public class WebsitesService implements IWebsitesService {

    private final WebsitesRepository websitesRepository;
    private final AutoMapper mapper;

    @Override
    public void addWebsite(WebsiteDTO websiteDTO) {
        websitesRepository.save(mapper.toWebsite(websiteDTO));
    }

    @Nullable
    @Override
    public WebsiteDTO findWebsiteByUrl(String url) {
        var website = websitesRepository.findByUrl(url);
        if (website == null)
            return null;

        return mapper.toWebsiteDTO(website);
    }
}
