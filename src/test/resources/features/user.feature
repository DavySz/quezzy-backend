# language: pt
Funcionalidade: Listar e Gerenciar Usuários  
  Como administrador  
  Quero listar, criar, atualizar e deletar usuários  
  Para gerenciar o acesso ao sistema  

  Cenário: Listar todos os usuários com sucesso  
    Dado que existem usuários cadastrados no sistema  
    Quando faço uma requisição GET para "/api/users"  
    Então o status da resposta deve ser 200  
    E o corpo da resposta deve conter uma lista de usuários  

  Cenário: Criar um novo usuário com sucesso  
    Dado que forneço um username, email e password válidos  
    Quando faço uma requisição POST para "/api/users" com o corpo JSON:  
      """  
      {  
        "username": "davy123",  
        "email": "davy@gmail.com",  
        "password": "senha123"  
      }  
      """  
    Então o status da resposta deve ser 201  
    E o corpo da resposta deve conter o usuário criado  

  Cenário: Criar um usuário com email inválido  
    Dado que forneço um username e password válidos  
    E um email inválido  
    Quando faço uma requisição POST para "/api/users" com o corpo JSON:  
      """  
      {  
        "username": "davy123",  
        "email": "davy@",  
        "password": "senha123"  
      }  
      """  
    Então o status da resposta deve ser 400  
    E o corpo da resposta deve conter uma mensagem de erro sobre o email inválido  

  Cenário: Buscar um usuário pelo ID que não existe  
    Dado que não existe um usuário com ID 999  
    Quando faço uma requisição GET para "/api/users/999"  
    Então o status da resposta deve ser 404  
    E o corpo da resposta deve estar vazio  

  Cenário: Atualizar um usuário com sucesso  
    Dado que existe um usuário com ID 1  
    Quando faço uma requisição PUT para "/api/users/1" com o corpo JSON:  
      """  
      {  
        "username": "davy_updated",  
        "email": "davy_updated@gmail.com",  
        "password": "senha123"  
      }  
      """  
    Então o status da resposta deve ser 200  
    E o corpo da resposta deve conter o usuário atualizado  

  Cenário: Deletar um usuário que existe  
    Dado que existe um usuário com ID 1  
    Quando faço uma requisição DELETE para "/api/users/1"  
    Então o status da resposta deve ser 204  
    E o corpo da resposta deve estar vazio  

  Cenário: Deletar um usuário que não existe  
    Dado que não existem usuários com ID 999  
    Quando faço uma requisição DELETE para "/api/users/999"  
    Então o status da resposta deve ser 404  
    E o corpo da resposta deve estar vazio  
