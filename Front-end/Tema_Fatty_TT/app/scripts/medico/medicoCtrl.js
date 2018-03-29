/**
* medicoCtrl  - Controlador ligado a la vista medico.html
*/
angular.module('trabajoTerminal')
.controller('medicoCtrl', function($scope,$cookies,$log,medicoService,toastr){

  $scope.codigo = "sdfsd";
  $scope.algo="algo";
  $scope.codigo="";
  $scope.correoPaciente="";

/**
* generarCodigo  - Funcion que solicita la creacion de un codigo para el medico
*/
$scope.generarCodigo = function(){

  $log.debug("generar codigo");
  var idMedico =  $cookies.get("idUsuario")

  medicoService.generaCodigoMedico(idMedico).then(
    function successCallback(d){
      $log.debug("d.mensaje : " + d.mensaje)
      //$cookies.put("token",d.mensaje);
      $scope.codigo = d.mensaje;
    },
    function errorCallback(d){
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        toastr.error(d.data.mensaje, 'Error');
      }
    });
},

$scope.enviarCodigo = function(){

  $log.debug("enviar codigo");
  var idMedico =  $cookies.get("idUsuario")
  var correoPacienteArray=[];

  correoPacienteArray.push($scope.correoPaciente);
  medicoService.enviarCodigoMedico(correoPacienteArray, $scope.codigo, $cookies.get("nombre"), "correo@gmail.com", idMedico).then(
    function successCallback(d){
      $log.debug("d.mensaje : " + d.mensaje)
      //$cookies.put("token",d.mensaje);
      toastr.success(d.mensaje, d.respuesta);
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
},

$scope.validarCorreo = function(){
    if($scope.formCodigoMedico.$valid)
      return  true;
    else 
      return false;
},

$scope.validarCorreoCodigo = function(){
    if($scope.validarCorreo() && $scope.codigo.length == 6)
      return  true;
    else 
      return false;
}

});