package whyzpotato.myreview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.Users;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public void save(Review review) {
        if (review.getId() == null)
            em.persist(review);
        else
            em.merge(review);
    }

    public void delete(Long id) {
        if (findById(id) != null)
            em.remove(id);
    }

    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
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

    //리뷰 목록을 보여줄 때 item 정보(제목, 사진 등)도 함께 보여주기 때문에 item도 fetch join
    public List<Review> findAllBookReviewByUser(Users users) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Book'", Review.class)
                .setParameter("users", users)
                .getResultList();
    }

    public List<Review> findAllMovieReviewByUser(Users users) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.users = :users and type(i) = 'Movie'", Review.class)
                .setParameter("users", users)
                .getResultList();
    }

}
