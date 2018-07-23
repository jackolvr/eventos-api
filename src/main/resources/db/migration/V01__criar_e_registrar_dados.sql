CREATE TABLE evento (
  codigo bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  data varchar(255) NOT NULL,
  horario varchar(255) NOT NULL,
  local varchar(255) NOT NULL,
  nome varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO evento (codigo, data, horario, local, nome) VALUES (1, '25/07/2018', '8:00', 'Univasf', 'Teste de PUT');
INSERT INTO evento (codigo, data, horario, local, nome) VALUES (2, '26/07/2018', '9:00', 'Uneb', 'Reunião dos mestres');
INSERT INTO evento (codigo, data, horario, local, nome) VALUES (3, '27/07/2018', '13:00', 'Facape', 'Palestra Spring');