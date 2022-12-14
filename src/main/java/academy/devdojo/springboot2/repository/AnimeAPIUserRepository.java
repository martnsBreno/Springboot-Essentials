package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.AnimesAPIUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeAPIUserRepository extends JpaRepository<AnimesAPIUser, Long> {

    AnimesAPIUser findByUsername(String name);
}
