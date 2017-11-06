angular.module('trabajoTerminal')

.controller('pacientesCtrl', function($scope,$log,pacientesService,toastr,blockUI,$cookies){
  
   $scope.pacientes = [];
   $scope.historialclinico = [];
   $scope.usuario = "";

    $scope.verPacientes = function(){
      $log.debug("ver pacientes");

      var idUsuario =  $cookies.get("idUsuario")

      $log.debug("idUsuario : " + idUsuario);

       pacientesService.recuperarListaPacientes(idUsuario).then(
          function successCallback(d) {
            $log.debug("d" + JSON.stringify(d));
            
             $scope.pacientes=d;
          },
        
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
              toastr.error(d.data.mensaje, 'Error');
            }
          });
        
    }
	
	$scope.verHistorial = function(){
		$log.debug("$scope.usuario" + $scope.usuario);
		pacientesService.recuperarListaHistorialClinico($scope.usuario).then(
          function successCallback(d) {
            $log.debug("d" + JSON.stringify(d));
           
          },
        
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
              toastr.error(d.data.mensaje, 'Error');
            }
          });
		
	}
	
	$scope.calcularEdad = function(nacimiento){
		$log.debug("calcular Edar");
		var ageDifMs = Date.now() - new Date(nacimiento);
		var ageDate = new Date(ageDifMs); // miliseconds from epoch
		return Math.abs(ageDate.getUTCFullYear() - 1970);
	}

});
