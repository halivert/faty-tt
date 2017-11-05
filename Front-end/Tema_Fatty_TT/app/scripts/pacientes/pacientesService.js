//Login Service
angular.module('trabajoTerminal')

.service("pacientesService", function($log, $http, $q){

  return {
    recuperarListaPacientes: function(idMedico) {
      
      var data = $.param({idMedico: idMedico});

      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }

      var url = 'http://35.188.191.232/tt-escom-diabetes/medico/pacientes/?idMedico='+idMedico;  
      //var url = 'http://localhost:8080/tt-escom-diabetes/medico/pacientes/?idMedico='+idMedico;

      return $http.get(url,data,config).then(

        function successCallback(response) {
          console.log("response OK : " + JSON.stringify(response.data));
          return response.data;                   
        },
        function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
    }
  }

})