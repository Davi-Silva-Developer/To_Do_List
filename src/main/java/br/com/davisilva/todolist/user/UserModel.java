package br.com.davisilva.todolist.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

//Criando modelo de usuario.
@Data
@Entity
@Table(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")     // << faz a implementação do ID automaticamente.
    private UUID id;                        //<< UUID é um idetificador unico.

    @Column(unique = true)                  // < essa annotation diz que essa coluna tem uma restrição(constraint) é preciso ser um valor unico
    private  String username;               // < isso faz a validação de usuário, não é possivel mais de uma pessoa com o mesmo username.

    private String name;
    private String password;

    @CreationTimestamp                        // <--- O Hibernate preenche a data sozinho
    private LocalDateTime createdAT;
}
