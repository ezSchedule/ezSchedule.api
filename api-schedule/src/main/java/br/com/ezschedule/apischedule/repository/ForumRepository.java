package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<ForumPost, Integer> {

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE ForumPosts SET  WHERE email = ?1 ", nativeQuery = true)
//    Object logoutUser();

    @Query("Select t from Tenant t where subscribed = 1")
    List<Tenant> findSubscribedTenants();

    @Query("Select f from ForumPost f where f.typeMessage = :content")
    List<ForumPost> findBasedOnType(String content);

}
