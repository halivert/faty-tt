angular
.module('trabajoTerminal')

.controller('glucosaCtrl', function($scope,$log,toastr,blockUI,$cookies,$state,$cookies,$filter,glucosaService){

 $scope.registroGlucosa={
    azucar:'',
    fechaReg:''
  }	

/**
* guardarHistorialClinico  - Guardar la informacion del historial clinico del paciente seleccionado
*/
$scope.guardarRegistroGlucosa = function(){
  console.log("guardarRegistroGlucosa()");

  /*Cookie que se recupera del inicio de sesion*/
  var idPaciente =  $cookies.get("idUsuario")

  glucosaService.guardarRegistroGlucosa(idPaciente,$scope.registroGlucosa.azucar,$scope.registroGlucosa.fechaReg).then(

    function successCallback(d) {
      toastr.success(d, 'Ok');
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
