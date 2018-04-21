//Login Service
angular.module('trabajoTerminal')

.service("glucosaService", function($log, $http, $q,$filter){

 return {
/**
* guardarRegistroGlucosa  - Funcion que envia a la API los datos necesarios para registrar los niveles de glucosa
*/
guardarRegistroGlucosa: function(idPaciente,azucar,fechaRegistro) {

  console.log("fechaRegistro : " + fechaRegistro);

    //fechaRegistro = $filter('date')(new Date(fechaRegistro), 'yyyy-MM-dd HH:mm:ss');
    //var date = new Date(fechaRegistro);

    console.log("date : " + fechaRegistro);

    //var fechaRegistro = $filter('date')(new Date(fechaRegistro), 'yyyy-MM-dd HH:mm:ss');

    var data = {
      azucar: azucar,
      fechaRegistro: fechaRegistro
    };

    var config = {
      headers : {'Content-Type': 'application/json'}
    }

    var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/registroglucosa';
    //var url = 'http://localhost:8080/tt-escom-diabetes/session/usuarios';

    console.log("data : " + JSON.stringify(data));
    return $http.post(url,data,config).then(function successCallback(response) {

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
* recuperarRegistrosGlucosaService - Funcion que recupera los registros de glucosa de un paciente 
*/
recuperarRegistrosGlucosaService: function(idUsuario) {    

  var data = $.param({idUsuario: idUsuario});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa';  

  return $http.get(url,data,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }

  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
},

/**
* recuperarNRegistrosGlucosaService - Funcion que recupera N registros de glucosa de un paciente 
*/
recuperarNRegistrosGlucosaService: function(idUsuario, limiteRegistro) {    

  var data = $.param({idUsuario: idUsuario, limiteRegistro: limiteRegistro});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/limiteRegistro/'+limiteRegistro;  
  
  return $http.get(url,data,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }
    
  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
},

recuperarRegistrosGlucosaPorFiltros: function(idUsuario, fechaInicio,fechaFin) {    

  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/fechaInicio/'+fechaInicio+'/fechaFin/'+fechaFin;  
   
  return $http.get(url,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }
    
  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
}


}
})