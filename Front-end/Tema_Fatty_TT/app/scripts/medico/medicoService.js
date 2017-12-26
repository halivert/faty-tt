//Medico Service
angular.module('trabajoTerminal')

.service("medicoService", function($log, $http, $q){

  return {

/**
* generaCodigoMedico  - Funcion que se comunica con la API para generar un token (codigo) de medico
*/
    generaCodigoMedico: function(idMedico){
      var data = $.param({idMedico: idMedico});
      var config = {
        headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
      }

      var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/medico/'+idMedico+'/token/';
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/token/';

      return $http.get(url,data,config).then(
        function successCallback(response) {
          if (response.data.respuesta === "ERROR"){
            return $q.reject(response);
          }
          else{
            return response.data;
          }
        },
        function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
    }
  }
})