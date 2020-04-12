package com.amit.moviecatalogservice.resources;

import com.amit.moviecatalogservice.models.CatelogItem;
import com.amit.moviecatalogservice.models.Movie;
import com.amit.moviecatalogservice.models.Rating;
import com.amit.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;// for asynchronous call

    @RequestMapping("/{userId}")
    @GetMapping
    public List<CatelogItem> getCatalog(@PathVariable("userId") String userId) {
        //return Collections.singletonList(new CatelogItem("RHTDM", "romantic movie", 8));
        //RestTemplate restTemplate = new RestTemplate();// commented because i have added it as a instance variable
        // Get the movie id from ratings
        /*List<Rating> ratings = Arrays.asList(new Rating("12",6), new Rating("13", 7)
        , new Rating("14", 9));*/

        UserRating ratings = restTemplate.getForObject("http://localhost:9092/ratings/users/"+userId, UserRating.class);

        return ratings.getUserRatings().stream().map(rating -> {
            //Movie movie = restTemplate.getForObject("http://localhost:9091/movies/"+rating.getMovieId(), Movie.class);
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:9091/movies/"+rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)//for asynchronous call
                    .block();
            return new CatelogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());
    }
}
