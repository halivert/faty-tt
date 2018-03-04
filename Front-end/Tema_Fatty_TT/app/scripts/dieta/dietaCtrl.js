/**
* dietaCtrl - Controlador ligado a la vista dieta_rigurosa.html
*/
angular.module('trabajoTerminal')
.controller('dietaCtrl', function($scope,$log){ 

  $scope.tiempos = ["Desayuno","Colación 1","Comida","Colación 2","Cena"];
/**
* dietaRigurosaSlider  - Funcion que inicia la dependencia de slider por medio de Jquery 
*/
$scope.dietaRigurosaSlider = function(){
  $log.debug("dietaRigurosaSlider");

  angular.element(document).ready(function() {  
    tiempo = $('.tiempo')  
    tiempo.slick({
      dots: false,
      infinite: false
    });

    $('.slick-arrow').click(function(){
      console.log($scope.tiempos[tiempo.slick('slickCurrentSlide')])
    });  

    angular.element(document).ready(function() {  
      dTable = $('#dataTable-alimentos')  
      dTable.DataTable({"language": {
        "url": "https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
      },
      "paging":   false,
      "info":     false
    });  
    });  

  });
},//Termina dietaRigurosaSlider

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


