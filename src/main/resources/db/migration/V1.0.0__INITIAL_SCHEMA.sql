DO $body$
DECLARE
	admin_user_id UUID;
	role_admin_id INT;
BEGIN
	CREATE TABLE users
	(
		id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
		login CHARACTER VARYING(100) NOT NULL,
		email CHARACTER VARYING(100) NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		password character varying(60) NOT NULL,
		CONSTRAINT user_login_unique UNIQUE (login),
		CONSTRAINT user_email_unique UNIQUE (email)--,
		--CONSTRAINT password_length_check CHECK (char_length(password::text) = 60)
	);
	
	CREATE TABLE "roles" (
		id SERIAL PRIMARY KEY,
		name CHARACTER VARYING(50) NOT NULL,
		description CHARACTER VARYING(200),
		creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
		modified TIMESTAMP WITH TIME ZONE
	);
	
	CREATE TABLE "user_role" (
		id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
		user_id UUID NOT NULL,
		role_id INT NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
		modified TIMESTAMP WITH TIME ZONE,
		CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES "users" (id),
		CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES "roles" (id)
	);
	
	admin_user_id := gen_random_uuid();
	INSERT INTO users (id, login, email, creation, password)
	VALUES(admin_user_id, 'admin', 'admin@metube.com', NOW(), 'admin');
	
	role_admin_id := 1;
	INSERT INTO roles (id, name, description)
	VALUES(role_admin_id, 'ROLE_ADMIN', 'Admin role for user.');
	
	INSERT INTO user_role (user_id, role_id )
	VALUES(admin_user_id, role_admin_id);
	
END
$body$ language plpgsql;