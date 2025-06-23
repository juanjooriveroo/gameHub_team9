package com.gamehub.model;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ForeignKey;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="matches")
@Schema(description = "Representa una partida entre dos jugadores dentro de un torneo.")
public class Match {

    @Id
    @Schema(description = "ID único de la partida", example = "c2aab846-2212-45f3-b2b7-e56f63ab8a84")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Schema(description = "Resultado de la partida", example = "PLAYER1_WIN")
    private Result result;

    @Column
    @Schema(description = "Número de ronda de la partida", example = "1")
    private int round;

    @ManyToOne
    @JoinColumn(name = "tournament_id", foreignKey = @ForeignKey(name = "fk_match_tournament"))
    @Schema(description = "Torneo al que pertenece esta partida")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "player1_id", foreignKey = @ForeignKey(name = "fk_match_player1"))
    @Schema(description = "Primer jugador")
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", foreignKey = @ForeignKey(name = "fk_match_player2"))
    @Schema(description = "Segundo jugador")
    private User player2;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Mensajes asociados a la partida")
    private List<Message> messages;
}