-- Tabla de usuarios
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    rank VARCHAR(50),
    points INT DEFAULT 0
);

-- Tabla de torneos
CREATE TABLE tournaments (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    max_players INT NOT NULL,
    status VARCHAR(50) NOT NULL
);

-- Las relaciones List<User> se resuelven con una tabla intermedia (tournament_players).
CREATE TABLE tournament_players (
    tournament_id UUID REFERENCES tournaments(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (tournament_id, user_id)
);

-- Tabla de partidas
CREATE TABLE matches (
    id UUID PRIMARY KEY,
    tournament_id UUID REFERENCES tournaments(id) ON DELETE SET NULL,
    player1_id UUID REFERENCES users(id) ON DELETE SET NULL,
    player2_id UUID REFERENCES users(id) ON DELETE SET NULL,
    result VARCHAR(50),
    round INT
);

-- Tabla de mensajes
CREATE TABLE messages (
    id UUID PRIMARY KEY,
    sender_id UUID REFERENCES users(id) ON DELETE SET NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    match_id UUID REFERENCES matches(id) ON DELETE SET NULL,
    tournament_id UUID REFERENCES tournaments(id) ON DELETE SET NULL
);
