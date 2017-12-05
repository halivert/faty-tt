//Login Service
angular.module('trabajoTerminal')

.service("pacientesService", function($log, $http, $q){

  return {
    recuperarListaPacientes: function(idMedico) {
      
      var data = $.param({idMedico: idMedico});

      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }


		//var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
		
      var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
		
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
	
	recuperarListaHistorialClinico: function(idPaciente) {
      
      var data = $.param({idPaciente: idPaciente});
      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }

      //var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/';  
      var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/';

      return $http.get(url,data,config).then(
        function successCallback(response) {
          
			if (response.data.respuesta === "ERROR"){
				console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
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

	recuperarDetallHistorialClinico: function(idPaciente) {
      
      var data = $.param({idPaciente: idPaciente});
      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }

      var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/1';  
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'pacientes/;

      return $http.get(url,data,config).then(
        function successCallback(response) {
          
		  if(response.data.respuesta === "OK"){
			console.log("response OK : " + JSON.stringify(response.data));
			return response.data;
		  }
		  else if (response.data.respuesta === "ERROR"){
			console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
			return $q.reject(response);
		  }                   
        },
        function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
    }
	
  }

})