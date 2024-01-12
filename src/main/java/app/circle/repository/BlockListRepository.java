package app.circle.repository;

import app.circle.entity.BlockList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BlockListRepository extends JpaRepository<BlockList,Long > {

    List<BlockList> findByBlockerUserId(UUID blockerUserId);

}
