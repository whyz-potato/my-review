package whyzpotato.myreview;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import whyzpotato.myreview.domain.*;
import whyzpotato.myreview.repository.ItemRepository;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class InitDB {
    private final InitService initService;
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        initService.init();
    }

    public void cleanUp(){
        usersRepository.deleteAll();
        reviewRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public Users createUser(String name) {
            Users users = Users.builder()
                    .name(name)
                    .pw("pw")
                    .email(name + "@potato.com")
                    .build();
            em.persist(users);
            return users;
        }

        public Book createBook(String title, String isbn) {
            Book book = Book.builder()
                    .title(title)
                    .author(title + "'s author")
                    .isbn(isbn)
                    .build();
            em.persist(book);
            return book;
        }

        public Movie createMovie(String title, String director) {
            Movie movie = Movie.builder()
                    .title(title)
                    .director(director)
                    .build();
            em.persist(movie);
            return movie;
        }

        public Review createReview(Users users, Item item, ReviewStatus status) {
            Review review = Review.builder()
                    .users(users)
                    .item(item)
                    .rate(5)
                    .content("리뷰 작성 내용")
                    .status(status)
                    .build();
            em.persist(review);
            return review;
        }

        public void init() {
            Users user1 = createUser("user1");
            Users user2 = createUser("user2");
            Users user3 = createUser("user3");
            Book book1 = createBook("스프링 데이터 예제 프로젝트로 배우는 전자정부 표준 데이터베이스 프레임워크 자바 orm 표준 JPA 프로그래밍", "9788960777330");
            Book book2 = createBook("스프링 부트와 aws로 혼자 구현하는 웹 서비스", "9788965402602");
            Book book3 = createBook("이펙티브 자바", "9788966262281");
            Movie movie1 = createMovie("극장판 뽀로로와 친구들: <b>바이러스를 없애줘</b>!", "박영균|");
            Movie movie2 = createMovie("<b>멍뭉이</b>", "김주환|");
            createReview(user1, book1, ReviewStatus.LIKE);
            createReview(user1, book2, ReviewStatus.LIKE);
            createReview(user1, book3, ReviewStatus.LIKE);
            createReview(user1, movie1, ReviewStatus.LIKE);
            createReview(user1, movie2, ReviewStatus.LIKE);
            createReview(user2, book1, ReviewStatus.WATCHING);
            createReview(user2, book2, ReviewStatus.DONE);
            createReview(user3, movie1, ReviewStatus.DONE);
            createReview(user3, movie2, ReviewStatus.DONE);
            createReview(user3, book2, ReviewStatus.DONE);
            createReview(user3, book3, ReviewStatus.LIKE);
        }




    }


}
