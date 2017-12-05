angular.module('trabajoTerminal')


.controller('pacientesCtrl', function($scope,$log,pacientesService,toastr,blockUI,$cookies,$filter){
  
   $scope.pacientes = [];
   $scope.historialclinico = [];
   $scope.usuario = {
	   id:''
   };
   $scope.currentPaciente={};

    $scope.verPacientes = function(){
      $log.debug("ver pacientes");

      var idUsuario =  $cookies.get("idUsuario")

      $log.debug("idUsuario : " + idUsuario);

       pacientesService.recuperarListaPacientes(idUsuario).then(
          function successCallback(d) {
             $scope.pacientes=d;
          },
        
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              toastr.error(d.data.mensaje, 'Error');
            }
          });
        
    },
	
	$scope.recuperaPaciente = function(){
		console.log("$scope.usuario.id : " + $scope.usuario.id);
		$scope.currentPaciente = $filter('filter')($scope.pacientes, {'id_USUARIO':$scope.usuario.id});
		console.log("$scope.currentPaciente : " + JSON.stringify($scope.currentPaciente) );
	},
	
	$scope.verHistorial = function(){
		$log.debug("$scope.usuario" + $scope.usuario.id);
		pacientesService.recuperarListaHistorialClinico($scope.usuario.id).then(
          function successCallback(d) {
			if(d.length == 0){
				toastr.warning("El paciente aún no tiene historial clínico.", 'Atención');
			}
           
          },
        
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
              toastr.error(d.data.mensaje, 'Error');
            }
          });
		
	},
	
	$scope.calculateAge = function calculateAge(birthday) { // birthday is a date
	var birthDate = new Date(birthday);
    var ageDifMs = Date.now() - birthDate.getTime();
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
	
    return Math.abs(ageDate.getUTCFullYear() - 1970);
}

});
