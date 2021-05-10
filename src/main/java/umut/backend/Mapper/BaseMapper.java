package umut.backend.Mapper;

import org.mapstruct.Named;
import umut.backend.DTOs.BaseDTO;
import umut.backend.Entities.BaseEntity;

public interface BaseMapper<T extends BaseDTO, Y extends BaseEntity> {

    @Named(value = "defaultToDto")
    T toDTO(Y model);

    @Named(value = "defaultToEntity")
    Y toEntity(T dto);
}
