package org.nelis.securechat.demo.backendspring.data;

import org.nelis.securechat.domain.ChatRoom;
import org.nelis.securechat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<ChatRoom, Long> {
}
