


angular.module('trabajoTerminal')

.controller('MainCtrl', function($scope,loginService,$log,$cookies,toastr){

  	$scope.nombrePagina = "Página principal";
	$scope.userName = "Nombre del usuario";
	$scope.helloText = 'Pagina de inicio';
  	$scope.descriptionText = 'Descripcion del trabajo terminal';

  	
	$scope.informacionIndividuo = {};  	

    $scope.prueba = function(){
    	$scope.descriptionText = "Se cambia el texto";

    };
    var nombre = "";

    $scope.date = new Date();
	
	$scope.obtenerRol = function(){
		if($cookies.get("rol") == "1"){
			$log.debug("Es medico");
			return "M";
		}
		else{
			$log.debug("Es paciente");
			return "P";
		}
 	}
	
	$scope.fecha = new Date();
    
});
