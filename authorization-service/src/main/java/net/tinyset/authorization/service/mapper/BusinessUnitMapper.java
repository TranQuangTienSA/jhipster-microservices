package net.tinyset.authorization.service.mapper;

import net.tinyset.authorization.domain.*;
import net.tinyset.authorization.service.dto.BusinessUnitDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BusinessUnit and its DTO BusinessUnitDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, })
public interface BusinessUnitMapper {

    @Mapping(source = "billToAddress.id", target = "billToAddressId")
    @Mapping(source = "billToAddress.street1", target = "billToAddressStreet1")
    @Mapping(source = "shipToAddress.id", target = "shipToAddressId")
    @Mapping(source = "shipToAddress.street1", target = "shipToAddressStreet1")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    BusinessUnitDTO businessUnitToBusinessUnitDTO(BusinessUnit businessUnit);

    List<BusinessUnitDTO> businessUnitsToBusinessUnitDTOs(List<BusinessUnit> businessUnits);

    @Mapping(source = "billToAddressId", target = "billToAddress")
    @Mapping(source = "shipToAddressId", target = "shipToAddress")
    @Mapping(source = "parentId", target = "parent")
    BusinessUnit businessUnitDTOToBusinessUnit(BusinessUnitDTO businessUnitDTO);

    List<BusinessUnit> businessUnitDTOsToBusinessUnits(List<BusinessUnitDTO> businessUnitDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BusinessUnit businessUnitFromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setId(id);
        return businessUnit;
    }
    

}
