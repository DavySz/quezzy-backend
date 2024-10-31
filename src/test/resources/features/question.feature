# language: pt
Funcionalidade: Gerenciamento de Questões
  Como um administrador
  Eu quero gerenciar questões
  Para que eu possa manter o sistema atualizado

  Cenário: Listar todas as questões
    Dado que eu criei uma questão válida
    Quando eu faço uma requisição GET para "/api/questions"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista de questões

  Cenário: Retornar vazio quando não há questões
    Quando eu faço uma requisição GET para "/api/questions"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista vazia

  Cenário: Criar uma questão válida
    Dado que eu tenho uma questão válida
    Quando eu faço uma requisição POST para "/api/questions"
    Então a resposta deve ter status 201
    E a resposta deve conter os dados da questão criada

  Cenário: Buscar questão por ID
    Dado que eu criei uma questão válida
    Quando eu faço uma requisição GET para "/api/questions/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados da questão

  Cenário: Buscar questão por ID não encontrado
    Quando eu faço uma requisição GET para "/api/questions/9999"
    Então a resposta deve ter status 404

  Cenário: Buscar questões por categoria
    Dado que eu criei uma questão válida
    Quando eu faço uma requisição GET para "/api/questions/category/1"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista de questões

  Cenário: Atualizar uma questão
    Dado que eu criei uma questão válida
    Quando eu faço uma requisição PUT para "/api/questions/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados atualizados da questão

  Cenário: Atualizar questão não encontrada
    Quando eu faço uma requisição PUT para "/api/questions/9999"
    Então a resposta deve ter status 404

  Cenário: Deletar uma questão
    Dado que eu criei uma questão válida
    Quando eu faço uma requisição DELETE para "/api/questions/{id}"
    Então a resposta deve ter status 204

  Cenário: Deletar questão não encontrada
    Quando eu faço uma requisição DELETE para "/api/questions/9999"
    Então a resposta deve ter status 404