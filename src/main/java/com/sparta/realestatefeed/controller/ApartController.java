package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.OneApartLikeResponseDto;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.ApartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aparts")
public class ApartController {

    private final ApartService apartService;

    public ApartController(ApartService apartService) {
        this.apartService = apartService;
    }

    /**
     * 게시글 등록 기능 ( 인가 필요 )
     * @param apartRequestDto : 등록할 게시글의 정보
     * @return : 등록 된 게시글의 정보 및 username
     */
    @PostMapping
    public ResponseEntity<CommonDto<ApartResponseDto>> createApart(@RequestBody ApartRequestDto apartRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonDto<ApartResponseDto> responseDto = apartService.createApart(apartRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /**
     * 특정 게시글 조회 기능
     * @param id : 특정 게시글의 번호
     * @return : 특정 게시글 조회 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonDto<OneApartLikeResponseDto>> getApart(@PathVariable Long id) {
        CommonDto<OneApartLikeResponseDto> responseDto = apartService.getApart(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /**
     * 전체 게시글 조회 기능
     * @return : 전체 게시글 조회 데이터
     */
    @GetMapping
    public ResponseEntity<CommonDto<List<ApartResponseDto>>> getAparts(@RequestParam(required = false) String area,
                                                                       @RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        page = page > 0 ? page - 1 : 0;
        CommonDto<List<ApartResponseDto>> responseDtos = apartService.getAparts(area, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    /**
     * 게시글 수정 기능 ( 인가 필요 )
     * @param id : 수정할 게시글의 id
     * @param apartRequestDto : 수정할 게시글의 정보
     * @return : 수정된 게시글의 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonDto<ApartResponseDto>> updateApart(@PathVariable Long id, @RequestBody ApartRequestDto apartRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonDto<ApartResponseDto> responseDto = apartService.updateApart(id, apartRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /**
     * 게시글 삭제 기능 ( 인가 필요 )
     * @param id : 삭제할 게시글의 id
     * @return : 삭제 완료 메시지 상태 코드 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonDto<String>> deleteApart(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonDto<String> responseDto = apartService.deleteApart(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /**
     * 게시글 좋아요 기능 ( 인가 필요 )
     * @param id : 좋아요 할 게시글의 id
     * @return : 좋아요 추가/취소 확인
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<CommonDto<String>> likeApart(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        CommonDto<String> responseDto = apartService.likeApart(id, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    /**
     * 좋아요 누른 게시글 조회 기능
     * @return : 좋아요 누른 게시글 조회
     */
    @GetMapping("/like")
    public ResponseEntity<CommonDto<List<ApartResponseDto>>> getLikeAparts(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "5") int size,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommonDto<List<ApartResponseDto>> responseDtos = apartService.getLikeAparts(userDetails.getUser().getId(), page, size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

}
