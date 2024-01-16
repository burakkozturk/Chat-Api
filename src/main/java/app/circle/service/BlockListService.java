package app.circle.service;

import app.circle.entity.BlockList;
import app.circle.entity.Friend;
import app.circle.repository.BlockListRepository;
import app.circle.repository.FriendRepository;
import app.circle.repository.FriendshipRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlockListService {

    private final BlockListRepository blockListRepository;
    private final FriendShipRequestService friendShipRequestService;
    private final FriendRepository friendRepository;

    public BlockListService(BlockListRepository blockListRepository, FriendShipRequestService friendShipRequestService, FriendRepository friendRepository) {
        this.blockListRepository = blockListRepository;
        this.friendShipRequestService = friendShipRequestService;
        this.friendRepository = friendRepository;
    }

    public List<UUID> getBlockedUserList(UUID blockerId) {
        List<BlockList> blockedUsers = blockListRepository.findByBlockerUserId(blockerId);
        return blockedUsers.stream()
                .map(BlockList::getBlockedUserId)
                .collect(Collectors.toList());
    }


    // BlockListService içinde

    public String blockUser(UUID blockerUserId, UUID blockedUserId){
        // Block list kaydı oluştur
        BlockList blockList = new BlockList();
        blockList.setBlockerUserId(blockerUserId);
        blockList.setBlockedUserId(blockedUserId);
        blockListRepository.save(blockList);

        // Engelleyen ve engellenen kullanıcılar arasındaki tüm arkadaşlık ilişkilerini bul
        List<Friend> friendships = friendRepository.findFriendshipsBetweenUsers(blockerUserId, blockedUserId);

        // Bulunan tüm arkadaşlık ilişkilerini sil
        for (Friend friendship : friendships) {
            friendRepository.delete(friendship);
        }

        return "User blocked and removed from friends list if present";
    }


    public String removeBlock(Long blockListId){
        blockListRepository.deleteById(blockListId);
        return "Block Removed";
    }

}
