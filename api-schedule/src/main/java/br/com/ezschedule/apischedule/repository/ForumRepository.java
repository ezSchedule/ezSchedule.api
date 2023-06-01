package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<ForumPost, Integer> {

    @Query("Select f from ForumPost f where f.typeMessage = :content")
    List<ForumPost> findBasedOnType(String content);

    @Query("Select f from ForumPost f where f.condominium.id = :id")
    List<ForumPost> findBasedOnCondominiumId(int id);

}
