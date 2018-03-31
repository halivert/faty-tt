//Login Service
angular.module('trabajoTerminal')

.service("dietaService", function($log, $http, $q,$filter){

 return {

/**
* recuperarRegistrosGlucosaService - Funcion que recupera los registros de glucosa de un paciente 
*/
  recuperarDieta: function(idDieta,idUsuario) {    

    //var data = $.param({idDieta: idDieta});
    var config = {
      headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
    }

    var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/dietas/'+idDieta;  
    //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/dietas/'+idDieta;
    //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/127/dietas/3';
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