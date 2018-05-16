angular
.module('trabajoTerminal')

.controller('glucosaCtrl', function($scope,$log,toastr,blockUI,$cookies,$state,$cookies,$filter,glucosaService,SweetAlert){

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
  var instance;
  var labels;
  var data;
  d.forEach(function (data) {
    $scope.labels.push(data.fechaRegistro);
    $scope.data[0].push(data.azucar);

  });

  $scope.$on('chart-create', function(event, instance){
    $scope.chart = instance.chart;
    
  });

  $scope.onClick = function (elements, e) {

    glucosaService.recuperarRegistro(elements,e,d,$scope.chart,$scope.labels, $scope.data);
console.log("Click");
/*SweetAlert
        .input('Your name', 'What is it?')
        .then(function (response) {
            // Process response
        });*/

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

/*var recuperarRegistro = function(elements,e,d){

  var pos = Chart.helpers.getRelativePosition(e, $scope.chart);


  var intersect = elements.find(function(element) {
    return element.inRange(pos.x, pos.y);
  });
  if(intersect){
    for(i in d) {
      if(d[i].fechaRegistro==$scope.labels[intersect._index] && d[i].azucar==$scope.data[intersect._datasetIndex][intersect._index]){ 
        console.log(JSON.stringify(d[i]));
        
         var idPaciente = d[i].idPaciente;
        var idRegistroGlucosa = d[i].idRegistroGlucosa;

        swal({
          title: "Editar registro",
          text: "Fecha : " + d[i].fechaRegistro + "\x0A Nivel de glucosa : " + d[i].azucar,
          content: "input",
          buttons: ["Cancelar", "Actualizar registro"],
        }).then(name => {
          if (!name) throw null;

          var data = {"azucar": name}
         
          console.log("idRegistroGlucosa" + idRegistroGlucosa);
          return fetch(
            "http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/registroglucosa/"+idRegistroGlucosa,{
              method : "PUT",
              headers : {'Content-Type': 'application/json'},
              body : JSON.stringify(data)
            });
        })
        .then(results => {
          return results.json();
        })
        .then(json => {
          //const movie = json.results[0];

          console.log("RESPUESTA : " + JSON.stringify(json));
          if (json.respuesta == "ERROR") {
            return swal("Error", json.mensaje , "error");
          }

          swal(json.respuesta, json.mensaje, "success")
          .then((value) => {
            console.log("AQUI:::");
            location.reload();
          });
        })
        .catch(err => {
          if (err) {
            return swal("Error", err.mensaje , "error");
          } else {
            swal.stopLoading();
            swal.close();
          }

        });
      }
    }

  }

}*/

});
