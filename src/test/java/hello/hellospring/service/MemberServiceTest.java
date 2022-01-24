package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    //test 클래스 자동으로 만드는 단축키 : 커맨드 + 쉬프트 + t
    // 컨트롤 + r : 이전에 실행했던 테스트코드 실행
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring!!");

        //when
        Long saveId = memberService.join(member);

        //then
        Optional<Member> result = memberService.findOne(saveId);
        Member findMember = result.get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring!!");

        Member member2 = new Member();
        member2.setName("spring!!");

        //when
        memberService.join(member1);

        //then
        assertThatThrownBy(() ->
                memberService.join(member2)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}