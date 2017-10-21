
angular.module('trabajoTerminal')

.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr){

	$scope.titulo = "Trabajo terminal";

	$scope.usuarioObject={
        email:'',
        password:''
  	};

    $scope.iniciarSesion = function(email,password){
      $log.debug("iniciarSesion");




      loginService.iniciaSesionService(email,password).then(
      	function successCallback() {
	       
	        	$state.transitionTo('index.main');
	       
        },
        function errorCallback(d) {
        	$log.debug(JSON.stringify(d.data.mensaje));
			toastr.error(d.data.mensaje, 'Error');
			

        }); 

    };


});
