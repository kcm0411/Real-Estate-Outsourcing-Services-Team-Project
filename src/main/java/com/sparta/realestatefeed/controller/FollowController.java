package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> addFollow(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommonDto<String> response = followService.addFollow(userDetails.getUser(), userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/aparts")
    public ResponseEntity<?> getFollowersPosts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam(defaultValue = "time") String orderBy,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){

        CommonDto<List<ApartResponseDto>> response = followService.getFollowersPosts(userDetails.getUser(), orderBy, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
