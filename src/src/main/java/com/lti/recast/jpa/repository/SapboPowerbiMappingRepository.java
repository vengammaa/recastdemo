package com.lti.recast.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lti.recast.jpa.entity.SapboPowerbiMapping;

@Repository
public interface SapboPowerbiMappingRepository extends JpaRepository<SapboPowerbiMapping,Integer> {
	SapboPowerbiMapping findBySapboComponent(String sapboComponent);
}
