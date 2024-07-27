package me.croco.onulmohaji.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.croco.onulmohaji.member.domain.Member;
import me.croco.onulmohaji.member.domain.MemberSearchInfo;
import me.croco.onulmohaji.member.dto.MemberAddRequest;
import me.croco.onulmohaji.member.repository.MemberRepository;
import me.croco.onulmohaji.member.repository.MemberSearchInfoRepository;
import me.croco.onulmohaji.util.Authorities;
import me.croco.onulmohaji.util.HttpHeaderChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberSearchInfoRepository memberSearchInfoRepository;
    private final HttpHeaderChecker httpHeaderChecker;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 email : " + username));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id : " + id));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 email : " + email));
    }

    public Long saveMember(MemberAddRequest request) {

        if(memberRepository.existsByEmail(request.getEmail())) {    // 이메일 중복확인
            return null;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return memberRepository.save(
                        Member.builder()
                                .email(request.getEmail())
                                .nickname(request.getNickname())
                                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                                .localcodeId(request.getLocalcodeId())
                                .authorities(Authorities.ROLE_USER)
                                .build()
                )
                .getId();
    }

    public MemberSearchInfo findMemberSearchInfo(Long memberId) {
        return memberSearchInfoRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID"));
    }
    public Member getLoginMember(HttpServletRequest request) {
        // 로그인 상태인지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            boolean validToken = httpHeaderChecker.checkAuthorizationHeader(request);

            if (!validToken) {   // 비로그인 상태
                return null;
            }
        }

        // 로그인 멤버 반환
        return memberRepository.findByEmail(authentication.getName()).get();
    }
}