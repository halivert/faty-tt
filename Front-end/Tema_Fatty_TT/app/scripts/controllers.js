/**
 * INSPINIA - Responsive Admin Theme
 *
 */

/**
 * MainCtrl - controller
 */



angular.module('trabajoTerminal')

.controller('MainCtrl', function($scope){

  $scope.nombrePagina = "Página principal";
	$scope.userName = "Nombre del usuario";
	$scope.helloText = 'Pagina de inicio';
  $scope.descriptionText = 'Descripcion del trabajo terminal';

    $scope.prueba = function(){
    	$scope.descriptionText = "Se cambia el texto";

    };
});
