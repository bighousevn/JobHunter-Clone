package vn.bighousevn.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.bighousevn.jobhunter.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account ,Long>, JpaSpecificationExecutor<Account>{


}
