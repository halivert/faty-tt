
angular.module('trabajoTerminal')

.controller('loginCtrl', function($scope,$location,$log,$state){

	$scope.titulo = "Trabajo terminal";

    $scope.iniciarSesion = function(){
      $log.debug("iniciarSesion");
       $state.transitionTo('index.main');
    };
});
