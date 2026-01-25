package br.com.davisilva.todolist.task;

import br.com.davisilva.todolist.utils.Utils;
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
        if (currentDate.isAfter(taskModel.getHrInicio()) || currentDate.isAfter(taskModel.getHrfim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio / data de termino deve ser maior do que a data atual.");
        }

        // Validação 2: Data Início vs Data Fim
        if (taskModel.getHrInicio().isAfter(taskModel.getHrfim())) {
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
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    // --- MÉTODO DE ATUALIZAR (PUT) ---
    // Mapeia requisições HTTP do tipo PUT para /tasks/{id}
    // Usado para ATUALIZAR uma tarefa existente
    @PutMapping("/{id}")

    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

        var task = this.taskRepository.findById(id).orElse(null);
        //Verificando se a tarefa existe
        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não foi encontrada");
        }

        var idUser = request.getAttribute("idUser");

        // Validando se o Usuario e dono da tarefa.
        if (!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario não tem permissão para alterar essa tarefa");
        }


        Utils.copyNOnNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.ok().body(this.taskRepository.save(taskUpdated));


    }
}