package com.maf.user.repository;

import com.maf.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByRole(User.Role role);

}
