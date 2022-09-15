package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder().name("Pokemon").build();
    }

    public static Anime createValidAnime(){
        return Anime.builder().name("Pokemon").id(1L).build();
    }

    public static Anime createAValidUpdateAnime() {
        return Anime.builder().name("Pokemon").id(1L).build();
    }



}
