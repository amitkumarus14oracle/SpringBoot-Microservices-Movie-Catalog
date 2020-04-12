package com.amit.ratingsdataservice.resources;

import com.amit.ratingsdataservice.models.Rating;
import com.amit.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/ratings")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 8);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String movieId) {
        UserRating ur = new UserRating();
        ur.setUserRatings(Arrays.asList(new Rating(movieId, 8), new Rating("13", 7)
                , new Rating("14", 9)));
        return ur;
    }
}
