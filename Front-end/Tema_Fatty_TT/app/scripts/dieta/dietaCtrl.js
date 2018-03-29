/**
* dietaCtrl - Controlador ligado a la vista dieta_rigurosa.html
*/
angular.module('trabajoTerminal')
.controller('dietaCtrl', function($scope,$log,alimentoService,$cookies,NgTableParams,$filter){ 


$scope.alimentos = [];
$scope.dieta ={
  "Desayuno":[],
  "Colación 1":[],
  "Comida":[],
  "Colación 2":[],
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
        currentTiempo = $scope.tiempos[currentSlide].label;
      },
      afterChange: function (event, slick, currentSlide, nextSlide) {
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

},//Termina dietaRigurosaSlider

$scope.agregarAlimento = function(idAlimento){
  console.log("iniciaGraficaBarras : " + idAlimento);
  var currentAlimento = {};
  currentAlimento = $filter('filter')($scope.alimentos, {'idAlimento':idAlimento}, true)[0];

  //$scope.dieta = [];
  //$scope.dieta.push(currentAlimento);
  console.log(currentTiempo);
  $scope.dieta.currentTiempo.push(currentAlimento);
  //console.log(JSON.stringify($scope.dieta));
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


});


