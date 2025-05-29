package com.mitra.koptani.repository;

import com.mitra.koptani.entity.Mitra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MitraRepository extends JpaRepository<Mitra, Integer> {

    List<Mitra> findAllByDeletedAtIsNull();

    Optional<Mitra> findAllByIdAndDeletedAtIsNull(Integer id);
}
