
angular.module('trabajoTerminal')

.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr,$cookies,blockUI){

	$scope.titulo = "Bienvenido a CERES";




	$scope.usuarioObject={
        email:'',
        password:''
  };
  $scope.datosI={

  };

  $scope.limpiaInfo = function(){
  $cookies.put("auth","false");
  //$cookies.put("idIndividuo","");
   $cookies.put("rol","");
   $cookies.put("nombre","");
 }
  
//var informacionIndividuo = {};
    $scope.iniciarSesion = function(){
      $log.debug("iniciarSesion");
      
      loginService.iniciaSesionService($scope.usuarioObject.email,$scope.usuarioObject.password).then(
      	function successCallback(d) {
          //$cookies.put("idIndividuo",d.idIndividuo);
          
          blockUI.start();
          
          $cookies.put("auth","true");
          $cookies.put("rol",d.individuoRol);
	        toastr.success(d.mensaje,'Ok');
	        $state.transitionTo('index.main');


          loginService.recuperarInformacionInd(d.idIndividuo,d.individuoRol).then(

                                  function successCallback(infoIndividuo){
                                   // var informacionIndividuo = infoIndividuo;
                                    $log.debug("informacionIndividuo.nombreCompleto : " + infoIndividuo.nombreCompleto);
                                    var nombre = infoIndividuo.nombreCompleto;
                                    $cookies.put("nombre",nombre);
                                    if(d.individuoRol == 1){
                                        $cookies.put("idMedico",infoIndividuo.idMedico);
                                    }
                                    if(d.individuoRol == 0){
                                        $cookies.put("idPaciente",infoIndividuo.idPaciente);
                                    }
                                    
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
        return  ($scope.datosI.keyword === $scope.datosI.confirmpass ? true : false); 
    }

    $scope.registrarP = function(){
      $log.debug("JSON.stringify($scope.datosI)" + JSON.stringify($scope.datosI));
      // Enviar la fecha de nacimiento
      loginService.guardarIndividuo($scope.datosI.nombre,$scope.datosI.apellidoPaterno,$scope.datosI.apellidoMaterno,$scope.datosI.email,$scope.datosI.keyword,"",$scope.datosI.idSexo,$scope.datosI.rol,$scope.datosI.cedula).then(
        function successCallback(d) {
            $log.debug("d" + JSON.stringify(d));
            toastr.success(d, 'Ok');
            angular.element('#modal-form').modal('hide');
            $scope.datosI={};
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
