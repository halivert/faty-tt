//Pacientes Service
angular.module('trabajoTerminal')

.service("pacientesService", function($log, $http, $q, $filter){

  return {

/**
* recuperarListaPacientes - Funcion que se comunica con la API para recuperar la lista de pacientes asociados a un medico
*/
recuperarListaPacientes: function(idMedico) {
  var data = $.param({idMedico: idMedico});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';

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

recuperarNumeroPacientes: function(idMedico) {
      
      var data = $.param({idMedico: idMedico});

      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }


		var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
		
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
		
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
	
/**
* recuperarListaHistorialClinico  - Funcion que se comunica con la API para recuperar la lista de historial clinico de un paciente con base en su identficador
*/
recuperarListaHistorialClinico: function(idPaciente) {
  var data = $.param({idPaciente: idPaciente});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/';  
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/';
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

/**
* guardarInfoHistorialClinico  - Funcion que se comunica con la API para guardar informarcion del historial clinico de un paciente
*/
guardarInfoHistorialClinico: function(idPaciente,peso,talla,estatura,imc,lipidos,carbohidratos,proteinas,azucar) {

  
  var fechaRegistro = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');


  var data = {
    idPaciente: idPaciente,
    fecha: fechaRegistro,
    peso: peso,
    talla: talla,
    estatura: estatura,
    imc: imc,
    lipidos: lipidos,
    carbohidratos: carbohidratos,
    proteinas:proteinas,
    azucar: azucar
  };

  

  var config = {
    headers : {'Content-Type': 'application/json'}
  }

  var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/pacientes/historialclinico';  

  console.log("data : " + JSON.stringify(data));

  return $http.post(url,data,config).then(
    function successCallback(response) {
      if(response.data.respuesta === "OK"){
        console.log("response OK : " + JSON.stringify(response.data.mensaje));
        return response.data.mensaje;     
      }
      else{
        console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
        return $q.reject(response);
      }

    },function errorCallback(response) {
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);
    });
},    

/**
* recuperarDetallHistorialClinico - Recuperar la informacion detallada de un historial clinico 
*/
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