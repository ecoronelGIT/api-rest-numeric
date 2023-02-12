--numerics
CREATE TABLE IF NOT EXISTS public.numeric_logs
(
    id SERIAL PRIMARY KEY,
    method varchar(10) NOT NULL,
    url varchar NOT NULL,
    request varchar,
    request_headers varchar,
    response varchar,
    response_status int NOT NULL,
    response_headers varchar NOT NULL,
    duration bigint,
    created_at timestamp NOT NULL
);
