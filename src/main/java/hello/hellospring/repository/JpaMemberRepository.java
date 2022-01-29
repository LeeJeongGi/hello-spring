package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // jpa를 사용하려면 EntityManager를 사용한다(스프링 빈에서 자동생성해서 주입해준다)
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {

        em.persist(member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {

        Member member = em.find(Member.class, id);

        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {

        //PK가 아니면 쿼리를 입력하여야 한다.
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    @Override
    public List<Member> findAll() {

        //inLine Method 단축키 : 커맨드 + 옵션 + n;
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
