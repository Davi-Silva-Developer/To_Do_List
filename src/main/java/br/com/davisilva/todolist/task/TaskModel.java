package br.com.davisilva.todolist.task;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String descricao;

    @NotBlank(message = "O campo title é obrigatório")// anotação de validação usada para verificar existencia, caso deixe o campo em branco
    @Size(max = 50, message = "O campo title deve conter no máximo 50 caracteres") // anotação de validação usada para limitar o tamanho
    private String title;

    private LocalDateTime hrInicio;
    private LocalDateTime hrfim;
    private String prioridade;
    private UUID idUser;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    //CRIANDO UMA "ExceptionHANDLER" PERSONALIZADA
    //public void setTitle(String title)throws Exception{
    //
    //        if(title.length() > 50){
    //            throw  new Exception(" O campo title deve conter no maximo 50 caracteres");
    //        }
    //        this.title = title;
    //    }
}










