package org.qza.gft.crw.store.data.repo.gift;

import org.qza.gft.crw.store.data.entity.Gift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author gft
 */
public interface GiftRepository extends CrudRepository<Gift, Long> {

	Page<Gift> findAll(Pageable page);

}
