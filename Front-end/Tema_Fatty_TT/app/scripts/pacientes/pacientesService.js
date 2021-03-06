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

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
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
* recuperarNumeroPacientes - Recuperar el numero de pacientes asociados a un medico 
*/
recuperarNumeroPacientes: function(idMedico) {
      
      var data = $.param({idMedico: idMedico});

      var config = {
            headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
          }


		var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/';
		
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

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/';  
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
guardarInfoHistorialClinico: function(idPaciente,peso,talla,estatura,imc,lipidos,carbohidratos,proteinas,azucar,actividadFisica) {  
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
    azucar: azucar,
    actividadFisica:actividadFisica
  };

  

  var config = {
    headers : {'Content-Type': 'application/json'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/historialclinico';  

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

editarInfoHistorialClinico: function(idPaciente,idHistorialClinico,peso,talla,estatura,imc,lipidos,carbohidratos,proteinas,azucar,actividadFisica,observaciones) {  
  var fechaRegistro = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
  
  var data = {
    idHistorialClinico : idHistorialClinico,
    idPaciente: idPaciente,
    fecha: fechaRegistro,
    peso: peso,
    talla: talla,
    estatura: estatura,
    imc: imc,
    lipidos: lipidos,
    carbohidratos: carbohidratos,
    proteinas:proteinas,
    azucar: azucar,
    actividadFisica:actividadFisica,
    observaciones:observaciones
  };

  

  var config = {
    headers : {'Content-Type': 'application/json'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/'+idHistorialClinico;  

  //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/'+21;

  console.log("data : " + JSON.stringify(data));

  return $http.put(url,data,config).then(
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
recuperarDetallHistorialClinico: function(idPaciente,idHistorialClinico) {

  var data = $.param({idPaciente: idPaciente, idHistorialClinico:idHistorialClinico});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/historialclinico/'+idHistorialClinico;  

      return $http.get(url,data,config).then(
        function successCallback(response) {
            return response.data;                           
        },
        function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
    },

/**
* recuperaUltimoHistorial - Recupera el ultimo historial de un paciente
*/
 recuperaUltimoHistorial: function(idPaciente) {
  //var data = $.param({idPaciente: idPaciente, idHistorialClinico:idHistorialClinico});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/ultimoHistorialclinico' 
      //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'pacientes/;
      return $http.get(url,config).then(
        function successCallback(response) {
            return response.data;                           
        },
        function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
    }  
}

})