--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: role_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.role_enum AS ENUM (
    'ROLE_STUDENT',
    'ROLE_TEACHER',
    'ROLE_PENDING',
    'ROLE_ADMIN'
);


ALTER TYPE public.role_enum OWNER TO postgres;

--
-- Name: status_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.status_enum AS ENUM (
    'PENDING',
    'ACTIVE'
);


ALTER TYPE public.status_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: course_members; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course_members (
    course_member_id integer NOT NULL,
    user_id integer NOT NULL,
    course_id integer NOT NULL,
    group_id integer,
    status public.status_enum NOT NULL
);


ALTER TABLE public.course_members OWNER TO postgres;

--
-- Name: course_members_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.course_members_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.course_members_id_seq OWNER TO postgres;

--
-- Name: course_members_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.course_members_id_seq OWNED BY public.course_members.course_member_id;


--
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courses (
    course_id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.courses OWNER TO postgres;

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.courses_id_seq OWNER TO postgres;

--
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courses_id_seq OWNED BY public.courses.course_id;


--
-- Name: files; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.files (
    file_id integer NOT NULL,
    url character varying(255) NOT NULL,
    date_added timestamp without time zone NOT NULL,
    solution_id integer NOT NULL,
    original_name character varying(255),
    db_filename character varying(255)
);


ALTER TABLE public.files OWNER TO postgres;

--
-- Name: files_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.files_file_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.files_file_id_seq OWNER TO postgres;

--
-- Name: files_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.files_file_id_seq OWNED BY public.files.file_id;


--
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groups (
    group_id integer NOT NULL,
    course_id integer NOT NULL,
    name character varying(128)
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- Name: groups_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.groups_group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.groups_group_id_seq OWNER TO postgres;

--
-- Name: groups_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.groups_group_id_seq OWNED BY public.groups.group_id;


--
-- Name: solutions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solutions (
    solution_id integer NOT NULL,
    course_member_id integer NOT NULL,
    task_id integer NOT NULL,
    date_added timestamp without time zone NOT NULL,
    date_graded timestamp without time zone,
    grade integer,
    grade_description character varying
);


ALTER TABLE public.solutions OWNER TO postgres;

--
-- Name: solutions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.solutions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.solutions_id_seq OWNER TO postgres;

--
-- Name: solutions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.solutions_id_seq OWNED BY public.solutions.solution_id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tasks (
    task_id integer NOT NULL,
    course_id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255),
    due_date timestamp without time zone
);


ALTER TABLE public.tasks OWNER TO postgres;

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tasks_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tasks_id_seq OWNER TO postgres;

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tasks_id_seq OWNED BY public.tasks.task_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    name character varying(100) NOT NULL,
    surname character varying(100) NOT NULL,
    role public.role_enum NOT NULL,
    avatar character varying(128)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.user_id;


--
-- Name: course_members course_member_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_members ALTER COLUMN course_member_id SET DEFAULT nextval('public.course_members_id_seq'::regclass);


--
-- Name: courses course_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses ALTER COLUMN course_id SET DEFAULT nextval('public.courses_id_seq'::regclass);


--
-- Name: files file_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files ALTER COLUMN file_id SET DEFAULT nextval('public.files_file_id_seq'::regclass);


--
-- Name: groups group_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups ALTER COLUMN group_id SET DEFAULT nextval('public.groups_group_id_seq'::regclass);


--
-- Name: solutions solution_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions ALTER COLUMN solution_id SET DEFAULT nextval('public.solutions_id_seq'::regclass);


--
-- Name: tasks task_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks ALTER COLUMN task_id SET DEFAULT nextval('public.tasks_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: course_members; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course_members (course_member_id, user_id, course_id, group_id, status) FROM stdin;
7	17	6	\N	ACTIVE
8	17	7	\N	ACTIVE
9	17	8	\N	ACTIVE
10	18	9	\N	ACTIVE
11	19	10	\N	ACTIVE
15	12	6	\N	PENDING
17	13	7	\N	PENDING
19	14	8	\N	PENDING
22	16	9	\N	PENDING
12	11	10	2	PENDING
14	12	10	2	PENDING
16	13	10	2	PENDING
18	14	10	3	PENDING
20	15	10	3	PENDING
21	16	10	3	PENDING
23	13	9	\N	PENDING
\.


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.courses (course_id, name) FROM stdin;
6	Programowanie 1
7	Programowanie 2
8	Programowanie 3
9	Bazy danych
10	Aplikacje mobilne
\.


--
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.files (file_id, url, date_added, solution_id, original_name, db_filename) FROM stdin;
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.groups (group_id, course_id, name) FROM stdin;
2	10	Grupa 1
3	10	Grupa 2
\.


--
-- Data for Name: solutions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solutions (solution_id, course_member_id, task_id, date_added, date_graded, grade, grade_description) FROM stdin;
\.


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (task_id, course_id, name, description, due_date) FROM stdin;
2	10	Zadanie nr. 1	Zadanie testowe kursu Aplikacje mobilne	2024-06-30 00:00:00
3	10	Zadanie nr. 2	Zadanie testowe kursu Aplikacje mobilne	2024-06-28 00:00:00
5	9	Zadanie nr. 1	Przykładowe zadanie	2024-06-27 00:00:00
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, email, password, name, surname, role, avatar) FROM stdin;
10	admin@admin.com	$2a$10$yJWWat.SienC2qAPzuI62eOJqpfYP/dnMlRv/C3ISaLf9DPBKZ29q	admin	admin	ROLE_ADMIN	\N
11	user1@test.com	$2a$10$BHM3vpcAHjbKtnGWSIiB/urNA8EVPEQqejTJxIyRZbwWF.mFZdLG2	Jan	Kowalski	ROLE_STUDENT	\N
12	user2@test.com	$2a$10$FouiVMmEtFW8GQKeyB.IquaLl5PWaiYajZNv5IRTtYQGI/s26vZCK	Tomasz	Nowak	ROLE_STUDENT	\N
13	user3@test.com	$2a$10$/s.bkf4B4bAi/DFNTsJbd.xVBKWIh4oxnflm0sM25Bl3j8qz1JfKG	Jacek	Placek	ROLE_STUDENT	\N
14	user4@test.com	$2a$10$Zjn/hJT4w59s5QzExM9/Q.e1y0zNi5WNgutkv1yr/4PJv0S53iPuK	Mariusz	Przeciętny	ROLE_STUDENT	\N
15	user5@test.com	$2a$10$huk6UX.Ep.rucqALjs/JL.NycIkd1xTwj6RbrAFFF7co5KpVNPVf6	Patryk	Niesamowity	ROLE_STUDENT	\N
16	user6@test.com	$2a$10$1RjFzDHLcpaXYrXGd70nIuhIj6Vyf9UC9.zcER6qZCjSYMkIxFnzK	Juliusz	Słowacki	ROLE_STUDENT	\N
17	teacher1@test.com	$2a$10$YP8q9xblyNhhqkCcQMNBaOpGQPeobVrLq1rags8XbXF22FzvHr3v2	Jan	Sobieski	ROLE_TEACHER	\N
18	teacher2@test.com	$2a$10$ohfCD02Uqjvz8EsvLITB.O7nVU7vilRJvyVizmUVlO04K32N9n0U6	Władysław	Krzywousty	ROLE_TEACHER	\N
19	teacher3@test.com	$2a$10$bWpA6AmQeOSJtOey3cMo7.lFUPHQZjjnzNxsKk600WaKkcTTovQvO	Paweł	Normalny	ROLE_TEACHER	\N
\.


--
-- Name: course_members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_members_id_seq', 23, true);


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_id_seq', 10, true);


--
-- Name: files_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.files_file_id_seq', 39, true);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 3, true);


--
-- Name: solutions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solutions_id_seq', 21, true);


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tasks_id_seq', 5, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 19, true);


--
-- Name: course_members course_members_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_members
    ADD CONSTRAINT course_members_pkey PRIMARY KEY (course_member_id);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (course_id);


--
-- Name: files files_file_path_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_file_path_key UNIQUE (url);


--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (file_id);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- Name: solutions solutions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_pkey PRIMARY KEY (solution_id);


--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (task_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: course_members course_members_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_members
    ADD CONSTRAINT course_members_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.courses(course_id);


--
-- Name: course_members course_members_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_members
    ADD CONSTRAINT course_members_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(group_id);


--
-- Name: course_members course_members_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_members
    ADD CONSTRAINT course_members_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- Name: files filesfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT filesfk FOREIGN KEY (solution_id) REFERENCES public.solutions(solution_id);


--
-- Name: groups groups_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.courses(course_id);


--
-- Name: solutions solutions_course_member_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_course_member_id_fkey FOREIGN KEY (course_member_id) REFERENCES public.course_members(course_member_id);


--
-- Name: solutions solutions_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(task_id);


--
-- Name: tasks tasks_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.courses(course_id);


--
-- PostgreSQL database dump complete
--

