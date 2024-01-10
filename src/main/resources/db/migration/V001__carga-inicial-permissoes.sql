create table public.permissao (
	id bigserial primary key,
	nome varchar(30) not null
);

INSERT INTO public.permissao(nome) VALUES('ROLE_USUARIO');
