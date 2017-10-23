//Login Service
angular.module('trabajoTerminal')

.service("loginService", function($log, $http, $q){


   return {

   iniciaSesionService: function(email,password) {
   		$log.debug("username,password" + email,password);

   		    var data = {email:email,keyword:password};

   		    var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            }
            var url = 'http://localhost:8080/tt-escom-diabetes/sesion/login/';
            return $http.post(url,data,config)
						.then(function successCallback(response) {
						  
						    console.log("response OK : " + JSON.stringify(response.data));
						    return response.data;						    
						  }, function errorCallback(response) {
						 
						    console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
						    return $q.reject(response);
						  });
        },

    guardarIndividuo: function(nombre,apellidoPaterno,apellidoMaterno,email,keyword,fechaNacimiento,idSexo,rol) {
      

          var data = $.param({
                nombre: nombre,
                apellidoPaterno: apellidoPaterno,
                apellidoMaterno: apellidoMaterno,
                email: email,
                keyword: keyword,
                fechaNacimiento: fechaNacimiento,
                idSexo: idSexo,
                rol: rol
            });
          var config = {
                headers : {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                }
            }
            var url = 'http://localhost:8080/tt-escom-diabetes/sesion/individuos/';
            return $http.post(url,data,config)
            .then(function successCallback(response) {
              
                console.log("response OK : " + JSON.stringify(response.data.mensaje));
                return response.data.mensaje;               
              }, function errorCallback(response) {
             
                console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
                return $q.reject(response);
              });
        }    

  }

})