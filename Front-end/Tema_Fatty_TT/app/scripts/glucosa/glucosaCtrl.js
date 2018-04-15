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
},

/**
* recuperarRegistrosGlucosa  - Recupera los registros de glucosa de un paciente, por medio del idUsuario
*/
$scope.recuperarRegistrosGlucosa = function(){
  console.log("recuperarRegistrosGlucosa()");

  blockUI.start();
  var idPaciente =  $cookies.get("idCurrentPaciente")

  glucosaService.recuperarRegistrosGlucosaService(idPaciente).then(

    function successCallback(d) {
      $scope.series = ['Nivel de glucosa'];

      $scope.labels = [];
      $scope.data = [[]];

      
      d.forEach(function (data) {
        $scope.labels.push(data.fechaRegistro);
        $scope.data[0].push(data.azucar);
      });

      $scope.onClick = function (points, evt) {
        console.log(points, evt);
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

});
