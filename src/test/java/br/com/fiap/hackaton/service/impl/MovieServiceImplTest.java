package br.com.fiap.hackaton.service.impl;

import br.com.fiap.hackaton.exception.MovieNotFoundException;
import br.com.fiap.hackaton.model.Movie;
import br.com.fiap.hackaton.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void shouldFindMovieWhenMethodCalled(){
        Movie movie = new Movie();

        when(movieRepository.findMoviesByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Collections.singletonList(movie));

        List<Movie> list = movieService.findMovie("select * from Movie where title like '%MovieName%'");

        Assertions.assertEquals(1, list.size());
    }

    @Test
    void shouldThrowErrorWhenMoviesDoesNotExists(){
        when(movieRepository.findMoviesByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        assertThrows(MovieNotFoundException.class, () -> movieService.findMovie("select * from Movie where title like '%MovieName%'"));
    }

    @Test
    void shouldGetMovieWithSuccessfuly(){
        when(movieRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Movie()));

        Movie movie = movieService.getMovie(1L);

        Assertions.assertNotNull(movie);
    }

    @Test
    void shouldThrowErrorWhenGetMovieHasError(){
        when(movieRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovie(1L));
    }

}