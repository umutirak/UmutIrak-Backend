package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class WebsiteDTO extends BaseDTO {
    UUID id;
    String name;
    String url;
}
