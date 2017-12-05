angular
    .module('trabajoTerminal')

.controller('navigationCtrl', function($scope,$cookies,$log){
	
	//$log.debug("navigationCtrl");
	$scope.obtenerNombre = function(){
		return $cookies.get("nombre");
 	}

	

	/*if($cookies.get("rol") == "1"){
		$scope.rol = "M";
		$log.debug("Es medico");
	}
	else{
		$scope.rol = "P";
			$log.debug("Es paciente");
	}*/

	

});
