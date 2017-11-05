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
            var url = 'http://35.188.191.232/tt-escom-diabetes/sesion/login/';
            //var url = 'http://localhost:8080/tt-escom-diabetes/sesion/login/';
            return $http.post(url,data,config)
						.then(function successCallback(response) {
						  
						    console.log("response OK : " + JSON.stringify(response.data));
						    return response.data;						    
						  }, function errorCallback(response) {
						 
						    console.log("response ERROR : " + JSON.stringify(response.data));
						    return $q.reject(response);
						  });
        },

    guardarIndividuo: function(nombre,apellidoPaterno,apellidoMaterno,email,keyword,fechaNacimiento,idSexo,rol,cedula) {
          var data = $.param({
                        nombre: nombre,
                        apellidoPaterno: apellidoPaterno,
                        apellidoMaterno: apellidoMaterno,
                        email: email,
                        keyword: keyword,
                        fechaNacimiento: fechaNacimiento,
                        idSexo: idSexo,
                        rol: rol,
                        cedula: cedula
                    });

          var config = {
                headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
            }
          var url = 'http://35.188.191.232/tt-escom-diabetes/sesion/individuos/';
          //var url = 'http://localhost:8080/tt-escom-diabetes/sesion/individuos/';
          return $http.post(url,data,config).then(function successCallback(response) {
                console.log("response OK : " + JSON.stringify(response.data.mensaje));
                return response.data.mensaje;               
              },function errorCallback(response) {
             
                console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
                return $q.reject(response);
              });
        },

        recuperarInformacionInd: function(idIndividuo,idRol) {
         
          $log.debug("idIndividuo : " + idIndividuo);
          $log.debug("idRol : " + idRol);
          
          var data = $.param({
                idIndividuo: idIndividuo,
                idRol: idRol
            });


          var config = {
                headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
              }

          var url = 'http://35.188.191.232/tt-escom-diabetes/sesion/individuos/?idIndividuo='+idIndividuo+'&idRol='+idRol;  
          //var url = 'http://localhost:8080/tt-escom-diabetes/sesion/individuos/?idIndividuo='+idIndividuo+'&idRol='+idRol;

          return $http.get(url,data,config).then(function successCallback(response) {
                console.log("response OK : " + JSON.stringify(response.data));
                return response.data;               
              },function errorCallback(response) {
             
                console.log("response ERROR : " + JSON.stringify(response.data));
                return $q.reject(response);
              });
        }


  }

})