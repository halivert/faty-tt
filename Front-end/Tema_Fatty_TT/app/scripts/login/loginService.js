//Login Service
angular.module('trabajoTerminal')

.service("loginService", function($log, $http, $q){


   return {

   iniciaSesionService: function(email,password) {
   		
   		    var data = {email:email,keyword:password};

   		    var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            }
            var url = 'http://35.188.191.232/tt-escom-diabetes/session/login';
            //var url = 'http://localhost:8080/tt-escom-diabetes/sesion/login/';
            return $http.post(url,data,config)
						.then(function successCallback(response) {
						  
                if(response.data.respuesta === "OK"){
                  console.log("response OK : " + JSON.stringify(response.data));
                  return response.data;  
                }
                else{
                   console.log("response ERROR : " + JSON.stringify(response.data));
                   return $q.reject(response);
                }
						    						    
						  },function errorCallback(response) {
						    console.log("response ERROR : " + JSON.stringify(response.data));
						    return $q.reject(response);
						  });
        },

    guardarUsuario: function(nombre,apellidoPaterno,apellidoMaterno,email,keyword,fechaNacimiento,sexo,idRol,cedulaProfesional,codigoMedico) {
          var data = {
                        nombre: nombre,
                        apellidoPaterno: apellidoPaterno,
                        apellidoMaterno: apellidoMaterno,
                        email: email,
                        keyword: keyword,
                        fechaNacimiento: "27/02/1994",
                        sexo: sexo,
                        idRol: idRol,
                        cedulaProfesional: cedulaProfesional,
                        codigoMedico: codigoMedico
                    };

          var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
            }
          var url = 'http://35.188.191.232/tt-escom-diabetes/session/usuarios';
          $log.debug("data : " + JSON.stringify(data)) ;
          //var url = 'http://localhost:8080/tt-escom-diabetes/session/usuarios';
         
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

        recuperarInformacionUsuario: function(idUsuario) {
         
          $log.debug("idUsuario : " + idUsuario);
          
          var data = $.param({idUsuario: idUsuario});


          var config = {
                headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
              }

          var url = 'http://35.188.191.232/tt-escom-diabetes/ceres/usuarios/'+idUsuario;  
          //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/usuarios/'+idUsuario;

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