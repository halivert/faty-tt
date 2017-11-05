angular
    .module('trabajoTerminal')

.controller('navigationCtrl', function($scope,$cookies){
	
	//$log.debug("navigationCtrl");
	$scope.obtenerNombre = function(){
		return $cookies.get("nombre");
 	}

	

	if($cookies.get("rol") == "1")
		$scope.rol = "M";
	else
		$scope.rol = "P";

	

});
