//Login Service
angular.module('trabajoTerminal')

.service("loginService", function($log, $http, $q){


   return {

   iniciaSesionService: function(username,password) {
   		$log.debug("username,password" + username,password);

   		    var data = $.param({
                idPaciente: password
            });

   		    var config = {
                headers : {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                }
            }
            var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/?'+data;

            return $http.get(url,data,config)
						.then(function successCallback(response) {
						  
						    console.log("response OK : " + JSON.stringify(response.data));
						    return response.data;						    
						  }, function errorCallback(response) {
						 
						    console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
						    return $q.reject(response);
						  });
        }
  }

})