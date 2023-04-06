package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<ForumPost, Integer> {

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE ForumPosts SET  WHERE email = ?1 ", nativeQuery = true)
//    Object logoutUser();
}
