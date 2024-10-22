DO $body$
BEGIN
	CREATE TABLE users
	(
		id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
		login character varying(100) NOT NULL,
		creation timestamp with time zone NOT NULL,
		modified timestamp with time zone,
		expires timestamp with time zone,
		password character varying(60) NOT NULL,
		CONSTRAINT user_login_unique UNIQUE (login),
		CONSTRAINT password_length_check CHECK (char_length(password::text) = 60)
	);
END
$body$ language plpgsql;