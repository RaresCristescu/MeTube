package com.app.data.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Message;
import com.app.data.entity.Role;
import com.app.data.enums.RoleEnum;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID>, JpaSpecificationExecutor<Message> {
}
