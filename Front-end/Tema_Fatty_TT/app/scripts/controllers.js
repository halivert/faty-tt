

/**
* MainCtrl - Controlador principal, usado para definir propiedades, las cuales pueden ser usadas por cualquiera de las vistas
* 			 alguna de las propiedades son la fecha o el rol del usuario
*/
angular.module('trabajoTerminal')

.controller('MainCtrl', function($scope,loginService,$cookies,toastr){

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
	$scope.fecha = new Date();


/**
* obtenerRol()	-	Función para recuperar el rol de usuario.
*/
	$scope.obtenerRol = function(){
		console.log("obtenerRol()");
		return ($cookies.get("rol") == "1" ? "M" : "P")
	}

});
