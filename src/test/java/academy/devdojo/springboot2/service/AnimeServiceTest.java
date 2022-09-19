package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.Exception.BadRequestException;
import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findAll(any(PageRequest.class))).thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(anyString())).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(any(Anime.class))).thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(any(Anime.class));
    }

    @Test
    @DisplayName("List Returns list of anime inside page object when successfull")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("Returns a List of Animes when successfull")
    void listAll_ReturnsListOfAnime_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesList = animeService.listAllNonPageable();

        Assertions.assertThat(animesList).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animesList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find by id return an anime when successfull")
    void findById_ReturnsAnime_WhenSuccessfull() {
        Anime Anime = AnimeCreator.createValidAnime();

        Anime animeFoundWithId = animeService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(animeFoundWithId.getId()).isNotNull().isEqualTo(Anime.getId());
    }

    @Test
    @DisplayName("Find by id Throws BadRequestException when anime not found")
    void findById_ThrowsBadRequestException_WhenAnimeIsNotFound() {

        BDDMockito.when(animeRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));

    }


    @Test
    @DisplayName("findByName returns a list of anime when successfull")
    void findByName_ReturnsListOfAnime_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animePage = animeService.findByName("anime");

        Assertions.assertThat(animePage).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when successfull")
    void findByName_ReturnsAnEmptyListOfAnime_WhenSuccessfull() {

        BDDMockito.when(animeRepositoryMock.findByName(anyString())).thenReturn(Collections.emptyList());

        String animeCreated = AnimeCreator.createValidAnime().getName();
        List<Anime> animePage = animeService.findByName("");

        Assertions.assertThat(animePage).isEmpty();
    }

    @Test
    @DisplayName("Save returns an anime when successfull")
    void save_ReturnsAnime_WhenSuccessfull() {
        Anime Anime = AnimeCreator.createValidAnime();

        Anime animeFoundWithId = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(animeFoundWithId).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace updates an anime when successfull")
    void replace_UpdatesAnime_WhenSuccessfull() {

        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

    }

    @Test
    @DisplayName("Delete removes an anime when successfull")
    void delete_RemovesAnime_WhenSuccessfull() {

        Assertions.assertThatCode(() -> animeService.delete(1)).doesNotThrowAnyException();

    }

}