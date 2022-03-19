package com.indrachrsm.ewallet.systemparameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Integer> {
    SystemParameter findByName(String name);
}
