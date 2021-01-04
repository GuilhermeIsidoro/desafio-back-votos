insert into desafio_votos.agenda (id, agenda_name, agenda_description) values (1, 'TEST AGENDA', 'TEST AGENDA DESCRIPTION');
insert into desafio_votos.agenda (id, agenda_name, agenda_description) values (2, 'SECOND TEST AGENDA', 'SECOND TEST AGENDA DESCRIPTION');

insert into desafio_votos.voting_session (id, start_time, end_time, agenda_id) values (1, TO_DATE('2021-01-04 07:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-01-04 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);
insert into desafio_votos.voting_session (id, start_time, end_time, agenda_id) values (2, TO_DATE('2021-01-04 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-01-04 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);

insert into desafio_votos.vote_type (id, vote_description,agenda_id) values (1, 'YES',1), (2, 'NO',1);

insert into desafio_votos.associate (id,name) values
	 ('09854960021','JOAO'),
	 ('20596098014','EPAMINONDAS'),
	 ('02914457057', 'GUILHERME');
	 
insert into desafio_votos.vote (id, agenda_id,associate_id,voting_date,vote_type_id) values
	 (1, 1,'09854960021','2021-01-04 08:11:45',1),
	 (2, 1,'20596098014','2021-01-04 08:56:17',1);