package com.pecaja.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pecaja.app.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByNome(String nome);
}
