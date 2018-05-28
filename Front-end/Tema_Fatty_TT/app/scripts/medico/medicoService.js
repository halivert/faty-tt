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

      var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/token/';
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
    },

    enviarCodigoMedico: function(destinatario, token, nombreMedico, remitente, idMedico){
      var data = {
      destinatario: destinatario,
      token: token,
      nombreMedico: nombreMedico,
      remitente: remitente
      };
      var config = {
         headers : {'Content-Type': 'application/json'}
      }
      console.log("data : " + JSON.stringify(data));
      var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/correoToken';
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/correoToken';

      return $http.post(url,data,config).then(
        function successCallback(response) {
          if (response.data.respuesta === "ERROR"){
            console.log("response ERROR : " + JSON.stringify(response.data));
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
    },

    reasignaMedico: function(idPaciente, codigoMedico){
      var data = {
      idPaciente: idPaciente,
      codigoMedico: codigoMedico
      };

      var config = {
         headers : {'Content-Type': 'application/json'}
      }
      console.log("data : " + JSON.stringify(data));
      var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente;
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idPaciente;


      return $http.put(url,data,config).then(
        function successCallback(response) {
          if (response.data.respuesta === "ERROR"){
            console.log("response ERROR : " + JSON.stringify(response.data));
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