package app.circle.controller;

import app.circle.entity.BlockList;
import app.circle.service.BlockListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/block")
public class BlockListController {

    private final BlockListService blockListService;

    @Autowired
    public BlockListController(BlockListService blockListService) {
        this.blockListService = blockListService;
    }

    @GetMapping("/blocked-users/{blockerId}")
    public ResponseEntity<List<UUID>> getBlockedUserList(@PathVariable UUID blockerId) {
        List<UUID> blockedUsers = blockListService.getBlockedUserList(blockerId);
        return ResponseEntity.ok(blockedUsers);
    }



    @PostMapping("/user")
    public ResponseEntity<String> blockUser(@RequestParam UUID blockerUserId, @RequestParam UUID blockedUserId){
        String response = blockListService.blockUser(blockerUserId, blockedUserId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/remove")
    public ResponseEntity<String> removeBlock(@RequestParam Long blockListId){
        String response = blockListService.removeBlock(blockListId);
        return ResponseEntity.ok(response);
    }


}
