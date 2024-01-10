//v3

create table public.investimento (
	id bigserial primary key,
	nome varchar(100) not null,
	tipo_id int4 not null,
	usuario_id int4 not null	
);

ALTER TABLE public.investimento ADD CONSTRAINT fk_investimento_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(codigo) ON DELETE CASCADE;


//v4

create table public.usuario_permissoes (
	id bigserial primary key,
	usuario_codigo int4 not null,
	permissoes_id int4 not null	
);

ALTER TABLE public.usuario_permissoes ADD CONSTRAINT fk_usuario FOREIGN KEY (usuario_codigo) REFERENCES public.usuario(codigo);

ALTER TABLE public.usuario_permissoes ADD CONSTRAINT fk_permissao FOREIGN KEY (permissoes_id) REFERENCES public.permissao(id);

--insert into usuario_permissoes(usuario_codigo, permissoes_id) values (1, 1);

//v5

create table public.investimento_tipo (
	id bigserial primary key,
	nome varchar(100) not null,
	tipo varchar(4) not null UNIQUE,
	classe_id varchar(4) int4 not null		
);

ALTER TABLE public.investimento ADD CONSTRAINT fk_investimento_tipo FOREIGN KEY (tipo_id) REFERENCES public.investimento_tipo(id);
