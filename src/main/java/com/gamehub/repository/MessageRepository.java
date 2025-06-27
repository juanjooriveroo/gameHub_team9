package com.gamehub.repository;

import com.gamehub.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface MessageRepository  extends JpaRepository<Message, UUID> {

    List<Message> findByTournamentId(UUID tournamentId);
}
