package net.tinyset.authorization.repository;

import net.tinyset.authorization.domain.BusinessUnit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BusinessUnit entity.
 */
@SuppressWarnings("unused")
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit,Long> {

}
