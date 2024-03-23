package com.itmo.olymp.utils;

import com.itmo.olymp.dto.director.DirectorDto;
import com.itmo.olymp.dto.director.DirectorListWrapper;
import com.itmo.olymp.dto.director.DirectorWrapper;
import com.itmo.olymp.dto.movie.MovieDto;
import com.itmo.olymp.dto.movie.MovieListWrapper;
import com.itmo.olymp.dto.movie.MovieWrapper;
import com.itmo.olymp.entity.Director;
import com.itmo.olymp.entity.Movie;
import com.itmo.olymp.exception.RequiredFieldException;
import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EntityMapper {

    private MovieDto mapMovieEntity(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .rating(movie.getRating())
                .length(movie.getLength().toString())
                .director(movie.getDirector().getId())
                .build();
    }

    public MovieWrapper mapMovieEntityToWrapper(Movie movie) {
        return MovieWrapper.builder()
                .movie(mapMovieEntity(movie))
                .build();
    }

    public MovieListWrapper mapMovieEntityList(List<Movie> movies) {
        return MovieListWrapper.builder().list(
                        movies.stream().map(EntityMapper::mapMovieEntity).collect(Collectors.toList()))
                .build();
    }

    public Movie mapMovieWrapperToEntity(MovieWrapper movieWrapper) throws ParseException {
        return mapMovieDtoToEntity(movieWrapper.getMovie());
    }

    private Movie mapMovieDtoToEntity(MovieDto movieDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        if (movieDto.getLength() == null) {
            throw new RequiredFieldException("Movie length");
        }
        return Movie.builder()
                .id(movieDto.getId())
                .year(movieDto.getYear())
                .title(movieDto.getTitle())
                .rating(movieDto.getRating())
                .length(new Time(sdf.parse(movieDto.getLength()).getTime()))
                .director(Director.builder().id(movieDto.getDirector()).build())
                .build();
    }



    public DirectorWrapper mapDirectorEntityToWrapper(Director director) {
        return DirectorWrapper.builder()
                .director(new DirectorDto(director.getId(), director.getName()))
                .build();
    }

    public DirectorListWrapper mapDirectorEntityList(List<Director> directorList) {
        return DirectorListWrapper.builder()
                .list(directorList.stream()
                        .map(director -> new DirectorDto(director.getId(), director.getName()))
                        .collect(Collectors.toList()))
                .build();
    }


    public Director mapDirectorWrapperToEntity(DirectorWrapper directorWrapper) {
        return mapDirectorDtoToDirector(directorWrapper.getDirector());
    }

    private Director mapDirectorDtoToDirector(DirectorDto directorDto) {
        return new Director(directorDto.getId(), directorDto.getFio());
    }

}
