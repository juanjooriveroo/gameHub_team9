package com.gamehub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Lista de mensajes de una partida.")
public class MessageMatchListDto {

    @Schema(description = "Lista de mensajes de la partida")
    List<MessageMatchDto> messages;
}