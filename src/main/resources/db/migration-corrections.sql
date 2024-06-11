//v3 

create table public.investimento (
	id bigserial primary key,
	nome varchar(100) not null,
	codigo varchar(20),
	tipo_id int4 not null,
	usuario_id int4 not null	
);

ALTER TABLE public.investimento ADD CONSTRAINT fk_investimento_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(codigo) ON DELETE CASCADE;