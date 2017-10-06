angular
    .module('trabajoTerminal')

.controller('indiceMasaCorporalCtrl', function($scope,$log){
  //////////////VARIABLES////////////////////////
  $scope.nombrePagina = "Calcular el IMC";
  $scope.respuestaObject={
        peso:'',
        altura:''
  };
  $scope.imc = '';

  ///////////////////////FUNCIONES/////////////////////////

  //edgar.hurtado concluir la validacion
  $scope.validarPesoAltura = function(){
    $log.debug("inicia validarPesoAltura()");
    if(($scope.respuestaObject.peso.trim() == '' || $scope.respuestaObject.peso.trim() == 0) || ($scope.respuestaObject.altura.trim() == '' || $scope.respuestaObject.altura.trim() == 0)){
      return true;
    }
    else{
      return false;
    }
  };

  $scope.calcularIMC = function(){
    $log.debug("inicia calcularIMC()");
    $scope.imc = ($scope.respuestaObject.peso)/Math.pow($scope.respuestaObject.altura,2);
  };

});
