//Alimento service
angular.module('trabajoTerminal')

.service("alimentoService", function($http, $q){

 return {
/**
* recuperarAlimentos - Servicio que invoca a la API para recuperar la lista de alimentos 
*/
  recuperarAlimentos: function(tipoAlimento) {    

    console.log("tipoAlimento : " + tipoAlimento);

    var config = {
      headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
    }

    //var alimento = "Fruta";

    var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/alimentos/'+tipoAlimento;  
    
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