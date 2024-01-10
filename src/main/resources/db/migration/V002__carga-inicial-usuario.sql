create table public.usuario (
	codigo bigserial primary key,	
	ativo boolean not null,
	celular varchar(25),
	email varchar(100) not null,
	idioma varchar(30) not null,
	login varchar(5) not null UNIQUE,
	nascimento date not null,
	nome varchar(50) not null,
	senha varchar(100) not null
);

INSERT INTO public.usuario
(ativo, celular, email, idioma, login, nascimento, nome, senha)
VALUES('true', '48999003500', 'joao@gmail.com', 'PT', 'joaon', '07-04-1986', 'Joao Nobody', 'joao1234');
