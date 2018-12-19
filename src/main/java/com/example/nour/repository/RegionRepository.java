package com.example.nour.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nour.model.Region;

@RepositoryRestResource(collectionResourceRel = "region", path = "region")
public interface RegionRepository extends CrudRepository<Region, Long> {

}
