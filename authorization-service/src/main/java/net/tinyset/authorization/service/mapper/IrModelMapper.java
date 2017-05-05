package net.tinyset.authorization.service.mapper;

import net.tinyset.authorization.domain.*;
import net.tinyset.authorization.service.dto.IrModelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity IrModel and its DTO IrModelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IrModelMapper {

    IrModelDTO irModelToIrModelDTO(IrModel irModel);

    List<IrModelDTO> irModelsToIrModelDTOs(List<IrModel> irModels);

    IrModel irModelDTOToIrModel(IrModelDTO irModelDTO);

    List<IrModel> irModelDTOsToIrModels(List<IrModelDTO> irModelDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default IrModel irModelFromId(Long id) {
        if (id == null) {
            return null;
        }
        IrModel irModel = new IrModel();
        irModel.setId(id);
        return irModel;
    }
    

}
