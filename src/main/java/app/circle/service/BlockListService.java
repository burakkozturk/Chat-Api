package app.circle.service;

import app.circle.entity.BlockList;
import app.circle.entity.User;
import app.circle.repository.BlockListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlockListService {

    private final BlockListRepository blockListRepository;

    public BlockListService(BlockListRepository blockListRepository) {
        this.blockListRepository = blockListRepository;
    }


    public List<UUID> getBlockedUserList(UUID blockerId) {
        List<BlockList> blockedUsers = blockListRepository.findByBlockerUserId(blockerId);
        return blockedUsers.stream()
                .map(BlockList::getBlockedUserId)
                .collect(Collectors.toList());
    }


    public String blockUser(UUID blockerUserId, UUID blockedUserId){
        BlockList blockList = new BlockList();
        blockList.setBlockerUserId(blockerUserId);
        blockList.setBlockedUserId(blockedUserId);

        blockListRepository.save(blockList);
        return "User blocked successfully";
    }

    public String removeBlock(Long blockListId){
        blockListRepository.deleteById(blockListId);
        return "Block Removed";
    }

}
