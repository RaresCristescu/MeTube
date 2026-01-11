DO $body$
DECLARE
	user_admin_id UUID;
	role_admin_id UUID;
BEGIN
	CREATE TABLE products
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		name CHARACTER VARYING(100) NOT NULL,
		description CHARACTER VARYING(500) NOT NULL,
		price BIGINT NOT NULL,
		is_active BOOLEAN NOT NULL DEFAULT TRUE,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT product_pk primary key (id)
	);
	
	CREATE TABLE inventories
	(
		product_id UUID NOT NULL,
		total_quantity NUMERIC(24) NOT NULL,
		reserved_quantity NUMERIC(24),
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT inventories_pk primary key (product_id),
		CONSTRAINT inventories_product_fk FOREIGN KEY (product_id) REFERENCES "products"(id)
	);
	
	CREATE TABLE carts
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		user_id UUID NOT NULL,
		status CHARACTER VARYING(20),
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT carts_pk primary key (id),
		CONSTRAINT carts_user_fk FOREIGN KEY (user_id) REFERENCES "users"(id)
	);
	
	CREATE TABLE cart_product
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		cart_id UUID NOT NULL,
		product_id UUID NOT NULL,
		quantity NUMERIC(24),
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT cart_product_pk primary key (id),
		CONSTRAINT cart_product_cart_fk FOREIGN KEY (cart_id) REFERENCES "carts"(id),
		CONSTRAINT cart_product_product_fk FOREIGN KEY (product_id) REFERENCES "products"(id),
		CONSTRAINT cart_product_unique UNIQUE (cart_id, product_id),
        CONSTRAINT cart_quantity_bigger_then_1 CHECK (quantity >= 1)
	);
	
END
$body$ language plpgsql;