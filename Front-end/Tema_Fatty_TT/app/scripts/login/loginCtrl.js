
angular.module('trabajoTerminal')

.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr,$cookies,blockUI){

	$scope.titulo = "Bienvenido a CERES";




	$scope.usuarioObject={
        email:'',
        password:''
  };
  $scope.datosU={
    nombre:'',
    apellidoPaterno:'',
    apellidoMaterno:'',
    idSexo:'',
    email:'',
    keyword:'',
    rol:'',
    cedula:'',
    codigoM:''
  };

	$scope.limpiaInfo = function(){
		$cookies.put("auth","false");
		
		$cookies.remove("rol");
		$cookies.remove("nombre","");
		$cookies.remove("idUsuario","");
	}
  
    $scope.iniciarSesion = function(){
      
      loginService.iniciaSesionService($scope.usuarioObject.email,$scope.usuarioObject.password).then(
      	function successCallback(d) {
          blockUI.start();
          
              $cookies.put("auth","true");
              $cookies.put("idUsuario",d.idUsuario);
    	        toastr.success(d.mensaje,'Ok');
    	        //$state.transitionTo('index.main');

              loginService.recuperarInformacionUsuario(d.idUsuario).then( 
                    function successCallback(informacionUsuario){
                      var nombreCompleto = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
                      $log.debug("nombreCompleto : " + nombreCompleto);
                      $log.debug("informacionUsuario.idRol : " + informacionUsuario.idRol);
                      $cookies.put("rol",informacionUsuario.idRol);
                      $cookies.put("nombre",nombreCompleto);  
						$state.transitionTo('index.main');
						
                  });
         blockUI.stop();
	       
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

      if($scope.formRegistro.$valid)
        return  $scope.validaPasword();
      else 
        return false;
    }

    $scope.validaPasword = function(){
        return  ($scope.datosU.keyword === $scope.datosU.confirmpass ? true : false); 
    }

    $scope.registrarUsuario = function(){
      $log.debug("JSON.stringify($scope.datosU)" + JSON.stringify($scope.datosU));
      // Enviar la fecha de nacimiento
      loginService.guardarUsuario($scope.datosU.nombre,$scope.datosU.apellidoPaterno,$scope.datosU.apellidoMaterno,$scope.datosU.email,$scope.datosU.keyword,"",$scope.datosU.idSexo,$scope.datosU.rol,$scope.datosU.cedula,$scope.datosU.codigoM).then(
        function successCallback(d) {
            $log.debug("d" + JSON.stringify(d));
            toastr.success(d, 'Ok');
            angular.element('#modal-form').modal('hide');
            $scope.datosU={};
         
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
