package org.project.dev.member.repository;


import org.project.dev.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

//    boolean existsByMemberEmail(String memberEmail);

    boolean existsByMemberNickName(String memberNickName);

    boolean existsByMemberPhone(String memberPhone);
    
    // 이메일 찾기
    MemberEntity findByMemberNickNameAndMemberPhone(String memberNickName, String memberPhone);
    
    // 비밀번호 찾기
    MemberEntity findByMemberEmailAndMemberPhone(String memberEmail, String memberPhone);

    // 회원목록 내 검색
    List<MemberEntity> findByMemberEmailContaining(String search);
    List<MemberEntity> findByMemberNameContaining(String search);
    List<MemberEntity> findByMemberNickNameContaining(String search);
    List<MemberEntity> findByMemberPhoneContaining(String search);
    List<MemberEntity> findByMemberBirthContaining(String search);
    List<MemberEntity> findByMemberStreetAddressContaining(String search);
    List<MemberEntity> findByMemberDetailAddressContaining(String search);
    List<MemberEntity> findByRoleContaining(String search);

    // 일반회원, 간편회원 한 테이블에 모두 출력, 값 없으면 null 처리
//    @Query("SELECT 'member' AS member, m.memberEmail AS memberEmail, " +
//            "m.memberNickName AS memberNickName, m.memberName AS memberName, " +
//            "m.memberBirth AS memberBirth, m.memberPhone AS memberPhone, " +
//            "m.memberDetailAddress AS memberDetailAddress, " +
//            "m.memberStreetAddress AS memberStreetAddress " +
//            "FROM MemberEntity m " +
//            "UNION ALL " +
//            "SELECT 'semiMember' AS member, s.semiMemberEmail AS memberEmail, " +
//            "NULL AS memberNickName, NULL AS memberName, NULL AS memberBirth, " +
//            "NULL AS memberPhone, NULL AS memberDetailAddress, NULL AS memberStreetAddress " +
//            "FROM SemiMemberEntity s")
//    List<CombineDto> getCombinedMemberAndSemiMemberInfo();
}


