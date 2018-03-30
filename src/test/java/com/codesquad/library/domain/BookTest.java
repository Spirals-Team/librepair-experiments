package com.codesquad.library.domain;

import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.domain.repositories.BookRepository;
import com.codesquad.library.domain.repositories.MemberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Validator;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookTest {

    private static final Logger log = LoggerFactory.getLogger(BookTest.class);

    @Autowired
    private Validator validator;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    private Book book;
    private Member member;
    private Review review;

    @Before
    public void setUp() {
        this.book = Book.builder()
                .title("이말년씨리즈")
                .description("이말년 씨리즈~ 재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미재미")
                .build();

        this.member = Member.builder()
                .name("양희정")
                .loginEmail("lemon@codesquad.kr")
                .password("1234")
                .build();

        this.review = Review.builder()
                .content("킹갓말년의 역작! 필독을 추천합니다.")
                .build();

        this.member = memberRepository.save(member);
        this.book = this.bookRepository.save(book);
    }


    @Test
    @Transactional
    public void RENT_TEST() {
        this.book.setPossessed(this.member);

        bookRepository.save(this.book);

        assertThat(bookRepository.findByTitle("이말년씨리즈").get().getMember(), is(this.member));
        assertThat(bookRepository.findByTitle("이말년씨리즈").get().getLastRentDate().getDayOfMonth(), is(LocalDateTime.now().getDayOfMonth()));
//        assertThat(memberRepository.findByLoginEmail("lemon@codesquad.kr").get().getLendedBooks().get(0), is(this.book));
    }

    @Test
    @Transactional
    public void REGISTER_REVIEW() {
        Book book = bookRepository.findByTitle("이말년씨리즈").get();

        book.addReview(this.review);
        bookRepository.save(book);

        assertThat(book.getReviews().get(0), is(this.review));
    }
}