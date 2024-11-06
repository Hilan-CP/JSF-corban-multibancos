INSERT INTO `team`(`ID`,`NAME`) VALUES (1,'Time1'),(2,'Time2'),(3,'Time3'),(4,'Time4'),(5,'Time5');
INSERT INTO `bank`(`ID`,`CODE`,`SHORTNAME`) VALUES (1,'623','PAN'),(2,'336','C6'),(3,'318','BMG');
INSERT INTO `customer`(`BIRTHDATE`,`CPF`,`PHONE`,`NAME`) VALUES ('1997-05-30','02012038042','44987654322','Carla'),('1999-10-20','05396454008','44987654324','João'),('1998-05-10','11485608074','44987654323','José'),('2001-01-02','20366488007','44987654325','Jorge'),('1997-07-08','47627260019','44987654326','Lucas'),('2001-01-01','49083176070','44987654321','Ana');
INSERT INTO `employee`(`TEAM_ID`,`CPF`,`NAME`,`TYPE`) VALUES (2,'33701848009','Zenobia Martins','GESTOR'),(1,'64809569071','Joãozinho Josias','CONSULTOR'),(1,'67661033020','José Jorge','CONSULTOR');
INSERT INTO `proposal`(`GENERATION`,`PAYMENT`,`VALUE`,`BANK_ID`,`ID`,`CUSTOMER_CPF`,`EMPLOYEE_CPF`,`STATUS`) VALUES ('2024-08-20','2024-08-20',100,1,1,'49083176070','64809569071','CONTRATADA'),('2024-08-21','2024-08-21',1000,1,2,'02012038042','67661033020','CONTRATADA'),('2024-08-22','2024-08-25',500,2,3,'11485608074','33701848009','CONTRATADA'),('2024-08-23','2024-08-23',600,1,4,'05396454008','64809569071','CONTRATADA'),('2024-08-24','2024-08-24',101,3,5,'20366488007','64809569071','SOLICITADA'),('2024-08-25','2024-08-25',1001,1,6,'47627260019','67661033020','SOLICITADA'),('2024-08-26','2024-08-27',501,3,7,'47627260019','33701848009','SOLICITADA'),('2024-08-27','2024-08-27',601,1,8,'47627260019','64809569071','CANCELADA'),('2024-08-28','2024-08-28',102,2,9,'47627260019','67661033020','CANCELADA'),('2024-08-29','2024-09-01',1002,1,10,'05396454008','33701848009','CANCELADA'),('2024-08-30','2024-08-30',502,2,11,'20366488007','64809569071','CONTRATADA'),('2024-08-31','2024-08-31',602,2,12,'47627260019','67661033020','SOLICITADA'),('2024-08-25','2024-08-25',1001,1,13,'11485608074','67661033020','SOLICITADA');
INSERT INTO `user`(`ID`,`NAME`,`PASSWORD`) VALUES (1, '33701848009', 'PBKDF2WithHmacSHA256:2048:6G+jLvXAmTNrxIqcQNLLpcv6eck4cx1EVH0fgdbDtMo=:wEfFu/fTDPpp5fBAa+1NiuF3OwJ+QRshjixkJbxA8jY='),(2, '67661033020', 'PBKDF2WithHmacSHA256:2048:XSDwOumI1+Qwrb9Zw+9JC/+5+jZ0oJ9jQDPOGNBDDbg=:UqjRCzCqqttDd+KfXRo4HGqAa1N2azyLB6KN7rm5JMY='),(3, '64809569071', 'PBKDF2WithHmacSHA256:2048:2ih9VyhHGJGPRt94seRZhVvrPrKKyvRsKTyPYn2LwN0=:jW3SA8J6T1YPqgyTiHJJqFTYPt5htYyRb8LYM639p4M=')
INSERT INTO `user_group`(`ID`,`GROUPNAME`,`USERNAME`) VALUES (1, 'GESTOR', '33701848009'),(2, 'CONSULTOR', '67661033020'),(3, 'CONSULTOR', '64809569071')
