package com.itmo.olymp.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {

    private Integer id;

    private String title;

    private Integer year;

    private Integer director;

    private String length;

    private Integer rating;
}
