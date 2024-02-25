package com.nvelich.newsnba.repository;

import com.nvelich.newsnba.models.Hooper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HooperRepository extends JpaRepository<Hooper,Long> {

    Hooper findByName(String Name);
}
