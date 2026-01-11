package com.app.data.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.app.data.entity.Cart;
import com.app.data.entity.CartProduct;
import com.app.data.entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {
}
