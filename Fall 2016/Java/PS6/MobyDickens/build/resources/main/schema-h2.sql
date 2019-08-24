--
-- Create the book table
--
CREATE TABLE public.book
(
  id integer PRIMARY KEY ,
  title character varying,
  isbn bigint,
  author character varying,
  date_published TIMESTAMP,
  genre character varying,
  price numeric
);

DROP SEQUENCE public.book_id_seq;
CREATE SEQUENCE public.book_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;



