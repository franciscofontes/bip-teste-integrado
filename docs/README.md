
Olá, como vai?

O desafio foi interessante. Eu comecei analisando as tarefas e fiz um esboço da arquitetura que no meu entendimento ficou assim:
![arquitetura.png](arquitetura.png)

O EJB se comunica com o banco de dados, o microserviço em spring boot faz a intermediação entre o frontend e o EJB, 

No microserviço em spring boot poderiamos ter um acesso a um banco auxiliar noSQL para armazenar logs por exemplo, dependendo da necessidade.

Na implementação do codigo, no EJB utilizei Jakarta EE porém não usei as implementações modernas de Repository e sim o padrão DAO, com typed queries, usando diretiva de Transaction e o @Version para assegurar Optimistic Lock.
Foi uma opção de demonstração para o intuito deste teste

O bug foi corrigido e tomei a liberdade de incluir a regra de negocios como: o id de origem não pode ser o mesmo de destino além da verificação do saldo.

A comunicação entre o spring boot e o EJB utilizei o OpenFeign, poderia ter usado o Kafka mas assumi que esse serviço é de pouco acesso e o acesso via REST poderia ser o mais adequado nesse contexto

No front end usei o angular 20 LTS com a abordagem relativamente nova signals junto com um framework css chamado bulma que é bem leve e bonito.

Acredito que com mais tempo poderia fazer mais melhorias, espero que gostem.  

Abraço.
