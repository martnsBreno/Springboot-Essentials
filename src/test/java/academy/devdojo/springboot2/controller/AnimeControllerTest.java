package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.listAll(any())).thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(anyLong())).thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(anyString())).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(any(AnimePostRequestBody.class))).thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(anyLong());
    }

    @Test
    @DisplayName("List Returns list of anime inside page object when successfull")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("Returns a List of Animes when successfull")
    void listAll_ReturnsListOfAnime_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesList = animeController.listAll().getBody();

        Assertions.assertThat(animesList).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animesList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Find by id return an anime when successfull")
    void findById_ReturnsAnime_WhenSuccessfull() {
        Anime Anime = AnimeCreator.createValidAnime();

        Anime animeFoundWithId = animeController.findById(1).getBody();

        Assertions.assertThat(animeFoundWithId.getId()).isNotNull().isEqualTo(Anime.getId());
    }

    @Test
    @DisplayName("findByName returns a list of anime when successfull")
    void findByName_ReturnsListOfAnime_WhenSuccessfull() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animePage = animeController.findByName("anime").getBody();

        Assertions.assertThat(animePage).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when successfull")
    void findByName_ReturnsAnEmptyListOfAnime_WhenSuccessfull() {

        BDDMockito.when(animeServiceMock.findByName(anyString())).thenReturn(Collections.emptyList());

        String animeCreated = AnimeCreator.createValidAnime().getName();
        List<Anime> animePage = animeController.findByName("").getBody();

        Assertions.assertThat(animePage).isEmpty();
    }

    @Test
    @DisplayName("Save returns an anime when successfull")
    void save_ReturnsAnime_WhenSuccessfull() {
        Anime Anime = AnimeCreator.createValidAnime();

        Anime animeFoundWithId = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(animeFoundWithId).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace updates an anime when successfull")
    void replace_UpdatesAnime_WhenSuccessfull() {

        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete removes an anime when successfull")
    void delete_RemovesAnime_WhenSuccessfull() {

        Assertions.assertThatCode(() -> animeController.delete(1)).doesNotThrowAnyException();

        ResponseEntity<Void> delete = animeController.delete(1);

        Assertions.assertThat(delete).isNotNull();

        Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}