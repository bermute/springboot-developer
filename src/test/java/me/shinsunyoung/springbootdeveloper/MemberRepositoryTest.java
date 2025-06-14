package me.shinsunyoung.springbootdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        //when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(3);

        members.forEach(m -> System.out.println("member = " + m.getId() + "m.getname()"+ m.getName()));


    }


    @Sql("/insert-members.sql")
    @Test
    void getMemberByName(){

        //when
        Member member = memberRepository.findByName("C").get();

        // then
        assertThat(member.getId()).isEqualTo(3);

        System.out.println("member = " + member);
        System.out.println("member name = " + member.getName());
    }

    @Test
    void saveMeber(){
        //given
        Member member = new Member(1L,"A");

        //when
        Member saved = memberRepository.save(member);

        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
        Member mm = memberRepository.findById(1L).get();
        Optional<Member> wew =memberRepository.findById(1L);


        System.out.println("ss="+saved.getId()+saved.getName());
        System.out.println("mm="+mm.getName()+mm.getId());
        System.out.println("wew="+wew.toString());
    }

    @Test
    void saveMembers(){
        List<Member> members = List.of(new Member(2L,"B"),new Member(3L,"C"));
/*        List<Member> me = new ArrayList<>();
        me.add(new Member(2L,"B"));*/
        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);

        System.out.println("tt="+memberRepository.findAll().size());

    }

    @Sql("/insert-members.sql")
    @Test
    void delMemberById(){

        //when
        memberRepository.deleteById(2L); // 디비 삭제

        //then
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();   // ture 확인용
        System.out.println("ss=" + memberRepository.findById(2L).isEmpty()); //값 확인용
        List<Member> mm = memberRepository.findAll();
        for (Member member : mm) {
            System.out.println("행확인용="+member.getId()+member.getName());
        }
    }

    @Sql("/insert-members.sql")
    @Test
    void delAll(){

        //when
        memberRepository.deleteAll();

        //then
        assertThat(memberRepository.findAll().size()).isZero();

    }

    @Sql("/insert-members.sql")
    @Test
    void update(){
        //given
        Member member = memberRepository.findById(2L).get();

        //when
        member.changeName("BC");

        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");
    }


}