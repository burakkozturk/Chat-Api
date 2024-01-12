package app.circle.service;

import app.circle.entity.BlockList;
import app.circle.repository.BlockListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlockListService {

    private final BlockListRepository blockListRepository;
    private final FriendShipRequestService friendShipRequestService;

    public BlockListService(BlockListRepository blockListRepository, FriendShipRequestService friendShipRequestService) {
        this.blockListRepository = blockListRepository;
        this.friendShipRequestService = friendShipRequestService;
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

        //friendShipRequestService.removeFriends(blockerUserId,blockedUserId);
        return "User blocked successfully";
    }

    public String removeBlock(Long blockListId){
        blockListRepository.deleteById(blockListId);
        return "Block Removed";
    }

}
