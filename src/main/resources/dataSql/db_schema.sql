create database record_to_doctor;

create table patient(
    id bigserial primary key,
    first_name text not null,
    last_name text not null,
    gender bool not null,
    birth_date date not null
    );

create table clinic(
    id bigserial primary key,
    name text not null,
    address text not null,
    unique (name, address)
    );

create table specialization(
    id bigserial primary key,
    specialization text not null unique);

create table doctor(
    id bigserial primary key,
    first_name text not null,
    last_name text not null,
    gender bool not null,
    birth_date date not null
    );

create table place_of_work(
    id bigserial primary key,
    date_of_start_work date not null,
    doctor_id bigint references doctor(id) not null,
    clinic_id bigint references clinic(id) not null,
    specialization_id bigint references specialization(id)
    );

create table medical_card(
    id bigserial primary key,
    record_date date not null,
    doctor_id bigint references doctor(id) not null,
    patient_id bigint references patient(id) not null,
    record text not null
    );

create table working_day(
    id bigserial primary key,
    start_time timestamp not null,
    end_time timestamp not null,
    doctor_id bigint references doctor(id) not null,
    clinic_id bigint references clinic(id) not null
    );

create table vacation(
    id bigserial primary key,
    start_time timestamp not null,
    end_time timestamp not null,
    doctor_id bigint references doctor(id) not null,
    clinic_id bigint references clinic(id) not null
    );

create table appointment_to_doctor (
    id bigserial primary key,
    start_time timestamp not null,
    doctor_id bigint references doctor(id) not null,
    specialization_id bigint references specialization(id) not null,
    patient_id bigint references patient(id) not null,
    clinic_id bigint references clinic(id) not null,
    status text not null
    );

create table doctor_specialization(
    id bigserial primary key,
    doctor_id bigint references doctor(id) not null,
    specialization_id bigint references specialization(id),
    unique(doctor_id,specialization_id)
    );

create table users (
    id bigserial primary key,
    login text not null,
    password text not null,
    enabled bool not null
);

create table roles(
    id bigserial primary key,
    name text not null
);

create table user_roles (
    user_id bigint references users(id),
    roles_id bigint references roles(id),
    PRIMARY KEY (user_id, roles_id)
);

create index medical_card_patient on medical_card (patient_id);