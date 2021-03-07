package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Requests.RequestAddWebsite;
import umut.backend.Services.Interfaces.IWebsitesService;

@AllArgsConstructor
@RequestMapping("/website")
@CrossOrigin
@RestController
public class WebsiteController {

    private final IWebsitesService websitesService;

    @PostMapping("/add")
    public ResponseEntity<Void> addWebsite(@RequestBody RequestAddWebsite request) {
        var dto = new WebsiteDTO();
        dto.setName(request.getName());
        dto.setUrl(request.getUrl());
        websitesService.addWebsite(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
