package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
