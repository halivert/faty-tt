angular
.module('trabajoTerminal')

.controller('alimentoCtrl', function($scope,$log,toastr,blockUI,$cookies,$state,$cookies,$filter,alimentoService){


$scope.tipoAlimento = ["Verdura","Fruta","Cereales con grasa","Cereales sin grasa"];
$scope.alimentos = [];

/**
* recuperarAlimentos  - Funcion para recuperar una lista de alimentos
*/
$scope.recuperarAlimentos = function(){
  console.log("recuperarAlimentos()");

  
  alimentoService.recuperarAlimentos($scope.tipoAlimento).then(

    function successCallback(d) {
      console.log("ALIMENTOS : " + JSON.stringify(d));
      $scope.alimentos = d;
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
