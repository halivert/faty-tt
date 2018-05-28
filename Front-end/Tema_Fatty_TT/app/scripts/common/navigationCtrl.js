/**
* navigationCtrl	-	Controlador que se usa en el menu lateral
*/
angular
.module('trabajoTerminal')

.controller('navigationCtrl', function($scope,$cookies,$log){

/**
* obtenerNombre	-	Funcion usada para obtener el nombre del usuario con base en la sesión iniciada
*/
	$scope.obtenerNombre = function(){
		return $cookies.get("nombre");
	}



});
