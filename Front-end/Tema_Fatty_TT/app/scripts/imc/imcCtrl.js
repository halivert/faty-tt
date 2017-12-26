/**
* indiceMasaCorporalCtrl  - Controlador que se usa en la vista imc.html, contiene la funcion para calcular el IMC
*/
angular
.module('trabajoTerminal')
.controller('indiceMasaCorporalCtrl', function($scope,$log){
 
  $scope.nombrePagina = "Calcular el IMC";
  $scope.respuestaObject={
    peso:'',
    altura:''
  };
  $scope.imc = '';

  
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

/**
* calcularIMC  - Funcion para calcular el indice de masa corporal
*/
  $scope.calcularIMC = function(){
    $log.debug("inicia calcularIMC()");
    $scope.imc = ($scope.respuestaObject.peso)/Math.pow($scope.respuestaObject.altura,2);
  };

});
