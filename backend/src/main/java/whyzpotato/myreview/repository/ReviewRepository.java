package whyzpotato.myreview.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import whyzpotato.myreview.domain.Review;
import whyzpotato.myreview.domain.User;

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

    public void delete(Review review) {
        if (review.getId() != null)
            em.remove(review.getId());
    }

    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findAll() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    //리뷰 상세를 보여줄 때 item 정보도 함께 보여주기 때문에 item도 fetch join
    public List<Review> findAllBookReviewByUser(User user) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.user = :user and type(i) = 'Book'", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Review> findLikeBookByUser(User user) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.user = :user and type(i) = 'Book' and r.status = 'LIKE'", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Review> findAllMovieReviewByUser(User user) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.user = :user and type(i) = 'Movie'", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Review> findLikeMovieByUser(User user) {
        return em.createQuery(
                        "select r" +
                                " from Review r" +
                                " join fetch r.item i" +
                                " where r.user = :user and type(i) = 'Movie' and r.status = 'LIKE'", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

}
