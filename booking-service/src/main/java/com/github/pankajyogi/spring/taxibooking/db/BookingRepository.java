package com.github.pankajyogi.spring.taxibooking.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends CrudRepository<BookingDO, String> {
}
