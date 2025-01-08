package com.stephenowino.Rented_Art_Backend.Repository;


import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Long> {
        Artist findByUsername(String username);

}


