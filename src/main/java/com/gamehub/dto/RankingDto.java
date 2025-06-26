package com.gamehub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con el ranking del torneo correspondiente")
public class RankingDto {

    @Schema(description = "Lista de jugadores ordenados por sus puntos en la clasificación")
    List<UserPublicDto> players;
}
