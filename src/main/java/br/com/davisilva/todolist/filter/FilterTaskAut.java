package br.com.davisilva.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.davisilva.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAut extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain Chain)
            throws ServletException, IOException {
        var servletpath = request.getServletPath();

        if (servletpath.equals("/tasks/")){


            //pegar autenticação (username e passworld) codificado em Base64
            var autorizacao = request.getHeader("Authorization");
            System.out.println("Antes da decodificação da base64: " + autorizacao);

            //Vai pegar todo o conjunto e tirar apenas a parte "Basic" e os espaço
            var user_password = autorizacao.substring("Basic".length()).trim();
            System.out.println("Antes da decodificação da base64 sem o ´basic´: " + user_password);

            //Decodificando a username e password que vem em Base64
            byte[] decodificando = Base64.getDecoder().decode(user_password);
            System.out.println("Durante a decodificação da base64 para byte: " + decodificando);

            //Transforma os bytes em String.
            var decodificado = new String(decodificando);
            System.out.println("Depois da decodificação da base64: " + decodificado);

            String[] credencial = decodificado.split(":"); // << separando o ´username´ do ´password´  cada um em uma variavel.
            String username = credencial[0]; // ´username´ na posição 0.
            String password = credencial[1]; // ´password´ na posição 1.
            System.out.println("Autorização");
            System.out.println(username);
            System.out.println(password);


            //2 passo validar Usuario
            var user = this.userRepository.findByUsername(username); //<< verificando se o usuario estar no banco de dados
            if(user == null){
                response.sendError(401);
                System.out.println("Usuário não existe.");
            }else{
                //3 passo validar senha
                var passwordverify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()); // << validando a senha
                if (passwordverify.verified){  //verificando se a senha é verdadeira
                    Chain.doFilter(request, response);
                }else{
                    response.sendError(401);
                    System.out.println("Senha incorreta.");
                }

            }



        }else {
            response.sendError(401);
        }


    }
}
