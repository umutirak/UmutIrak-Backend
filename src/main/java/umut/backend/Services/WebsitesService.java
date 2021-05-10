package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Entities.Website;
import umut.backend.Mapper.WebsiteMapper;
import umut.backend.ProductParser.HtmlParserFactory;
import umut.backend.Repository.WebsitesRepository;
import umut.backend.Services.Interfaces.IWebsitesService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class WebsitesService implements IWebsitesService {

    private final WebsitesRepository websitesRepository;
    private final WebsiteMapper mapper;

    @Override
    public WebsiteDTO addWebsite(String websiteUrl) throws URISyntaxException {
        var host = new URI(checkForHttp(websiteUrl)).getHost();
        var name = HtmlParserFactory.Website.hostOf(host).name();
        var website = new Website();
        website.setUrl(host);
        website.setName(name);

        return mapper.toDTO(websitesRepository.save(website));
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteByUrl(String websiteUrl) throws URISyntaxException {
        var uri = new URI(checkForHttp(websiteUrl));
        var website = websitesRepository.findByUrl(uri.getHost());
        return mapper.toDTO(website);
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteById(UUID id) {
        var website = websitesRepository.findById(id);
        return website.map(mapper::toDTO).orElse(null);
    }

    @Nullable
    @Override
    public WebsiteDTO getWebsiteByName(String name) {
        var website = websitesRepository.findByName(name);
        return mapper.toDTO(website);
    }

    @Override
    public List<WebsiteDTO> findAllWebsites() {
        return websitesRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private String checkForHttp(String url) {
        return url.startsWith("http") ? url : "https://" + url;
    }
}
