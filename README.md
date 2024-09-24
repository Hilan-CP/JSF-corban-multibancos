# Projeto JSF Corban Multibancos
A empresa Corban Multibancos atua como correspondente bancário na oferta de empréstimos de diversos bancos. O sistema deste projeto tem como objetivo permitir o armazenamento de prospostas realizadas para clientes e um acompanhamento de resultado diário.<br>
É necessário armazenar os dados da proposta e do cliente para formar uma carteira de clientes que futuramente serão retrabalhados na oferta de renovações de empréstimo. Sendo assim, quantos mais clientes existirem em carteira mais negócios serão fechados pela empresa.


<h2>
	<p>Diagrama de classe</p>
	<img src="./documents/diagrama de classe.png"/>
</h2>
As entidades do sistema são as equipes, os funcionários que podem ou não ter uma equipe, os cliente, os bancos atendidos pela empresa, e as propostas de empréstimo oferecidas aos clientes.


<h2>
	<p>Diagrama de caso de uso</p>
	<img src="./documents/diagrama de caso de uso.png"/>
</h2>
Inicialmente há dois tipos de usuários no sistema: consultor e gestor.<br>
O consultor é quem irá negociar com os clientes e inserir dados do cliente e da proposta.<br>
O gestor possui acesso a outras funcionalidades e pode também gerar negociações. O gestor pode gerenciar o cadastro dos bancos atendidos pela empresa, dos funcionários que utilizam o sistema e das equipes de funcionários, podendo também gerar relatórios e negociações.

### Requisitos de software
<a href="./documents/requisitos de software.docx">Documento de requisitos</a>

### Tecnologias utilizadas
- Java
- Jakarta Faces
- Jakarta Persistence
