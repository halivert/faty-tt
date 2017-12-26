/**
* medicoCtrl  - Controlador ligado a la vista medico.html
*/
angular.module('trabajoTerminal')
.controller('medicoCtrl', function($scope,$cookies,$log,medicoService){

  $scope.codigo = "sdfsd";
  $scope. algo="algo";

/**
* generarCodigo  - Funcion que solicita la creacion de un codigo para el medico
*/
$scope.generarCodigo = function(){

  $log.debug("generar codigo");
  var idMedico =  $cookies.get("idUsuario")

  medicoService.generaCodigoMedico(idMedico).then(
    function successCallback(d){
      $log.debug("d.mensaje : " + d.mensaje)
      $cookies.put("token",d.mensaje);
    },
    function errorCallback(d){
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        toastr.error(d.data.mensaje, 'Error');
      }
    });
},

/**
* verToken  - Funcion que sirve para mostrar en vista el token (codigo de medico) que se genero en la funcion generarCodigo
*/
$scope.verToken = function(){
  return $cookies.get("token");
}

});