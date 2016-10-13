<%-- 
    Document   : turma
    Created on : 27/09/2016, 20:10:36
    Author     : Kato
--%>
<%@include file="../../include/headerProfessor.jsp" %>
<%@include file="../../include/sidebarLeftProfessor.jsp" %>
<div class="col-md-9 col-lg-10 main">
    <br>
    <h2>Turmas</h2>
    <hr>
    <form class="form-horizontal">
        <fieldset>
        <!-- Form Name -->
        <a href="${pageContext.request.contextPath}/TurmaController?action=inserir" class="btn btn-success"> + Adicionar </a><br>
        <hr>
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-3 control-label" for="labelInstituicao"><b>Nome da institui��o:</b></label>  
            <div class="col-md-6">
                <select class="form-control" name="selectInstituicao" id="selectInstituicao" required>
                    <option value="0"></option>
                    <c:forEach items="${instituicoes}" var="instituicao"> 
                        <option value="${instituicao.id}">${instituicao.nome}</option>
                    </c:forEach>
                </select>
            </div>
        </div><br><br>
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-3 control-label" for="labelCurso"><b>Nome do curso:</b></label>  
            <div class="col-md-6">
                <select class="form-control" name="selectCurso" id="selectCurso" required>
                    <option></option>
                </select>
            </div>
        </div><br><br>
        
        
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-3 control-label" for="labelDisciplina"><b>Nome da disciplina:</b></label>  
            <div class="col-md-6">
                <select class="form-control" name="selectDisciplina" id="selectDisciplina" required>
                    <option></option>
                </select>
            </div>
        </div><br><br>
        <!-- Button -->
        <div class="form-group">
            <label class="col-md-3 control-label" for=""></label>
            <div class="col-md-6">
                <button id="" name="" class="btn btn-primary">Listar</button>
            </div>
        </div>
        </fieldset>
    </form>
    <hr>
    <br>
    <div class="table-responsive">
        <table class="table table-striped">
            <thead class="thead-inverse">
                <tr>
                    <th>#ID</th>
                    <th>Nome</th>
                    <th>A��es</th>
                </tr>
            </thead>
            <tr>
                <td>1</td>
                <td><a href="${pageContext.request.contextPath}/View/Professor/turmaAvaliacoes.jsp"> Turma Noturna</a></td>
                <td> 
                    <button id="" name="" class="btn btn-danger">Deletar</button>
                    &nbsp
                    <a href="${pageContext.request.contextPath}/View/Professor/editTurma.jsp" id="" name="" class="btn btn-info">Editar</a>
                    &nbsp
                    <a href="${pageContext.request.contextPath}/View/Professor/listarAlunosTurma.jsp" class="btn btn-success">Alunos</a>
                </td>
            <tr>
        </table>
    </div>
</div>
<%@include file="../../include/footerProfessor.jsp" %>
<script>
$(document).ready(function() {

    $('#selectInstituicao').change(function(event) {
        if($('#selectInstituicao').val()==='0'){
            $('#selectCurso option').remove();
            $('#selectDisciplina option').remove();
        }
        else{
            $.ajax({
               type: 'GET',
               url: 'AjaxController',
               data: 'instituicaoId='+$('#selectInstituicao').val(),
               statusCode: {
                   404: function() {
                       console.log('Pagina n�o encontrada');
                   },
                   500: function(){
                       console.log('Erro no servidor');
                   }
               },
               success: function(dados){
                   pegadados = dados.split(":");
                   limparselect();
                   if(dados === '')
                       $('#selectCurso').append("<option value='0'>Institui��o sem nenhum curso.</option>");
                   else{
                       $('#selectCurso').append('<option></option>');
                       for(var i = 0; i < pegadados.length - 1; i++){
                           var codigoCurso = pegadados[i].split("-")[0]; 
                           var nomeCurso = pegadados[i].split("-")[1];
                           $('#selectCurso').append('<option value ="'+codigoCurso+'">'+nomeCurso+'</option>');

                       }
                   }
               }
           }); 
           function limparselect(){
               $('#selectCurso option').remove();
           }
        }
    });
    $('#selectCurso').change(function(event) {
         $.ajax({
            type: 'GET',
            url: 'AjaxController',
            data: 'cursoId='+$('#selectCurso').val(),
            statusCode: {
                404: function() {
                    console.log('Pagina n�o encontrada');
                },
                500: function(){
                    console.log('Erro no servidor');
                }
            },
            success: function(dados){
                pegadados = dados.split(":");
                limparselect();
                if(dados === '')
                    $('#selectDisciplina').append("<option value='0'>Curso sem nenhuma disciplina.</option>");
                else{
                    $('#selectDisciplina').append("<option></option>");
                    for(var i = 0; i < pegadados.length - 1; i++){
                        var codigoDisciplina = pegadados[i].split("-")[0]; 
                        var nomeDisciplina = pegadados[i].split("-")[1];
                        $('#selectDisciplina').append('<option value ="'+codigoDisciplina+'">'+nomeDisciplina+'</option>');

                    }
                }
            }
        }); 
        function limparselect(){
            $('#selectDisciplina option').remove();
        }
    });
});
</script>