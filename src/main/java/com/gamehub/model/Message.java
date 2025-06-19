package com.gamehub.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ForeignKey;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="messages")
public class Message {

	@Id
	private UUID id;
	
	@Column(nullable = false)
	private String content;
	
	 @Column(nullable = false)
	private LocalDateTime timestamp;
	
	 @ManyToOne
	 @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_message_sender"))
	 private User sender;
	 
	 @ManyToOne
	 @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "fk_message_match"))
	 private Match match;

	 @ManyToOne
	 @JoinColumn(name = "tournament_id", foreignKey = @ForeignKey(name = "fk_message_tournament"))
	 private Tournament tournament;
}
