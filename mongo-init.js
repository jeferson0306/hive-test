db = db.getSiblingDB('admin');
db.createCollection('TAREFAS');
db.TAREFAS.insertMany([{
    nome: "Tarefa 1",
    descricao: "Criação do arquivo para popular o mongodb",
    status: "Concluída",
    dataDeCriacao: "2023-01-01T00:00:00Z",
    dataDeAtualizacao: "2023-01-01T01:00:00Z"
}, {
    nome: "Tarefa 2",
    descricao: "Criação do arquivo docker compose",
    status: "Não concluída",
    dataDeCriacao: "2023-01-02T00:00:00Z",
    dataDeAtualizacao: "2023-01-02T01:00:00Z"
}, {
    nome: "Tarefa 3",
    descricao: "Criação do arquivo dockerfile",
    status: "Concluída",
    dataDeCriacao: "2023-01-03T00:00:00Z",
    dataDeAtualizacao: "2023-01-03T01:00:00Z"
}, {
    nome: "Tarefa 4",
    descricao: "Construir a aplicação para criar, listar, atualizar, deletar e buscar por status",
    status: "Não concluída",
    dataDeCriacao: "2023-01-04T00:00:00Z",
    dataDeAtualizacao: "2023-01-04T01:00:00Z"
}, {
    nome: "Tarefa 5",
    descricao: "Utilizar o swagger para documentar a API",
    status: "Concluída",
    dataDeCriacao: "2023-01-05T00:00:00Z",
    dataDeAtualizacao: "2023-01-05T01:00:00Z"
}, {
    nome: "Tarefa 6",
    descricao: "Utilizar AWS lambda para processamento de eventos",
    status: "Não concluída",
    dataDeCriacao: "2023-01-06T00:00:00Z",
    dataDeAtualizacao: "2023-01-06T01:00:00Z"
}, {
    nome: "Tarefa 7",
    descricao: "Fiquei sem ideais de tarefas",
    status: "Concluída",
    dataDeCriacao: "2023-01-07T00:00:00Z",
    dataDeAtualizacao: "2023-01-07T01:00:00Z"
}, {
    nome: "Tarefa 8",
    descricao: "Mais uma nova tarefa sendo criada",
    status: "Não concluída",
    dataDeCriacao: "2023-01-08T00:00:00Z",
    dataDeAtualizacao: "2023-01-08T01:00:00Z"
}, {
    nome: "Tarefa 9",
    descricao: "Ei! Me delete aqui hehe",
    status: "Concluída",
    dataDeCriacao: "2023-01-09T00:00:00Z",
    dataDeAtualizacao: "2023-01-09T01:00:00Z"
}, {
    nome: "Tarefa 10",
    descricao: "Testar, testar e testar",
    status: "Não concluída",
    dataDeCriacao: "2023-01-10T00:00:00Z",
    dataDeAtualizacao: "2023-01-10T01:00:00Z"
}]);
