create table public.usuario_permissoes (
	id bigserial primary key,
	usuario_codigo int4 not null,
	permissoes_id int4 not null	
);

ALTER TABLE public.usuario_permissoes ADD CONSTRAINT fk_usuario FOREIGN KEY (usuario_codigo) REFERENCES public.usuario(codigo);

ALTER TABLE public.usuario_permissoes ADD CONSTRAINT fk_permissao FOREIGN KEY (permissoes_id) REFERENCES public.permissao(id);
