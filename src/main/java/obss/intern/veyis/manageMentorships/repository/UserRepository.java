package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    //List<Users> findAll();



}