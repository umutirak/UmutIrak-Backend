package umut.backend.Services.Interfaces;

import umut.backend.DTOs.WebsiteDTO;

import java.net.URISyntaxException;
import java.util.UUID;

public interface IWebsitesService {
    WebsiteDTO addWebsite(String websiteUrl) throws URISyntaxException;

    WebsiteDTO getWebsiteByUrl(String websiteUrl) throws URISyntaxException;

    WebsiteDTO getWebsiteById(UUID id);

    WebsiteDTO getWebsiteByName(String name);
}
