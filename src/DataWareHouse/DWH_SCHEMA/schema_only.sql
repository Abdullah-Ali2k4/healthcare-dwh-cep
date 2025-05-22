--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: dim_hospital; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dim_hospital (
    hospital_id integer NOT NULL,
    hospital_name text,
    zip text,
    contact_info text,
    address text
);


ALTER TABLE public.dim_hospital OWNER TO postgres;

--
-- Name: dim_medication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dim_medication (
    med_id integer NOT NULL,
    med_name text,
    supplier_id integer
);


ALTER TABLE public.dim_medication OWNER TO postgres;

--
-- Name: dim_outbreak; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dim_outbreak (
    outbreak_id integer NOT NULL,
    disease_name text,
    zip text,
    number_of_outbreak integer,
    outbreak_monthyear text
);


ALTER TABLE public.dim_outbreak OWNER TO postgres;

--
-- Name: dim_outbreak_outbreak_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.dim_outbreak ALTER COLUMN outbreak_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.dim_outbreak_outbreak_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: dim_patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dim_patient (
    patient_id integer NOT NULL,
    full_name text,
    gender text,
    dob date
);


ALTER TABLE public.dim_patient OWNER TO postgres;

--
-- Name: dim_supplier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dim_supplier (
    id integer NOT NULL,
    name text,
    contact_info text,
    address text
);


ALTER TABLE public.dim_supplier OWNER TO postgres;

--
-- Name: fact_medicine; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fact_medicine (
    med_id integer NOT NULL,
    current_stock integer NOT NULL,
    used_stock integer NOT NULL,
    last_updated date DEFAULT CURRENT_DATE NOT NULL,
    CONSTRAINT fact_medicine_current_stock_check CHECK ((current_stock >= 0)),
    CONSTRAINT fact_medicine_used_stock_check CHECK ((used_stock >= 0))
);


ALTER TABLE public.fact_medicine OWNER TO postgres;

--
-- Name: fact_visit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fact_visit (
    visit_id integer NOT NULL,
    patient_id integer,
    visit_date date NOT NULL,
    total_cost numeric(10,2) NOT NULL,
    diagnosis_name text,
    hospital_id integer,
    age_group text
);


ALTER TABLE public.fact_visit OWNER TO postgres;

--
-- Name: fact_visit_visit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fact_visit_visit_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fact_visit_visit_id_seq OWNER TO postgres;

--
-- Name: fact_visit_visit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fact_visit_visit_id_seq OWNED BY public.fact_visit.visit_id;


--
-- Name: fact_visitpermonth; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fact_visitpermonth (
    number_of_visit integer,
    hospital_id integer,
    month_year text NOT NULL
);


ALTER TABLE public.fact_visitpermonth OWNER TO postgres;

--
-- Name: fact_visit visit_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visit ALTER COLUMN visit_id SET DEFAULT nextval('public.fact_visit_visit_id_seq'::regclass);


--
-- Name: dim_hospital dim_hospital_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_hospital
    ADD CONSTRAINT dim_hospital_pkey PRIMARY KEY (hospital_id);


--
-- Name: dim_medication dim_medication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_medication
    ADD CONSTRAINT dim_medication_pkey PRIMARY KEY (med_id);


--
-- Name: dim_outbreak dim_outbreak_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_outbreak
    ADD CONSTRAINT dim_outbreak_pkey PRIMARY KEY (outbreak_id);


--
-- Name: dim_patient dim_patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_patient
    ADD CONSTRAINT dim_patient_pkey PRIMARY KEY (patient_id);


--
-- Name: dim_supplier dim_supplier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_supplier
    ADD CONSTRAINT dim_supplier_pkey PRIMARY KEY (id);


--
-- Name: fact_medicine fact_medicine_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_medicine
    ADD CONSTRAINT fact_medicine_pkey PRIMARY KEY (med_id, last_updated);


--
-- Name: fact_visit fact_visit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visit
    ADD CONSTRAINT fact_visit_pkey PRIMARY KEY (visit_id);


--
-- Name: fact_visitpermonth fact_visitpermonth_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visitpermonth
    ADD CONSTRAINT fact_visitpermonth_pkey PRIMARY KEY (month_year);


--
-- Name: dim_medication dim_medication_supplier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_medication
    ADD CONSTRAINT dim_medication_supplier_id_fkey FOREIGN KEY (supplier_id) REFERENCES public.dim_supplier(id);


--
-- Name: fact_medicine fact_medicine_med_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_medicine
    ADD CONSTRAINT fact_medicine_med_id_fkey FOREIGN KEY (med_id) REFERENCES public.dim_medication(med_id);


--
-- Name: fact_visit fact_visit_hospital_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visit
    ADD CONSTRAINT fact_visit_hospital_id_fkey FOREIGN KEY (hospital_id) REFERENCES public.dim_hospital(hospital_id);


--
-- Name: fact_visit fact_visit_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visit
    ADD CONSTRAINT fact_visit_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.dim_patient(patient_id);


--
-- Name: fact_visitpermonth fact_visitpermonth_hospital_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fact_visitpermonth
    ADD CONSTRAINT fact_visitpermonth_hospital_id_fkey FOREIGN KEY (hospital_id) REFERENCES public.dim_hospital(hospital_id);


--
-- Name: dim_outbreak fk_outbreak_monthyear; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dim_outbreak
    ADD CONSTRAINT fk_outbreak_monthyear FOREIGN KEY (outbreak_monthyear) REFERENCES public.fact_visitpermonth(month_year);


--
-- PostgreSQL database dump complete
--

