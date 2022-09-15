package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
      ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/9", Anime.class);
      log.info(entity);

      Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/11", Anime.class);
      log.info(object);

      Anime[] anime = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
      log.info(Arrays.toString(anime));

      ResponseEntity<Object> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
              HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
      });
      System.out.println("Response as a list:");
      log.info(exchange.getBody());
//
//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject( "http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("Saved Anime {}", kingdomSaved);

      Anime AzurLane = Anime.builder().name("Azur Lnae").build();
      ResponseEntity<Anime> savedAzurLane = new RestTemplate().exchange("http://localhost:8080/animes/",
              HttpMethod.POST, new HttpEntity<>(AzurLane), Anime.class);
      log.info("Saved Anime {}", savedAzurLane, createJsonHeader());

      Anime laneBody = savedAzurLane.getBody();
      laneBody.setName("Azur Lanee");

      ResponseEntity<Void> updatedAzurLane = new RestTemplate()
              .exchange("http://localhost:8080/animes/",
                      HttpMethod.PUT, new HttpEntity<>(laneBody, createJsonHeader()), Void.class);

      log.info("Anime Updated {}", updatedAzurLane);

      ResponseEntity<Void> DeletAzurLane = new RestTemplate()
              .exchange("http://localhost:8080/animes/{id}",
                      HttpMethod.DELETE, null, Void.class,  laneBody.getId());

      log.info("Anime Deleted {}", DeletAzurLane);
    }

    private static HttpHeaders createJsonHeader() {
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      return httpHeaders;
    }

    Anime anime = Anime.builder().build();
}
