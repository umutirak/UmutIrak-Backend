package umut.backend.Mapper;

import org.mapstruct.Mapper;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Entities.Website;

@Mapper(componentModel = "spring")
public interface WebsiteMapper extends BaseMapper<WebsiteDTO, Website> {
}
