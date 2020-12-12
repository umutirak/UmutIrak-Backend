package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryDTO {
    private UUID id;
    private String name;
    private String url;
    private String subPath;
}
