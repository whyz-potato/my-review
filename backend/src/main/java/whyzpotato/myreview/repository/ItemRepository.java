package whyzpotato.myreview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.domain.Users;

import javax.persistence.EntityManager;
import java.util.List;

import static java.lang.Math.min;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null)
            em.persist(item);
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public Book findBookByIsbn(String isbn) {
        return em.createQuery(
                        "select b" +
                                " from Book b" +
                                " where b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public List<Book> findAllBook() {
        return em.createQuery(
                        "select b " +
                                "from Book b", Book.class)
                .getResultList();
    }


    public List<Book> top10Book() {
        List<Book> books = em.createNativeQuery(
                        "select *" +
                                " from item i" +
                                " join (select r.item_id as id from review r group by r.item_id order by count(*) desc)" +
                                " on id = i.item_id" +
                                " where i.dtype = 'Book'", Book.class)
                .getResultList();
        return books.subList(0, min(10, books.size()));
    }

    public List<Book> newBooks(int max) {
        List<Book> books = em.createQuery(
                        "select b" +
                                " from Book b" +
                                " order by release_date desc", Book.class)
                .getResultList();
        return books.subList(0, min(max, books.size()));
    }

    //사용하지 말 것 (ReviewRepository에 있는 메소드 사용 권장)
    public List<Book> findLikeBooksByUser(Users users) {
        return em.createNativeQuery(
                        "select * from item i" +
                                " join (select *" +
                                " from review r" +
                                " where r.users_id = :users_id and r.status = 'LIKE') as r" +
                                " on r.item_id = i.item_id "
                        , Book.class)
                .setParameter("users_id", users.getId())
                .getResultList();
    }

    public Movie findMovieByTitleDirector(String title, String director) {
        return em.createQuery(
                        "select m" +
                                " from Movie m" +
                                " where m.title = :title and m.director = :director", Movie.class)
                .setParameter("title", title)
                .setParameter("director", director)
                .getSingleResult();
    }

    public List<Movie> findAllMovie() {
        return em.createQuery(
                        "select m " +
                                "from Movie m", Movie.class)
                .getResultList();
    }


    public List<Movie> top10Movie() {
        List<Movie> movies = em.createNativeQuery(
                        "select *" +
                                " from item i" +
                                " join (select r.item_id as id from review r group by r.item_id order by count(*) desc)" +
                                " on id = i.item_id" +
                                " where i.dtype = 'Movie'", Movie.class)
                .getResultList();
        return movies.subList(0, min(10, movies.size()));
    }

    public List<Movie> newMovies(int max) {
        List<Movie> movies = em.createQuery(
                        "select m" +
                                " from Movie m" +
                                " order by release_date desc", Movie.class)
                .getResultList();
        return movies.subList(0, min(max, movies.size()));
    }

}
