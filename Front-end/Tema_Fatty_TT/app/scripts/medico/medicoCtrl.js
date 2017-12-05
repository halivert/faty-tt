angular.module('trabajoTerminal')

.controller('medicoCtrl', function($scope,$cookies,$log,medicoService){

 $scope.codigo = "sdfsd";
 $scope. algo="algo";
 
	$scope.generarCodigo = function(){
      $log.debug("generar codigo");

	  
      var idMedico =  $cookies.get("idUsuario")

       medicoService.generaCodigoMedico(idMedico).then(
          function successCallback(d) {
             
			 $log.debug("d.mensaje : " + d.mensaje)
			 $cookies.put("token",d.mensaje);
          },
        
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              toastr.error(d.data.mensaje, 'Error');
            }
          });
        
    },
	
	$scope.verToken = function(){
        return $cookies.get("token");
    }
	
	
});