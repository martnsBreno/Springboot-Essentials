package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.AnimeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Springboot Essentials course")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save anime when sucessfull")
    void save_savesAnime_WhenSucessfull() {
        boolean isIdEmptyOrNull = false;
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime save = this.animeRepository.save(anime);
        Assertions.assertEquals("Pokemon", save.getName());
        if (save.getId() == 0 || save.getId() == null) {
            isIdEmptyOrNull = true;
        }
        Assertions.assertEquals(false, isIdEmptyOrNull);

    }


    @Test
    @DisplayName("Update anime when sucessfull")
    void update_updatesAnime_WhenSucessfull() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        anime.setName("FullMetal Alchemist");
        Anime animeUpdated = this.animeRepository.save(anime);
        Assertions.assertEquals("FullMetal Alchemist", animeUpdated.getName());

    }

    @Test
    @DisplayName("Delete anime when sucessfull")
    void delete_updatesAnime_WhenSucessfull() {
        boolean isAnimeEmpty = false;
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(anime);
        animeRepository.delete(animeSaved);
        Optional<Anime> isDeleted = this.animeRepository.findById(animeSaved.getId());
        if (isDeleted.isEmpty()) {
            isAnimeEmpty = true;
        }
        Assertions.assertEquals(true, isAnimeEmpty);
    }


    @Test
    @DisplayName("Returns a list of animes when sucesfull")
    void findByName_returnsAlistOfAnime_WhenSucessfull() {
        boolean isListEmpty = true;
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(anime);

        List<Anime> animes = animeRepository.findByName(animeSaved.getName());

        if(!animes.isEmpty()) {
            isListEmpty = false;
        }

        Assertions.assertEquals(false, isListEmpty);
    }



    @Test
    @DisplayName("Empty list when no anime is found")
    void findByName_returnsEmptyIfNoAnimeIsFound_WhenSucessfull() {
        boolean isListEmpty = false;
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(anime);

        animeRepository.delete(animeSaved);

        List<Anime> animes = animeRepository.findByName(animeSaved.getName());

        if(animes.isEmpty()) {
            isListEmpty = true;
        }

        Assertions.assertEquals(true, isListEmpty);
    }

}