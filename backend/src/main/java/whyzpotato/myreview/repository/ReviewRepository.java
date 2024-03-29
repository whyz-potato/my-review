package whyzpotato.myreview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import whyzpotato.myreview.domain.Item;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.Users;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public Review save(Review review) {
        if (review.getId() != null)
            return em.merge(review);

        Optional<Review> optional = findByUsersItem(review.getUsers(), review.getItem());
        if (optional.isPresent())
            return em.merge(optional.get().update(review));
        else
            em.persist(review);
        return review;
    }

    public void delete(Review review) {
        em.remove(review);
    }

    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(em.find(Review.class, id));
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    public void deleteAll() {
        em.createQuery("delete from review r");
    }

    public Optional<Review> findByUsersItem(Users users, Item item) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " where r.users = :users and r.item = :item", Review.class)
                .setParameter("users", users)
                .setParameter("item", item)
                .getResultList()
                .stream().findAny();
    }

    public List<Review> findAllByUserYear(Users users, int year) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " where r.users = :users and year(r.date) = :year and r.status = 'DONE'", Review.class)
                .setParameter("users", users)
                .setParameter("year", year)
                .getResultList();
    }

    public Long countByUserYear(Users users, int year) {
        return em.createQuery(
                        "select count(r)" +
                                " from Review r" +
                                " where r.users = :users and year(r.date) = :year and r.status = 'DONE'", Long.class)
                .setParameter("users", users)
                .setParameter("year", year)
                .getSingleResult();

    }

    public List<Object[]> countByUsers(Users users) {
        return em.createQuery(
                        "select year(r.date), count(r)" +
                                " from Review r" +
                                " where r.users = :users and r.status = 'DONE'" +
                                " group by year(r.date)" +
                                " order by year(r.date) desc ")
                .setParameter("users", users)
                .getResultList();
    }

    //리뷰 목록을 보여줄 때 item 정보(제목, 사진 등)도 함께 보여주기 때문에 item도 fetch join
    public List<Review> findAllBookReviewByUser(Users users, int start, int display) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Book'", Review.class)
                .setParameter("users", users)
                .setFirstResult(start)
                .setMaxResults(display)
                .getResultList();
    }

    //리뷰 목록을 보여줄 때 item 정보(제목, 사진 등)도 함께 보여주기 때문에 item도 fetch join
    public List<Review> findBookReviewByUserTitle(Users users, String title, int start, int display) {
        if (title == null)
            return findAllBookReviewByUser(users, start, display);
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Book' and i.title like concat('%', :title, '%')", Review.class)
                .setParameter("users", users)
                .setParameter("title", title)
                .setFirstResult(start)
                .setMaxResults(display)
                .getResultList();
    }

    public List<Review> findAllMovieReviewByUser(Users users, int start, int display) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Movie'", Review.class)
                .setParameter("users", users)
                .setFirstResult(start)
                .setMaxResults(display)
                .getResultList();
    }

    public List<Review> findMovieReviewByUserTitle(Users users, String title, int start, int display) {
        if (title == null)
            return findAllMovieReviewByUser(users, start, display);
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Movie' and i.title like concat('%', :title, '%')", Review.class)
                .setParameter("users", users)
                .setParameter("title", title)
                .setFirstResult(start)
                .setMaxResults(display)
                .getResultList();
    }
}
