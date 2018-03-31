/**
* dietaCtrl - Controlador ligado a la vista dieta_rigurosa.html
*/
angular.module('trabajoTerminal')
.controller('dietaCtrl', function($scope,$log,alimentoService,$cookies,NgTableParams,$filter,pacientesService,dietaService){ 


$scope.alimentos = [];
$scope.dieta ={
  "Desayuno":[],
  "C1":[],
  "Comida":[],
  "C2":[],
  "Cena":[]
};
$scope.Array = [
           {name: "Verdura"},
           {name: "Fruta"},
           {name: "Cereales con grasa"},
           {name: "Cereales sin grasa"},
];
var currentTiempo = "";
$scope.tiempos = [{label: 'Desayuno'}, {label: 'Colación 1'}, {label: 'Comida'}, {label: 'Colación 2'}, {label: 'Cena'}];
$scope.numberLoaded = true;
$scope.selected;



/**
* slickConfig - Propiedades para el slider
*/
$scope.slickConfig = {
  enabled: true,
  autoplay: false,
  draggable: false,
  method: {},
  event: {
      beforeChange: function (event, slick, currentSlide, nextSlide) {
        //console.log("currentSlide" + $scope.tiempos[currentSlide].label);
        //currentTiempo = $scope.tiempos[currentSlide].label;
      },
      afterChange: function (event, slick, currentSlide, nextSlide) {
        currentTiempo = $scope.tiempos[currentSlide].label;
      }
  }
};
    

/**
* clickOptionFun - Funcion que invoca a recuperarListaAlimentos() cada que se selecciona un tipo de alimento
*/
$scope.clickOptionFun = function (index) {
  $scope.recuperarListaAlimentos(index.name);

}

/**
* recuperarListaAlimentos  - Funcion llama al service para recuperar una lista de alimentos
*/
$scope.recuperarListaAlimentos = function(tipoAlimento){

  if(tipoAlimento==undefined){
    tipoAlimento="Verdura";
  }
  alimentoService.recuperarAlimentos(tipoAlimento).then(
    function successCallback(d) {
      $scope.alimentos = d;
      $scope.tableParams = new NgTableParams({}, { dataset: d});
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        toastr.error(d.data.mensaje, 'Error');
      }
    }
  );

}//Termina dietaRigurosaSlider

$scope.agregarAlimento = function(idAlimento){
  var currentAlimento = {};
  currentAlimento = $filter('filter')($scope.alimentos, {'idAlimento':idAlimento}, true)[0];

  switch (currentTiempo) {
      case 'Desayuno':
          $scope.dieta.Desayuno.push(currentAlimento);
          break;
      case 'Colación 1':
          $scope.dieta.C1.push(currentAlimento);
          break;
      case 'Comida':
          $scope.dieta.Comida.push(currentAlimento);
          break;
      case 'Colación 2':
          $scope.dieta.C2.push(currentAlimento);
          break;
      case 'Cena':
          $scope.dieta.Cena.push(currentAlimento);
          break;            
      default:
          $scope.dieta.Desayuno.push(currentAlimento);
  }

  console.log("DIETA : " + JSON.stringify($scope.dieta));
}

/**
* iniciaGraficaBarras - 
*/
$scope.iniciaGraficaBarras = function(){
  $log.debug("iniciaGraficaBarras");

  $scope.labels = ['Proteinas','CH', 'Lipidos','Azucar'];
  $scope.series = ['Descripción Datos'];

  $scope.data = [
    [65, 59, 80, 70]
  ];

  $scope.colours = ['#FF4C4C'];

}//Termina iniciaGraficaBarras

$scope.mostrarInformacion = function(tipoAlimento){

 
  alimentoService.recuperarAlimentos(tipoAlimento).then(
    function successCallback(d) {
      $scope.alimentos = d;
      $scope.tableParams = new NgTableParams({}, { dataset: d});
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        toastr.error(d.data.mensaje, 'Error');
      }
    }
  );

}

$scope.mostrarDieta = function(tipoAlimento){

  dietaService.recuperarDieta(1,2).then(
    function successCallback(d) {
     $scope.dieta = angular.fromJson(d.alimentosDisponibles);
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        toastr.error(d.data.mensaje, 'Error');
      }
    }
  );
}

});


