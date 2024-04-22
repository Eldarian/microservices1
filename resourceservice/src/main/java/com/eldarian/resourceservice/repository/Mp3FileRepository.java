package com.eldarian.resourceservice.repository;

import com.eldarian.resourceservice.model.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Mp3FileRepository extends JpaRepository<Mp3File, Long> {
    Optional<Mp3File> findById(long id);

}
