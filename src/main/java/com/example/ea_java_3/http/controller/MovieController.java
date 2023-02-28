package com.example.ea_java_3.http.controller;


import com.example.ea_java_3.domain.character.dto.CharacterDTO;
import com.example.ea_java_3.domain.character.dto.CharacterMapper;
import com.example.ea_java_3.domain.character.service.CharacterService;
import com.example.ea_java_3.domain.movie.dto.MovieDTO;
import com.example.ea_java_3.domain.movie.dto.MovieMapper;
import com.example.ea_java_3.domain.movie.service.MovieService;
import com.example.ea_java_3.exceptions.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "movie") // Base URL
public class MovieController {

    private final CharacterService characterService;
    private final MovieService movieService;
    private final CharacterMapper characterMapper;
    private final MovieMapper movieMapper;

    public MovieController(@Qualifier("movieServiceImpl") MovieService movieService, MovieMapper movieMapper, CharacterService characterService, CharacterMapper characterMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a project by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Success",
                content = { @Content (mediaType = "application/json",
                schema = @Schema(implementation = MovieDTO.class)
                )}
            ),
            @ApiResponse(responseCode = "404",
                description = "Movie does not exist with this ID",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class))}
            )
    })
    public ResponseEntity getById(@PathVariable int id) {
        MovieDTO mov = movieMapper.toMovieDto(
                movieService.getById(id)
        );
        return ResponseEntity.ok().body(mov);
    }

    @GetMapping("/movies")
    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
            array = @ArraySchema( schema = @Schema(implementation = MovieDTO.class)))}
           ),
            @ApiResponse(responseCode = "404",
            description = "Movie not found with that ID",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity getAll() {
        List<MovieDTO> movies = movieService.getAll().stream().map(movieMapper::toMovieDto).toList();
        return ResponseEntity.ok().body(movies.toString());
    }

    @GetMapping("{id}/characters")
    @Operation(summary = "Get all movie characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Success",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CharacterDTO.class)))
            })
    })
    public ResponseEntity<List<CharacterDTO>> getMovieCharacters(@PathVariable int id) {
        List<CharacterDTO> chars = movieService.getMovieCharacters(id).stream().map(characterMapper::toCharacterDto).toList();
        return ResponseEntity.ok().body(chars);
    }

    @PutMapping("{id}/characters")
    public ResponseEntity<String> updateMovieCharacters(@PathVariable String id) {
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody MovieDTO body) {
        return ResponseEntity.ok().body("ok");
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody MovieDTO body) {
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok().body("ok");
    }
}
