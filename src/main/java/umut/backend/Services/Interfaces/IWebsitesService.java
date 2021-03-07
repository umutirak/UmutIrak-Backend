package umut.backend.Services.Interfaces;

import umut.backend.DTOs.WebsiteDTO;

public interface IWebsitesService {
    void addWebsite(WebsiteDTO websiteDTO);

    WebsiteDTO findWebsiteByUrl(String url);
}
