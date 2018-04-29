angular
.module('trabajoTerminal')

.controller('glucosaCtrl', function($scope,$log,toastr,blockUI,$cookies,$state,$cookies,$filter,glucosaService){

 $scope.registroGlucosa={
  azucar:'',
  fechaReg:''
}	

$scope.fechaFin = new Date();
var fechaActual = new Date();
$scope.fechaInicio = fechaActual.setMonth(fechaActual.getMonth()-1);

$scope.fechaFin = $filter('date')(new Date($scope.fechaFin), 'dd/MM/yyyy');

$scope.fechaInicio = $filter('date')(new Date($scope.fechaInicio), 'dd/MM/yyyy');

/**
* guardarHistorialClinico  - Guardar la informacion del historial clinico del paciente seleccionado
*/
$scope.guardarRegistroGlucosa = function(){

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
},

$scope.mostrarRegistrosGlucosaPorFiltros = function(){
  /*Cookie que se recupera del inicio de sesion*/
  var idPaciente =  $cookies.get("idUsuario")
  console.log($scope.fechaInicio);
  var formatoFechaInicio = $scope.fechaInicio.replace(/\//g, "-");
  var formatoFechaFin = $scope.fechaFin.replace(/\//g, "-"); 
  glucosaService.recuperarRegistrosGlucosaPorFiltros(idPaciente,formatoFechaInicio,formatoFechaFin).then(

    function successCallback(d) {
      toastr.success('Se muestran los registros de glucosa', 'Ok');
      grafica(d);
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
        toastr.error(d.data.mensaje, 'Error');
      }
    });

},

/**
* recuperarRegistrosGlucosa  - Recupera los registros de glucosa de un paciente, por medio del idUsuario
*/
$scope.recuperarRegistrosGlucosa = function(){

  blockUI.start();
  var idPaciente =  $cookies.get("idCurrentPaciente")

  glucosaService.recuperarRegistrosGlucosaService(idPaciente).then(

    function successCallback(d) {
    grafica(d);
      
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
        toastr.error(d.data.mensaje, 'Error');
      }
    });
  blockUI.stop();

}

var grafica = function(d){
  $scope.series = ['Nivel de glucosa'];

      $scope.labels = [];
      $scope.data = [[]];

      
        d.forEach(function (data) {
          $scope.labels.push(data.fechaRegistro);
          $scope.data[0].push(data.azucar);
        });

      $scope.$on('chart-create', function(event, instance){
        $scope.chart = instance.chart;
      });

      $scope.onClick = function (elements, e) {

      recuperarRegistro(elements,e,d);

      };
      $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];
      $scope.options = {
        scales: {
          yAxes: [
          {
            id: 'y-axis-1',
            type: 'linear',
            display: true,
            position: 'left'
          }
          ]
        }
      };
}

var recuperarRegistro = function(elements,e,d){

  var pos = Chart.helpers.getRelativePosition(e, $scope.chart);

      
      var intersect = elements.find(function(element) {
        return element.inRange(pos.x, pos.y);
      });
      if(intersect){
        for(i in d) {
        if(d[i].fechaRegistro==$scope.labels[intersect._index] && d[i].azucar==$scope.data[intersect._datasetIndex][intersect._index]){ 
        console.log(JSON.stringify(d[i]));
      }
      }

      }

}

});
