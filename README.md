# Sala de bate papo

## Equipe

- **Adrian Grosch**
- **Matheus Parro de Sousa**

## Cenário Base

O sistema consiste numa sala de bate papo peer to peer através de sockets em conjunto com threads. Para entrar na sala,
cada cliente deve informar seu nome de usuário, host e porta de onde está hospedado o servidor, para que o mesmo aceite
a conexão do socket, disparando um aviso "Cliente entrou" para todos os já conectados. Cada cliente logado pode enviar
mensagens a todos os participantes. Tal comunicação acontece através de JSONs com a chave "name" e "message", sendo
respectivamente o nome do cliente que está enviando a mensagem e message o conteúdo digitado pelo menos

![Diagrama em branco](DiagramaClasse.png)

A manipulação dos dados é realizada a partir de mensagens recebidas do cliente, via Socket. A mensagem é uma String que
é separada pelo caracter especial ";". Essa String contém, em posições específicas, a operação de manipulação e os dados
requeridos, conforme esquema abaixo.

A aplicação cliente consiste em uma tela principal, que é a do bate papo, onde para cada novo cliente é instanciada uma
Thread chamada MessageListener, que monitora através de um BufferedReader as mensagens recebidas pelos outros clientes.
Tal thread é necessária pois a leitura do Buffered reader interrompe a execução do código até ser recebida uma mensagem,
logo, fica inviável enviar uma nova mensagem outra mensagem seja lida pelo mesmo cliente.; A aplicação servidor mantém
os clientes conectados no socket, ou seja, para cada cliente cuja cojnexão do socket foi aceita, será criada também uma
Thread para despachar uma mensagem de um cliente para outros clientes através de um PrintWriter instanciado a partir do
OutputStream de cada cliente. Esta Thread criada é adicionada num array estático na classe do servidor, para assim saber
quais clientes devem receber uma mensagem enviada por outro cliente.

**REQUISITOS FUNCIONAIS**

RF1: O Sistema deve permitir conexão com o server socket

RF3: O sistema deve permitir a troca de mensagens entre varios usuários.

RF3: O sistema deve manters mensagens digitas pelos usuários enquanto o aplicativo estiver aberto.

**JSON DA TROCA DE MENSAGENS ENTRE CLIENTE E SERVIDOR**

| name  |  message  | descrição
| ------------------- | ------------------- | ------------------- |
|  nome do cliente |  getClients | faz a requisição de todas as portas dos clientes logados|
|  nome do cliente |  porta do cliente logado | envia a porta do cliente que logou para salvar no array de portas |
**DIAGRAMA DE SEQUENCIA DAS MENSAGENS DE LOGIN**

![Diagrama em branco](loginDiagram.png)

**DIAGRAMA DE SEQUENCIA DAS MENSAGENS DE REQUISIÇÃO DE PORTAS**

![Diagrama em branco](getPortsDiagram.png)

**DIAGRAMA DE SEQUENCIA DAS MENSAGENS ENTRE CLIENTES**

![Diagrama em branco](SendMessageDiagram.png)


**JSON DA TROCA DE MENSAGENS ENTRE CLIENTE E CLIENTE**

| name  |  message  | descrição
| ------------------- | ------------------- | ------------------- |
|  nome do cliente |  mensagem | mensagem digitada pelo cliente a ser enviada a outros clientes|


