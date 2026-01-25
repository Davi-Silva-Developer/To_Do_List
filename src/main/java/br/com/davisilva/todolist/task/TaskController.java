package br.com.davisilva.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*") // Permite a conexão do Front
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    // --- MÉTODO DE CRIAR TAREFAS (POST) ---
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        System.out.println("chegou no controller");

        // extraindo o id do cliente atribuindo na var 'idUser'
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        // validando data e hora
        var currentDate = LocalDateTime.now();

        // Validação 1: Data atual vs Data Início/Fim
        if(currentDate.isAfter(taskModel.getHrInicio()) || currentDate.isAfter(taskModel.getHrfim())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio / data de termino deve ser maior do que a data atual.");
        }

        // Validação 2: Data Início vs Data Fim
        if(taskModel.getHrInicio().isAfter(taskModel.getHrfim())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor do que a data de termino");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    // ^^^ FECHEI O MÉTODO "CREATE" AQUI (Muito Importante!!!)


    // --- MÉTODO DE LISTAR TAREFAS (GET) ---
    // Este método serve como  "Teste de Conexão". Se o Login funcionar, ele retorna 200 "OK".
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }

    // --- MÉTODO DE ATUALIZAR (PUT) ---
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel tasksModel, HttpServletRequest request, @PathVariable UUID id){
        // O  retorno de void para TaskModel para ser mais padrão
        // Mas podes ajustar a lógica de update depois.
        var idUser = request.getAttribute("idUser");
        tasksModel.setIdUser((UUID) idUser);
        tasksModel.setId(id);
        return this.taskRepository.save(tasksModel); // Exemplo simples de retorno
    }
}