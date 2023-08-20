package org.project.dev.member.service;

import lombok.RequiredArgsConstructor;

import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // Create (builder 활용)
//    @Transactional
//    public void insertMember(MemberDto memberDto) {
//
//        String email = memberRepository.save(MemberEntity.builder()
//                .email(memberDto.getEmail())
//                .password(passwordEncoder.encode(memberDto.getPassword()))
//                .name(memberDto.getName())
//                .nickName(memberDto.getNickName())
//                .phone(memberDto.getPhone())
//                .birth(memberDto.getBirth())
//                .address(memberDto.getAddress())
//                .role(Role.MEMBER)
//                .build()).getEmail();
//        memberRepository.findByEmail(email).orElseThrow(IllegalAccessError::new);
//    }

    // Create (to메서드 활용)
    @Transactional
    public void insertMember(MemberDto memberDto){

        MemberEntity memberEntity=MemberEntity.toMemberEntityInsert(memberDto, passwordEncoder);
        Long memberId=memberRepository.save(memberEntity).getMemberId();
        MemberEntity memberEntity1=memberRepository.findById(memberId).orElseThrow(IllegalAccessError::new);
    }

    // Read (builder)
//    public List<MemberDto> listMember() {
//
//        List<MemberDto> memberDtoList = new ArrayList<>();
//        List<MemberEntity> memberEntityList = memberRepository.findAll();
//
//        for (MemberEntity memberEntity : memberEntityList) {
//            memberDtoList.add(MemberDto.builder()
//                    .id(memberEntity.getId())
//                    .email(memberEntity.getEmail())
//                    .password(memberEntity.getPassword())
//                    .name(memberEntity.getName())
//                    .nickName(memberEntity.getNickName())
//                    .phone(memberEntity.getPhone())
//                    .birth(memberEntity.getBirth())
//                    .address(memberEntity.getAddress())
//                    .role(memberEntity.getRole())
//                    .createTime(memberEntity.getCreateTime())
//                    .updateTime(memberEntity.getUpdateTime())
//                    .build());
//        }
//        return memberDtoList;
//    }

    // Read (to메서드)
    public List<MemberDto> listMember(){

        List<MemberDto> memberDtoList=new ArrayList<>();
        List<MemberEntity> memberEntityList=memberRepository.findAll();

        if (!memberEntityList.isEmpty()) {
            for(MemberEntity memberEntity: memberEntityList){

                MemberDto memberDto=MemberDto.toMemberDto(memberEntity);
                memberDtoList.add(memberDto);
            }
        }
        return memberDtoList;
    }

    // Detail (builder)
//    public MemberDto detailMember(Long id) {
//
//        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(IllegalAccessError::new);
//
//        return MemberDto.builder()
//                .id(memberEntity.getId())
//                .email(memberEntity.getEmail())
//                .password(memberEntity.getPassword())
//                .name(memberEntity.getName())
//                .nickName(memberEntity.getNickName())
//                .phone(memberEntity.getPhone())
//                .birth(memberEntity.getBirth())
//                .address(memberEntity.getAddress())
//                .role(memberEntity.getRole())
//                .createTime(memberEntity.getCreateTime())
//                .updateTime(memberEntity.getUpdateTime())
//                .build();
//    }

    // Detail (to메서드)
    public MemberDto detailMember(Long memberId){

        Optional<MemberEntity> optionalMemberEntity=Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
            return new IllegalArgumentException("조회할 아이디가 없습니다");
        }));

        if (optionalMemberEntity.isPresent()) {

            // 무시해도 됨
//            MemberDto memberDto=MemberDto.toMemberDto(optionalMemberEntity.get());
//            return memberDto;
            return MemberDto.toMemberDto(optionalMemberEntity.get());
        }
        return null;
    }

    // Update - 수정 화면
    public MemberDto updateViewMember(Long memberId){

        MemberEntity memberEntity=memberRepository.findById(memberId).orElseThrow(IllegalAccessError::new);
        return MemberDto.toMemberDto(memberEntity);
    }

    // Update - 실제 실행(builder)
//    @Transactional
//    public int updateMember(MemberDto memberDto){
//
//        MemberEntity memberEntity=MemberEntity.builder()
//                .id(memberDto.getId())
//                .email(memberDto.getEmail())
//                .password(memberDto.getPassword())
//                .name(memberDto.getName())
//                .nickName(memberDto.getNickName())
//                .phone(memberDto.getPhone())
//                .birth(memberDto.getBirth())
//                .address(memberDto.getAddress())
//                .role(memberDto.getRole())
//                .build();
//        Long id=memberRepository.save(memberEntity).getId();
//        Optional<MemberEntity> optionalMemberEntity=Optional.ofNullable(memberRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("수정할 아이디가 없습니다");
//        }));
//
//        if (optionalMemberEntity.isPresent()) {
//            System.out.println("회원정보 수정 성공");
//            return 1;
//        }else{
//            System.out.println("회원정보 수정 실패");
//            return 0;
//        }
//    }

        // Update - 실제 실행(to메서드)
    @Transactional
    public int updateMember(MemberDto memberDto){

        Optional<MemberEntity> optionalMemberEntity=Optional.ofNullable(memberRepository.findById(memberDto.getMemberId()).orElseThrow(()->{
            return new IllegalArgumentException("수정할 아이디가 없습니다");
        }));

        MemberEntity memberEntity=MemberEntity.toMemberEntityUpdate(memberDto);
        Long memberId=memberRepository.save(memberEntity).getMemberId();

        Optional<MemberEntity> optionalMemberEntity1=Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
            return new IllegalArgumentException("수정할 아이디가 없습니다");
        }));

        if (optionalMemberEntity1.isPresent()) {
            System.out.println("회원정보 수정 성공");
            return 1;
        }else{
            System.out.println("회원정보 수정 실패");
            return 0;
        }
    }

    // Delete
    @Transactional
    public int deleteMember(Long memberId){

        Optional<MemberEntity> optionalMemberEntity=Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
            return new IllegalArgumentException("삭제할 아이디가 없습니다");
        }));

        memberRepository.delete(optionalMemberEntity.get());

        Optional<MemberEntity> optionalMemberEntity1=memberRepository.findById(memberId);

        if(!optionalMemberEntity1.isPresent()){
            return 1;
        }else{
            return 0;
        }
    }

    // 이메일 중복 확인 기능
    @Transactional
    public boolean existsByMemberEmail(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }

    // 닉네임 중복 확인 기능
    @Transactional
    public boolean existsByMemberNickName(String memberNickName) {
        return memberRepository.existsByMemberNickName(memberNickName);
    }
}
