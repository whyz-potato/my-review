package whyzpotato.myreview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import whyzpotato.myreview.domain.Book;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Movie;
import whyzpotato.myreview.domain.Users;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Book save(Book book) {
        if (book.getId() != null)
            return em.merge(book);

        Optional<Book> optional = findBookByIsbn(book.getIsbn());
        if (optional.isPresent())
            return optional.get();
        else
            em.persist(book);
        return book;
    }

    public Movie save(Movie movie) {
        if (movie.getId() != null)
            return em.merge(movie);

        Optional<Movie> optional = findMovieByTitleDirector(movie.getTitle(), movie.getDirector());
        if (optional.isPresent())
            return optional.get();
        else
            em.persist(movie);
        return movie;
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(em.find(Item.class, id));
    }

    public void deleteAll() {
        em.createQuery("delete from item i");
    }

    //--책 조회--//
    public Optional<Book> findBookByIsbn(String isbn) {
        return em.createQuery(
                        "select b" +
                                " from Book b" +
                                " where b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .getResultList()
                .stream().findAny();
    }

    public List<Book> findAllBook() {
        return em.createQuery(
                        "select b " +
                                "from Book b", Book.class)
                .getResultList();
    }


    //--추천 페이지용 책 조회--//
    public List<Book> likeBooksByUser(Users users) {
        return em.createQuery(
                        "select i" +
                                " from Review r" +
                                " join r.item i" +
                                " where r.users = :user and type(i) = 'Book' and r.status = 'LIKE'", Item.class)
                .setParameter("user", users)
                .getResultList()
                .stream()
                .map(i -> (Book) i)
                .collect(Collectors.toList());
    }

    public List<Book> top10Books() {
        List<Book> books = em.createNativeQuery(
                        "select *" +
                                " from item i" +
                                " join (select r.item_id as id from review r group by r.item_id order by count(*) desc)" +
                                " on id = i.item_id" +
                                " where i.dtype = 'Book'", Book.class)
                .getResultList();
        return books.subList(0, min(10, books.size()));
    }

    public List<Book> newBooks() {
        List<Book> books = em.createQuery(
                        "select b" +
                                " from Book b" +
                                " order by release_date desc", Book.class)
                .getResultList();
        return books.subList(0, min(10, books.size()));
    }


    //-- Movie 조회 --//
    public Optional<Movie> findMovieByTitleDirector(String title, String director) {
        return em.createQuery(
                        "select m" +
                                " from Movie m" +
                                " where m.title = :title and m.director = :director", Movie.class)
                .setParameter("title", title)
                .setParameter("director", director)
                .getResultList()
                .stream().findAny();
    }

    public List<Movie> findAllMovie() {
        return em.createQuery(
                        "select m " +
                                "from Movie m", Movie.class)
                .getResultList();
    }


    //--추천 페이지 용 Movie 조회--//

    public List<Movie> likeMoviesByUser(Users users) {
        return em.createQuery(
                        "select i" +
                                " from Review r" +
                                " join r.item i" +
                                " where r.users = :user and type(i) = 'Movie' and r.status = 'LIKE'", Item.class)
                .setParameter("user", users)
                .getResultList()
                .stream()
                .map(i -> (Movie) i)
                .collect(Collectors.toList());
    }

    public List<Movie> top10Movies() {
        List<Movie> movies = em.createNativeQuery(
                        "select *" +
                                " from item i" +
                                " join (select r.item_id as id from review r group by r.item_id order by count(*) desc)" +
                                " on id = i.item_id" +
                                " where i.dtype = 'Movie'", Movie.class)
                .getResultList();
        return movies.subList(0, min(10, movies.size()));
    }

    public List<Movie> newMovies() {
        List<Movie> movies = em.createQuery(
                        "select m" +
                                " from Movie m" +
                                " order by release_date desc", Movie.class)
                .getResultList();
        return movies.subList(0, min(10, movies.size()));
    }


}
