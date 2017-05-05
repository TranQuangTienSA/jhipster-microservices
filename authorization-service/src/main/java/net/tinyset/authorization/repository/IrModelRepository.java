package net.tinyset.authorization.repository;

import net.tinyset.authorization.domain.IrModel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IrModel entity.
 */
@SuppressWarnings("unused")
public interface IrModelRepository extends JpaRepository<IrModel,Long> {

}
