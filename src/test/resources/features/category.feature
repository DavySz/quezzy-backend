# language: pt
Funcionalidade: Gerenciamento de Categorias
  Como um administrador
  Eu quero gerenciar categorias
  Para que eu possa manter o sistema organizado

  Cenário: Listar todas as categorias
    Dado que eu criei uma categoria válida
    Quando eu faço uma requisição GET para "/api/categories"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista de categorias

  Cenário: Retornar vazio quando não há categorias
    Quando eu faço uma requisição GET para "/api/categories"
    Então a resposta deve ter status 200
    E a resposta deve conter uma lista vazia

  Cenário: Criar uma categoria válida
    Dado que eu tenho uma categoria válida
    Quando eu faço uma requisição POST para "/api/categories"
    Então a resposta deve ter status 201
    E a resposta deve conter os dados da categoria criada

  Cenário: Buscar categoria por ID
    Dado que eu criei uma categoria válida
    Quando eu faço uma requisição GET para "/api/categories/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados da categoria

  Cenário: Buscar categoria por ID não encontrado
    Quando eu faço uma requisição GET para "/api/categories/9999"
    Então a resposta deve ter status 404

  Cenário: Atualizar uma categoria
    Dado que eu criei uma categoria válida
    Quando eu faço uma requisição PUT para "/api/categories/{id}"
    Então a resposta deve ter status 200
    E a resposta deve conter os dados atualizados da categoria

  Cenário: Atualizar categoria não encontrada
    Quando eu faço uma requisição PUT para "/api/categories/9999"
    Então a resposta deve ter status 404

  Cenário: Deletar uma categoria
    Dado que eu criei uma categoria válida
    Quando eu faço uma requisição DELETE para "/api/categories/{id}"
    Então a resposta deve ter status 204

  Cenário: Deletar categoria não encontrada
    Quando eu faço uma requisição DELETE para "/api/categories/9999"
    Então a resposta deve ter status 404