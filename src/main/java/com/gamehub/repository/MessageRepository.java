package com.gamehub.repository;

import com.gamehub.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository  extends JpaRepository<Message, UUID> {

    List<Message> findByTournamentId(UUID tournamentId);

    List<Message> findAllByMatch_Id(UUID matchId);

}
