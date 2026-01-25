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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        var servletpath = request.getServletPath();

        //  Verifica se estamos na rota de tarefas
        if (servletpath.startsWith("/tasks/")) {

            // --- BLOCO DE SEGURANÇA CORS (MANUAL) ---
            // Adiciona estes cabeçalhos em TODAS as respostas (Sucesso ou Erro)
            // Isso garante que o navegador consegue ler o erro 401 se a senha estiver errada
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            response.setHeader("Access-Control-Max-Age", "3600");

            // Se for o espião (OPTIONS), devolve OK e para aqui.
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            // ----------------------------------------

            //  Pega a autenticação
            var autorizacao = request.getHeader("Authorization");

            // Se não tiver senha (ou header vazio)
            if (autorizacao == null) {
                response.sendError(401, "Usuário sem autenticação");
                return;
            }

            //  Decodifica a senha
            // Try/Catch  caso venha lixo no header, manter simples
            var user_password = autorizacao.substring("Basic".length()).trim();
            byte[] decodificando = Base64.getDecoder().decode(user_password);
            var decodificado = new String(decodificando);

            String[] credencial = decodificado.split(":");
            String username = credencial[0];
            String password = credencial[1];

            // Valida no Banco
            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                // Usuário não existe
                response.sendError(401, "Usuário não encontrado");
            } else {
                //  Valida Senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    // SUCESSO: Passa o ID para o controller
                    request.setAttribute("idUser", user.getId());
                    chain.doFilter(request, response);
                } else {
                    // SENHA ERRADA
                    response.sendError(401, "Senha incorreta");
                }
            }

        } else {
            // Se não for rota /tasks/, deixa passar para o próximo filtro
            chain.doFilter(request, response);
        }
    }
}