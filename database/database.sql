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
    original_name character varying(255)
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
    name character varying(100) NOT NULL
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
    date_added timestamp without time zone NOT NULL
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
1	9	1	\N	PENDING
2	7	4	\N	ACTIVE
3	9	2	\N	PENDING
\.


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.courses (course_id, name) FROM stdin;
2	Programowanie 2
3	Programowanie 3
1	Programowanie 5
4	Test course
\.


--
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.files (file_id, url, date_added, solution_id, original_name) FROM stdin;
4	http://localhost:8080/f20e52c1-1a0e-4fc0-a0db-e9eba15dd520-3aaf9148a901c547e1306a00b3d9164b.jpg	2024-06-14 14:08:53.722	6	3aaf9148a901c547e1306a00b3d9164b.jpg
5	http://localhost:8080/5c1f7d6c-e162-4def-9761-bb3f7eb617d1-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 14:08:53.722	6	502d1bc3133159b48d5df895c5eb83eb.jpg
6	http://localhost:8080/a7c76e7d-5eef-45e9-8f5f-8ab61d8ef96e-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 14:08:53.722	6	559f276d64e08ed43cfa78d418853882.jpg
7	http://localhost:8080/eba9f2ff-3458-48e1-96cd-aed26d7523cc-3aaf9148a901c547e1306a00b3d9164b.jpg	2024-06-14 14:09:24.796	7	3aaf9148a901c547e1306a00b3d9164b.jpg
8	http://localhost:8080/419b86b3-24a3-4e62-bd14-7ee665fb7480-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 14:09:24.796	7	502d1bc3133159b48d5df895c5eb83eb.jpg
9	http://localhost:8080/ae9ca725-3e1a-46c0-89dd-8dbcfc3563f2-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 14:09:24.796	7	559f276d64e08ed43cfa78d418853882.jpg
10	http://localhost:8080/96ccb73f-3fe8-4a42-bc12-2adbfbb0bd9e-3aaf9148a901c547e1306a00b3d9164b.jpg	2024-06-14 15:21:16.427	8	3aaf9148a901c547e1306a00b3d9164b.jpg
11	http://localhost:8080/e75c5628-c264-42ab-8ace-5eae8b053e59-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 15:21:16.427	8	502d1bc3133159b48d5df895c5eb83eb.jpg
12	http://localhost:8080/025d9a7f-fd87-4137-b483-df20e42f1c60-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 15:21:16.427	8	559f276d64e08ed43cfa78d418853882.jpg
13	http://localhost:8080/ed63ec76-f671-4936-a148-579511e7729f-3aaf9148a901c547e1306a00b3d9164b.jpg	2024-06-14 15:34:36.499	9	3aaf9148a901c547e1306a00b3d9164b.jpg
14	http://localhost:8080/ad1b81b3-6a3e-44ae-989c-eb3f0f135b89-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 15:34:36.499	9	502d1bc3133159b48d5df895c5eb83eb.jpg
15	http://localhost:8080/1a3aeb26-8762-479f-9c31-2df150fe2340-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 15:34:36.499	9	559f276d64e08ed43cfa78d418853882.jpg
16	http://localhost:8080/449d5653-1f11-47d5-85e9-635085fe77a6-3aaf9148a901c547e1306a00b3d9164b.jpg	2024-06-14 15:35:09.844	10	3aaf9148a901c547e1306a00b3d9164b.jpg
17	http://localhost:8080/cccf8af8-f2ae-42fb-9ef3-e5d916b2d3ff-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 15:35:09.844	10	502d1bc3133159b48d5df895c5eb83eb.jpg
18	http://localhost:8080/32ffb99e-835f-4e07-9f9c-dd5445961a6b-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 15:35:09.844	10	559f276d64e08ed43cfa78d418853882.jpg
24	http://localhost:8080/2e5354a5-2e76-4450-9bad-536392605ea4-502d1bc3133159b48d5df895c5eb83eb - Copy.jpg	2024-06-14 15:39:23.585	12	502d1bc3133159b48d5df895c5eb83eb - Copy.jpg
25	http://localhost:8080/cdb811c4-17d5-4fdb-913e-1ea26da70d40-502d1bc3133159b48d5df895c5eb83eb.jpg	2024-06-14 15:39:23.585	12	502d1bc3133159b48d5df895c5eb83eb.jpg
26	http://localhost:8080/6ae7bf33-9f8c-4478-9409-cb02c19917dd-559f276d64e08ed43cfa78d418853882.jpg	2024-06-14 15:39:23.585	12	559f276d64e08ed43cfa78d418853882.jpg
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.groups (group_id, course_id, name) FROM stdin;
\.


--
-- Data for Name: solutions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solutions (solution_id, course_member_id, task_id, date_added) FROM stdin;
6	3	1	2024-06-14 14:08:53.722
7	3	1	2024-06-14 14:09:24.796
8	3	1	2024-06-14 15:21:16.427
9	3	1	2024-06-14 15:34:36.499
10	3	1	2024-06-14 15:35:09.844
12	3	1	2024-06-14 15:39:23.585
\.


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tasks (task_id, course_id, name, description, due_date) FROM stdin;
1	2	dupa		2024-06-09 17:19:11.948
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, email, password, name, surname, role, avatar) FROM stdin;
6	admin@admin.com	$2a$10$InhIOXRHU48BP5QBD3w80OOjJSjb6gNnaQFpIj3RCBR0zeDlyw8n.	admin	admin	ROLE_ADMIN	\N
8	test2@test2.com	$2a$10$hF9oolGZGJjAvmm2FxTDM.WWUfsKyZ2EVVpRAqszU9UPwyRJ3REpK	Test2	Testy2	ROLE_STUDENT	\N
9	test_user@test.com	$2a$10$WTE3W171pXy4hOQzPwBOXefXUR456z5kiPp9Ad.ChH4Ce/KtWiY/K	Test	User	ROLE_STUDENT	\N
7	test@test.com	$2a$10$prvXyVn7EADbmo1Wml5evOqyDRLgeisiFKUnnoGqwsnt5MP/DugUe	Test	Testy	ROLE_TEACHER	\N
\.


--
-- Name: course_members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_members_id_seq', 3, true);


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_id_seq', 4, true);


--
-- Name: files_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.files_file_id_seq', 26, true);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 1, false);


--
-- Name: solutions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solutions_id_seq', 12, true);


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tasks_id_seq', 1, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 9, true);


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

