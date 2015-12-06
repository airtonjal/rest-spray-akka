CREATE TABLE public.project
(
  pid serial PRIMARY KEY,
  uid integer NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  meta text
)
