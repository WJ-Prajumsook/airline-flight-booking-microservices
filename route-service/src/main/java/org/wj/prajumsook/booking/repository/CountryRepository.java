package org.wj.prajumsook.booking.repository;

import javax.enterprise.context.ApplicationScoped;

import org.wj.prajumsook.booking.entity.CountryEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<CountryEntity> {
}
