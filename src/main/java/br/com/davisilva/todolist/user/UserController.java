package br.com.davisilva.todolist.user;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Criando uma rota
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private IUserRepository userRepository; //< pedido para o Spring gerenciar o repositório.


    @PostMapping("/")
    public  ResponseEntity create(@RequestBody UserModel userModel){

       var user = this.userRepository.findByUsername(userModel.getUsername()); //<< Procurando um usuario
        //QUANDO O USUÁRIO JÁ EXISTE.
        if(user != null){
            System.out.println(" O usuário já existe no banco de dados. Não é possível cadastrar."); // << retorno no terminal
            //mensagem de erro + Status Code.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe"); // retorno para o usuário web
        }
        //FAZENDO A CRIPTOGRAFIA DA SENHA.
        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        //QUANDO É SUCESSO A CRIAÇÃO DO USUÁRIO.
        var userCriado = this.userRepository.save(userModel); //<< maneira de salvar os dados do usuário no banco de dados.
        return ResponseEntity.status(HttpStatus.CREATED).body(userCriado);

    }
}
