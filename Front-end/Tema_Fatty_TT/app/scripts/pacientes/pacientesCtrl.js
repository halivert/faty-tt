angular.module('trabajoTerminal')

.controller('pacientesCtrl', function($scope,$log,pacientesService,toastr,blockUI,$cookies){
  
   $scope.pacientes = [];

    $scope.verPacientes = function(){
       $log.debug("ver pacientes");

      var idMedico =  $cookies.get("idMedico")

      $log.debug("idMedico : " + idMedico);

       pacientesService.recuperarListaPacientes(idMedico).then(
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

});
