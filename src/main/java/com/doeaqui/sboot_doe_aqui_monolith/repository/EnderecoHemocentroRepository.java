package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.EnderecoHemocentro;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoHemocentroRepository extends MongoRepository<EnderecoHemocentro, Integer> {
    List<EnderecoHemocentro> findByGeoLocationNear(Point userLocation, Distance distance);
}