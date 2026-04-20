package com.mercado.admin.repository;

import com.mercado.admin.entity.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Unidad 2.2.1: Spring Data JPA
 * Al extender JpaRepository, Spring genera automáticamente los métodos
 * findAll, findById, save, deleteById, etc. sin escribir SQL.
 */
public interface DuenoRepository extends JpaRepository<Dueno, String> {}