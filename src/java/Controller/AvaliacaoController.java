/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Avaliacao;
import Beans.Turma;
import Dao.AvaliacaoDao;
import Dao.TurmaDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kato
 */
@WebServlet(name = "AvaliacaoController", urlPatterns = {"/AvaliacaoController"})
public class AvaliacaoController extends HttpServlet {
    private static String INSERT_OR_EDIT = "View/Professor/addEditAvaliacao.jsp";
    private static String LIST = "View/Professor/listarAvaliacoesTurma.jsp";
   
    private AvaliacaoDao   daoAvaliacao;
    private TurmaDao       daoTurma;
    private Avaliacao      avaliacao;
    private Turma          turma;
    
    public AvaliacaoController(){
        daoAvaliacao = new AvaliacaoDao();
        daoTurma     = new TurmaDao();
        avaliacao    = new Avaliacao();
        turma        = new Turma();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action.equalsIgnoreCase("delete")) {
            avaliacao.setId(Integer.parseInt(request.getParameter("idAvaliacao")));
            daoAvaliacao.delete(avaliacao);
            request.setAttribute("turma",daoTurma.getTurmaById(Integer.parseInt(request.getParameter("idTurma"))));
            request.setAttribute("avaliacoes",daoAvaliacao.getAvaliacoesByTurma(Integer.parseInt(request.getParameter("idTurma"))));
            forward = LIST;
        }
        else if (action.equalsIgnoreCase("edit")) {
            request.setAttribute("action","edit");
            request.setAttribute("turma",daoTurma.getTurmaById(Integer.parseInt(request.getParameter("idTurma"))));
            request.setAttribute("avaliacao",daoAvaliacao.getAvaliacaoById(Integer.parseInt(request.getParameter("idAvaliacao"))));
            forward = INSERT_OR_EDIT;
        }
        else if (action.equalsIgnoreCase("listarAvaliacoesPorTurma")) {
            session.setAttribute("idInstituicao",request.getParameter("idInstituicao"));
            session.setAttribute("idCurso", request.getParameter("idCurso"));
            session.setAttribute("idDisciplina", request.getParameter("idDisciplina"));
            request.setAttribute("turma",daoTurma.getTurmaById(Integer.parseInt(request.getParameter("idTurma"))));
            request.setAttribute("avaliacoes",daoAvaliacao.getAvaliacoesByTurma(Integer.parseInt(request.getParameter("idTurma"))));
            forward = LIST;
        }
        else {
            //Inserindo avaliação
            request.setAttribute("turma",daoTurma.getTurmaById(Integer.parseInt(request.getParameter("idTurma"))));
            request.setAttribute("action","inserir");
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        String nomeAvaliacao          = request.getParameter("nomeAvaliacao");
        String descricao              = request.getParameter("descricao");
        String requisitosAdicionais   = request.getParameter("requisitosAdicionais");
        String periodoSubmissoes      = request.getParameter("periodoSubmissoes");
        String periodoCorrecoes       = request.getParameter("periodoCorrecoes");
        String numCorrecoes           = request.getParameter("numCorrecoes");
        String notaMaxima             = request.getParameter("notaMaxima");
        String criterio               = request.getParameter("criterio");
        
        if(nomeAvaliacao!=null && descricao!=null && requisitosAdicionais!=null
                && periodoSubmissoes!=null && periodoCorrecoes!=null && numCorrecoes!=null
                && notaMaxima!=null && criterio!=null) {
      

            String[] periodoSubmissoesArray = periodoSubmissoes.split("-"); 
            String submissao_inicial = periodoSubmissoesArray[0].trim();
            String submissao_final   = periodoSubmissoesArray[1].trim();


            String[] periodoCorrecoesArray = periodoCorrecoes.split("-");
            String correcao_inicial = periodoCorrecoesArray[0].trim();
            String correcao_final   = periodoCorrecoesArray[1].trim();

            avaliacao.setNome(nomeAvaliacao);
            avaliacao.setDescricao(descricao);
            avaliacao.setRequisito_adicional(requisitosAdicionais);
            avaliacao.setSubmissao_inicial(Utils.DateUtil.datetimeToDB(submissao_inicial));
            avaliacao.setSubmissao_final(Utils.DateUtil.datetimeToDB(submissao_final));
            avaliacao.setCorrecao_inicial(Utils.DateUtil.datetimeToDB(correcao_inicial));
            avaliacao.setCorrecao_final(Utils.DateUtil.datetimeToDB(correcao_final));
            avaliacao.setNum_correcao_estudante(Integer.parseInt(numCorrecoes));
            avaliacao.setNota_maxima(Integer.parseInt(notaMaxima));
            avaliacao.setCriterio_correcao(criterio);
            
            
            String idTurma = request.getParameter("idTurma");

            if (action.equalsIgnoreCase("inserir")) {
                avaliacao.getTurma().setId(Integer.parseInt(idTurma));
                //Verificando o intervalo das datas.
                //O período de correções deve começar depois do período de submissões
                if(avaliacao.getCorrecao_inicial().before(avaliacao.getSubmissao_final())){
                    session.setAttribute("mensagemErro","O período de correções deve começar posteriormente ao período de submissões.");
                    response.sendRedirect("AvaliacaoController?action=inserir&idTurma="+idTurma);
                    return;
                }
                daoAvaliacao.insert(avaliacao);
                session.setAttribute("mensagemSucesso","Avaliação adicionada com sucesso.");
            }
            else {
                //Editar
                String idAvaliacao = request.getParameter("idAvaliacao");
                avaliacao.setId(Integer.parseInt(idAvaliacao));
                
                //Verificando o intervalo das datas.
                //O período de correções deve começar depois do período de submissões
                if(avaliacao.getCorrecao_inicial().before(avaliacao.getSubmissao_final())){
                    session.setAttribute("mensagemErro","O período de correções deve começar posteriormente ao período de submissões.");
                    response.sendRedirect("AvaliacaoController?action=edit&idTurma="+idTurma+"&idAvaliacao="+idAvaliacao);
                    return;
                }
                
                daoAvaliacao.update(avaliacao);
                session.setAttribute("mensagemSucesso","Avaliação atualizada com sucesso.");
            }
            request.setAttribute("turma",daoTurma.getTurmaById(Integer.parseInt(request.getParameter("idTurma"))));
            request.setAttribute("avaliacoes",daoAvaliacao.getAvaliacoesByTurma(Integer.parseInt(request.getParameter("idTurma"))));
            RequestDispatcher view = request.getRequestDispatcher(LIST);
            view.forward(request, response);
        }
        else{
            try (PrintWriter out = response.getWriter()) {
                out.print("Erro: Informações nulas!!");
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
