--create or replace function validaChavePessoa() 
--	returns trigger
--	language plpgsql
--	as $$
	
--	declare existe integer;

--begin 
	
--	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
--	if (existe <= 0) then
--	existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_id);
--	if (existe <= 0) then
--		raise exception 'Não foi encontrado nehum id ou fk para pessoa para associção';
--		end if;
--	end if;
--	return new;
--end;
--$$


--create trigger validaChavePessoaAvaliacaoProduto
--before update
--on avaliacao_produto
--for each row
--execute procedure validaChavePessoa();
