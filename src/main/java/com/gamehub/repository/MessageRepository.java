package com.gamehub.repository;

import com.gamehub.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository  extends JpaRepository<Message, UUID> {

}
