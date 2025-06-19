package com.gamehub.model;

import java.util.List;
import java.util.UUID;

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
public class Match {

	@Id
	private UUID id;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 50)
	private Result result;
	
	@Column
	private int round;
	
    @ManyToOne
    @JoinColumn(name = "tournament_id", foreignKey = @ForeignKey(name = "fk_match_tournament"))
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "player1_id", foreignKey = @ForeignKey(name = "fk_match_player1"))
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", foreignKey = @ForeignKey(name = "fk_match_player2"))
    private User player2;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
}
