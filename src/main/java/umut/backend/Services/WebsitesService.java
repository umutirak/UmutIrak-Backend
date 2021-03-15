package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Entities.Website;
import umut.backend.Mapper.WebsiteMapper;
import umut.backend.Repository.WebsitesRepository;
import umut.backend.Services.Interfaces.IWebsitesService;
import umut.backend.Util.Parser.HtmlParserFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class WebsitesService implements IWebsitesService {

    private final WebsitesRepository websitesRepository;
    private final WebsiteMapper mapper;

    @Override
    public WebsiteDTO addWebsite(String websiteUrl) throws URISyntaxException {
        var host = new URI(websiteUrl).getHost();
        var name = HtmlParserFactory.Website.hostOf(host).name();
        var website = new Website();
        website.setUrl(host);
        website.setName(name);

        return mapper.fromWebsite(websitesRepository.save(website));
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteByUrl(String websiteUrl) throws URISyntaxException {
        var uri = new URI(websiteUrl);
        var website = websitesRepository.findByUrl(uri.getHost());
        return mapper.fromWebsite(website);
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteById(UUID id) {
        var website = websitesRepository.findById(id);
        return website.map(mapper::fromWebsite).orElse(null);
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteByName(String name) {
        var website = websitesRepository.findByName(name);
        return mapper.fromWebsite(website);
    }
}
