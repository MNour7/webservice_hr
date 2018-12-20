package com.example.nour.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nour.model.Location;

@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends CrudRepository<Location, Long> {

}
