package org.project.dev.member.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.cartNew.entity.CartEntity;
import org.project.dev.cartNew.repository.CartRepository;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.dto.SemiMemberDto;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.entity.SemiMemberEntity;
import org.project.dev.member.repository.SemiMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemiMemberService {

    private final SemiMemberRepository semiMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    // Create + 장바구니 생성
    @Transactional
    public void insertSemiMember(SemiMemberDto semiMemberDto){

        SemiMemberEntity semiMemberEntity=SemiMemberEntity.toSemiMemberEntityInsert(semiMemberDto, passwordEncoder);
        Long memberId=semiMemberRepository.save(semiMemberEntity).getSemiMemberId();

        // 회원가입 이후 카트 생성
        createCartForMember(memberId);
    }
    private void createCartForMember(Long semiMemberId) {
        SemiMemberEntity semiMemberEntity = semiMemberRepository.findById(semiMemberId).orElseThrow(IllegalAccessError::new);
        CartEntity cart = CartEntity.createSemiCart(semiMemberEntity);
        cartRepository.save(cart);
    }

    // 이메일 중복 확인 메서드
    @Transactional
    public boolean existsBySemiMemberEmail(String semiMemberEmail) {
        return semiMemberRepository.existsBySemiMemberEmail(semiMemberEmail);
    }

    // Read - 간편회원목록
    public List<SemiMemberDto> listSemiMember(){

        List<SemiMemberDto> semiMemberDtoList=new ArrayList<>();
        List<SemiMemberEntity> semiMemberEntityList=semiMemberRepository.findAll();

        if (!semiMemberEntityList.isEmpty()) {
            for(SemiMemberEntity semiMemberEntity: semiMemberEntityList){

                SemiMemberDto semiMemberDto=SemiMemberDto.toSemiMemberDto(semiMemberEntity);
                semiMemberDtoList.add(semiMemberDto);
            }
        }
        return semiMemberDtoList;
    }

    //  Detail
    public SemiMemberDto detailSemiMember(Long semiMemberId){

        Optional<SemiMemberEntity> optionalSemiMemberEntity=Optional.ofNullable(semiMemberRepository.findById(semiMemberId).orElseThrow(()->{
            return new IllegalArgumentException("조회할 아이디가 없습니다");
        }));

        if (optionalSemiMemberEntity.isPresent()) {

            return SemiMemberDto.toSemiMemberDto(optionalSemiMemberEntity.get());
        }
        return null;
    }

    // Delete
    public int deleteSemiMember(Long semiMemberId) {

        Optional<SemiMemberEntity> optionalSemiMemberEntity=Optional.ofNullable(semiMemberRepository.findById(semiMemberId).orElseThrow(()->{
            return new IllegalArgumentException("삭제할 아이디가 없습니다");
        }));

        semiMemberRepository.delete(optionalSemiMemberEntity.get());

        Optional<SemiMemberEntity> optionalSemiMemberEntity1=semiMemberRepository.findById(semiMemberId);

        if(!optionalSemiMemberEntity1.isPresent()){
            return 1;
        }else{
            return 0;
        }
    }

    public SemiMemberEntity findSemiMember(Long semiMemberId) {
        return semiMemberRepository.findById(semiMemberId).get();
    }

    // 페이징
    public Page<SemiMemberDto> SemiMemberPagingList(Pageable pageable) {
        Page<SemiMemberEntity> semiMemberEntities = semiMemberRepository.findAll(pageable);
        //    boardEntities.map(board ->new BoardDto(board.getId(),board.getBoardContent(),))

        int nowPage = semiMemberEntities.getNumber();// 요청 페이지 번호
        long totalCount = semiMemberEntities.getTotalElements();// 전체게시글수
        int totalPage = semiMemberEntities.getTotalPages();// 전체 페이지갯수
        int pageSize = semiMemberEntities.getSize();    // 한페이지에 보이는 개수
        semiMemberEntities.isFirst(); // 첫번째 페이지인지?
        semiMemberEntities.isLast(); // 마지막 페이지인지?
        semiMemberEntities.hasPrevious(); // 이전 페이지 있는지?
        semiMemberEntities.hasNext(); // 다음 페이지 있는지?

        System.out.println(totalCount + " 총 글수");
        System.out.println(totalPage + " 총 페이지");
        System.out.println(pageSize + " 페이지 당 글수");
        System.out.println(nowPage + " 현재 페이지");

        // Entity → Dto
        Page<SemiMemberDto> semiMemberDtos = semiMemberEntities.map(SemiMemberDto::toSemiMemberDto);
        return semiMemberDtos;
        //    return boardRepository.findAll(pageable).map(BoardDto::toBoardDto);;
    }


}
