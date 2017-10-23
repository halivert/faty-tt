
angular.module('trabajoTerminal')

.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr,$cookies){

	$scope.titulo = "Bienvenido a CERES";

	$scope.usuarioObject={
        email:'',
        password:''
  };
  $scope.datosP={

  };
  $cookies.put("auth","false");
  
    $scope.iniciarSesion = function(){
      $log.debug("iniciarSesion");
      loginService.iniciaSesionService($scope.usuarioObject.email,$scope.usuarioObject.password).then(
      	function successCallback(d) {
          $cookies.put("auth","true");
          $cookies.put("rol",d.individuoRol);
	         toastr.success(d.mensaje,'Ok');
	        	$state.transitionTo('index.main');
	       
        },
        function errorCallback(d) {

        	if(d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else{
            $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
			      toastr.error(d.data.mensaje, 'Error');
          }
        });
    };

    $scope.validaForm =function(){

      return  $scope.formRegistro.$valid;
    }

    $scope.registrarP = function(){
      $log.debug("JSON.stringify($scope.datosP)" + JSON.stringify($scope.datosP));
      // Enviar la fecha de nacimiento
      loginService.guardarIndividuo($scope.datosP.nombre,$scope.datosP.apellidoPaterno,$scope.datosP.apellidoMaterno,$scope.datosP.email,$scope.datosP.keyword,"",$scope.datosP.idSexo,$scope.datosP.rol).then(
        function successCallback(d) {
            $log.debug("d" + JSON.stringify(d));
            toastr.success(d, 'Ok');
            angular.element('#modal-form').modal('hide');
            $scope.datosP={};
            //$state.transitionTo('login');
         
        },
        function errorCallback(d) {
          if(d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else{
            $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
            toastr.error(d.data.mensaje, 'Error');
          }
        });


    };


});
