create table public.usuario (
	codigo bigserial primary key,	
	ativo boolean not null,
	celular varchar(25),
	email varchar(100) not null UNIQUE,
	idioma varchar(30) not null,
	login varchar(30) not null UNIQUE,
	nascimento date not null,
	nome varchar(50) not null,
	senha varchar(100) not null
);

