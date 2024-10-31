# language: pt
Funcionalidade: Gerenciamento de Usuários
  Como um administrador
  Eu quero gerenciar usuários
  Para que eu possa manter o sistema atualizado

  Cenário: Listar todos os usuários
    Dado que eu criei um usuário válido
    Quando eu faço uma requisição GET para "/api/users"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista de usuários

  Cenário: Retornar vazio quando não há usuários
    Quando eu faço uma requisição GET para "/api/users"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista vazia

  Cenário: Criar usuário com campos faltando
    Dado que eu tenho um usuário com campos faltando
    Quando eu faço uma requisição POST para "/api/users"
    Então a resposta deve ter status 400
    E a resposta deve conter "Todos os campos são obrigatórios"

  Cenário: Criar usuário com email inválido
    Dado que eu tenho um usuário com email inválido
    Quando eu faço uma requisição POST para "/api/users"
    Então a resposta deve ter status 400
    E a resposta deve conter "Email inválido"

  Cenário: Criar usuário válido
    Dado que eu tenho um usuário válido
    Quando eu faço uma requisição POST para "/api/users"
    Então a resposta deve ter status 201

  Cenário: Buscar usuário por ID
    Dado que eu criei um usuário válido
    Quando eu faço uma requisição GET para "/api/users/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados do usuário

  Cenário: Buscar usuário por ID não encontrado
    Quando eu faço uma requisição GET para "/api/users/9999"
    Então a resposta deve ter status 404

  Cenário: Atualizar usuário
    Dado que eu criei um usuário válido
    Quando eu faço uma requisição PUT para "/api/users/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados atualizados do usuário

  Cenário: Atualizar usuário não encontrado
    Quando eu faço uma requisição PUT para "/api/users/9999"
    Então a resposta deve ter status 404

  Cenário: Atualizar usuário com campos faltando
    Dado que eu criei um usuário válido
    E eu tenho um usuário com campos faltando
    Quando eu faço uma requisição PUT para "/api/users/{id}"
    Então a resposta deve ter status 400
    E a resposta deve conter "Todos os campos são obrigatórios"

  Cenário: Atualizar usuário com email inválido
    Dado que eu criei um usuário válido
    E eu tenho um usuário com email inválido
    Quando eu faço uma requisição PUT para "/api/users/{id}"
    Então a resposta deve ter status 400
    E a resposta deve conter "Email inválido"

  Cenário: Deletar usuário
    Dado que eu criei um usuário válido
    Quando eu faço uma requisição DELETE para "/api/users/{id}"
    Então a resposta deve ter status 204

  Cenário: Deletar usuário não encontrado
    Quando eu faço uma requisição DELETE para "/api/users/9999"
    Então a resposta deve ter status 404