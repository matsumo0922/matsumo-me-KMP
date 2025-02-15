SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;


CREATE
EXTENSION IF NOT EXISTS "pgsodium" WITH SCHEMA "pgsodium";






COMMENT
ON SCHEMA "public" IS 'standard public schema';



CREATE
EXTENSION IF NOT EXISTS "pg_graphql" WITH SCHEMA "graphql";






CREATE
EXTENSION IF NOT EXISTS "pg_stat_statements" WITH SCHEMA "extensions";






CREATE
EXTENSION IF NOT EXISTS "pgcrypto" WITH SCHEMA "extensions";






CREATE
EXTENSION IF NOT EXISTS "pgjwt" WITH SCHEMA "extensions";






CREATE
EXTENSION IF NOT EXISTS "supabase_vault" WITH SCHEMA "vault";






CREATE
EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA "extensions";



CREATE TYPE "public"."article_source" AS ENUM (
    'markdown',
    'qiita',
    'zenn'
);


ALTER TYPE "public"."article_source" OWNER TO "postgres";

SET
default_tablespace = '';

SET
default_table_access_method = "heap";


CREATE TABLE IF NOT EXISTS "public"."article"
(
    "id"
    integer
    NOT
    NULL,
    "source"
    "public"
    .
    "article_source"
    NOT
    NULL,
    "title"
    character
    varying
(
    255
) NOT NULL,
    "summary" "text",
    "published_at" timestamp without time zone,
    "created_at" timestamp
                             without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" timestamp
                             without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
    );


ALTER TABLE "public"."article" OWNER TO "postgres";


CREATE SEQUENCE IF NOT EXISTS "public"."article_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."article_id_seq" OWNER TO "postgres";


ALTER SEQUENCE "public"."article_id_seq" OWNED BY "public"."article"."id";



CREATE TABLE IF NOT EXISTS "public"."article_tag"
(
    "article_id"
    integer
    NOT
    NULL,
    "tag_id"
    integer
    NOT
    NULL,
    "created_at"
    timestamp
    without
    time
    zone
    DEFAULT
    CURRENT_TIMESTAMP
    NOT
    NULL
);


ALTER TABLE "public"."article_tag" OWNER TO "postgres";


CREATE TABLE IF NOT EXISTS "public"."markdown_article_detail"
(
    "id"
    integer
    NOT
    NULL,
    "article_id"
    integer
    NOT
    NULL,
    "source_url"
    character
    varying
(
    255
) NOT NULL,
    "content" "text",
    "rendered_content" "text",
    "is_published" boolean DEFAULT true NOT NULL,
    "last_modified" timestamp without time zone,
    "created_at" timestamp
                              without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" timestamp
                              without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
    );


ALTER TABLE "public"."markdown_article_detail" OWNER TO "postgres";


CREATE SEQUENCE IF NOT EXISTS "public"."markdown_article_detail_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."markdown_article_detail_id_seq" OWNER TO "postgres";


ALTER SEQUENCE "public"."markdown_article_detail_id_seq" OWNED BY "public"."markdown_article_detail"."id";



CREATE TABLE IF NOT EXISTS "public"."qiita_article_detail"
(
    "id"
    integer
    NOT
    NULL,
    "article_id"
    integer
    NOT
    NULL,
    "source_id"
    character
    varying
(
    50
) NOT NULL,
    "source_url" character varying
(
    255
) NOT NULL,
    "content" "text",
    "rendered_content" "text",
    "is_published" boolean DEFAULT true NOT NULL,
    "comments_count" integer,
    "likes_count" integer,
    "reactions_count" integer,
    "stocks_count" integer,
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" timestamp
                           without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
    );


ALTER TABLE "public"."qiita_article_detail" OWNER TO "postgres";


CREATE SEQUENCE IF NOT EXISTS "public"."qiita_article_detail_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."qiita_article_detail_id_seq" OWNER TO "postgres";


ALTER SEQUENCE "public"."qiita_article_detail_id_seq" OWNED BY "public"."qiita_article_detail"."id";



CREATE TABLE IF NOT EXISTS "public"."tag"
(
    "id"
    integer
    NOT
    NULL,
    "name"
    character
    varying
(
    50
) NOT NULL,
    "slug" character varying
(
    50
),
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" timestamp
                           without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
    );


ALTER TABLE "public"."tag" OWNER TO "postgres";


CREATE SEQUENCE IF NOT EXISTS "public"."tag_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."tag_id_seq" OWNER TO "postgres";


ALTER SEQUENCE "public"."tag_id_seq" OWNED BY "public"."tag"."id";



CREATE TABLE IF NOT EXISTS "public"."zenn_article_detail"
(
    "id"
    integer
    NOT
    NULL,
    "article_id"
    integer
    NOT
    NULL,
    "source_id"
    character
    varying
(
    50
) NOT NULL,
    "source_url" character varying
(
    255
) NOT NULL,
    "content" "text",
    "rendered_content" "text",
    "is_published" boolean,
    "emoji" character varying
(
    10
),
    "article_type" character varying
(
    50
),
    "created_at" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" timestamp
                           without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
    );


ALTER TABLE "public"."zenn_article_detail" OWNER TO "postgres";


CREATE SEQUENCE IF NOT EXISTS "public"."zenn_article_detail_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."zenn_article_detail_id_seq" OWNER TO "postgres";


ALTER SEQUENCE "public"."zenn_article_detail_id_seq" OWNED BY "public"."zenn_article_detail"."id";



ALTER TABLE ONLY "public"."article" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."article_id_seq"'::"regclass");



ALTER TABLE ONLY "public"."markdown_article_detail" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."markdown_article_detail_id_seq"'::"regclass");



ALTER TABLE ONLY "public"."qiita_article_detail" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."qiita_article_detail_id_seq"'::"regclass");



ALTER TABLE ONLY "public"."tag" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."tag_id_seq"'::"regclass");



ALTER TABLE ONLY "public"."zenn_article_detail" ALTER COLUMN "id" SET DEFAULT "nextval"('"public"."zenn_article_detail_id_seq"'::"regclass");



ALTER TABLE ONLY "public"."article"
    ADD CONSTRAINT "article_pkey" PRIMARY KEY ("id");



ALTER TABLE ONLY "public"."article_tag"
    ADD CONSTRAINT "article_tag_pkey" PRIMARY KEY ("article_id", "tag_id");



ALTER TABLE ONLY "public"."markdown_article_detail"
    ADD CONSTRAINT "markdown_article_detail_pkey" PRIMARY KEY ("id");



ALTER TABLE ONLY "public"."qiita_article_detail"
    ADD CONSTRAINT "qiita_article_detail_pkey" PRIMARY KEY ("id");



ALTER TABLE ONLY "public"."tag"
    ADD CONSTRAINT "tag_name_key" UNIQUE ("name");



ALTER TABLE ONLY "public"."tag"
    ADD CONSTRAINT "tag_pkey" PRIMARY KEY ("id");



ALTER TABLE ONLY "public"."zenn_article_detail"
    ADD CONSTRAINT "zenn_article_detail_pkey" PRIMARY KEY ("id");



ALTER TABLE ONLY "public"."article_tag"
    ADD CONSTRAINT "fk_article" FOREIGN KEY ("article_id") REFERENCES "public"."article"("id");



ALTER TABLE ONLY "public"."markdown_article_detail"
    ADD CONSTRAINT "fk_markdown_article" FOREIGN KEY ("article_id") REFERENCES "public"."article"("id");



ALTER TABLE ONLY "public"."qiita_article_detail"
    ADD CONSTRAINT "fk_qiita_article" FOREIGN KEY ("article_id") REFERENCES "public"."article"("id");



ALTER TABLE ONLY "public"."article_tag"
    ADD CONSTRAINT "fk_tag" FOREIGN KEY ("tag_id") REFERENCES "public"."tag"("id");



ALTER TABLE ONLY "public"."zenn_article_detail"
    ADD CONSTRAINT "fk_zenn_article" FOREIGN KEY ("article_id") REFERENCES "public"."article"("id");



ALTER
PUBLICATION "supabase_realtime" OWNER TO "postgres";


GRANT USAGE ON SCHEMA
"public" TO "postgres";
GRANT USAGE ON SCHEMA
"public" TO "anon";
GRANT USAGE ON SCHEMA
"public" TO "authenticated";
GRANT USAGE ON SCHEMA
"public" TO "service_role";



































































































































































































GRANT ALL
ON TABLE "public"."article" TO "anon";
GRANT ALL
ON TABLE "public"."article" TO "authenticated";
GRANT ALL
ON TABLE "public"."article" TO "service_role";



GRANT ALL
ON SEQUENCE "public"."article_id_seq" TO "anon";
GRANT ALL
ON SEQUENCE "public"."article_id_seq" TO "authenticated";
GRANT ALL
ON SEQUENCE "public"."article_id_seq" TO "service_role";



GRANT ALL
ON TABLE "public"."article_tag" TO "anon";
GRANT ALL
ON TABLE "public"."article_tag" TO "authenticated";
GRANT ALL
ON TABLE "public"."article_tag" TO "service_role";



GRANT ALL
ON TABLE "public"."markdown_article_detail" TO "anon";
GRANT ALL
ON TABLE "public"."markdown_article_detail" TO "authenticated";
GRANT ALL
ON TABLE "public"."markdown_article_detail" TO "service_role";



GRANT ALL
ON SEQUENCE "public"."markdown_article_detail_id_seq" TO "anon";
GRANT ALL
ON SEQUENCE "public"."markdown_article_detail_id_seq" TO "authenticated";
GRANT ALL
ON SEQUENCE "public"."markdown_article_detail_id_seq" TO "service_role";



GRANT ALL
ON TABLE "public"."qiita_article_detail" TO "anon";
GRANT ALL
ON TABLE "public"."qiita_article_detail" TO "authenticated";
GRANT ALL
ON TABLE "public"."qiita_article_detail" TO "service_role";



GRANT ALL
ON SEQUENCE "public"."qiita_article_detail_id_seq" TO "anon";
GRANT ALL
ON SEQUENCE "public"."qiita_article_detail_id_seq" TO "authenticated";
GRANT ALL
ON SEQUENCE "public"."qiita_article_detail_id_seq" TO "service_role";



GRANT ALL
ON TABLE "public"."tag" TO "anon";
GRANT ALL
ON TABLE "public"."tag" TO "authenticated";
GRANT ALL
ON TABLE "public"."tag" TO "service_role";



GRANT ALL
ON SEQUENCE "public"."tag_id_seq" TO "anon";
GRANT ALL
ON SEQUENCE "public"."tag_id_seq" TO "authenticated";
GRANT ALL
ON SEQUENCE "public"."tag_id_seq" TO "service_role";



GRANT ALL
ON TABLE "public"."zenn_article_detail" TO "anon";
GRANT ALL
ON TABLE "public"."zenn_article_detail" TO "authenticated";
GRANT ALL
ON TABLE "public"."zenn_article_detail" TO "service_role";



GRANT ALL
ON SEQUENCE "public"."zenn_article_detail_id_seq" TO "anon";
GRANT ALL
ON SEQUENCE "public"."zenn_article_detail_id_seq" TO "authenticated";
GRANT ALL
ON SEQUENCE "public"."zenn_article_detail_id_seq" TO "service_role";



ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON SEQUENCES  TO "postgres";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON SEQUENCES  TO "anon";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON SEQUENCES  TO "authenticated";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON SEQUENCES  TO "service_role";






ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON FUNCTIONS  TO "postgres";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON FUNCTIONS  TO "anon";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON FUNCTIONS  TO "authenticated";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON FUNCTIONS  TO "service_role";






ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON TABLES  TO "postgres";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON TABLES  TO "anon";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON TABLES  TO "authenticated";
ALTER
DEFAULT PRIVILEGES FOR ROLE "postgres" IN SCHEMA "public" GRANT ALL ON TABLES  TO "service_role";






























RESET
ALL;
