/**
* pacientesCtrl - Controlador ligado a la vista pacientes.html
*/
angular.module('trabajoTerminal')
.controller('pacientesCtrl', function($scope,$log,pacientesService,toastr,blockUI,$cookies,$filter,$state,$cookies){

  $scope.pacientes = [];
  $scope.listhistorialclinico = [];
  //$scope.historial = {};
  $scope.usuario = {
    id:''
  };
  $scope.currentPaciente={};
  $scope.numero=0;

/**
* verPacientes  - Funcion que se ejecuta cuando se carga la vista de pacientes.html, lista los pacientes asociados a un medico
*/
$scope.verPacientes = function(){
  $log.debug("ver pacientes");

  var idUsuario =  $cookies.get("idUsuario")
  pacientesService.recuperarListaPacientes(idUsuario).then(
    function successCallback(d) {
      $scope.pacientes=d;

        //Integracion de los elementos recuperados por Angular con el plugin Datatable de JQuery
        angular.element(document).ready(function() {  
          dTable = $('#dataTable-pacientes')  
          dTable.DataTable({"language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
          }});  
        });  
      },
      function errorCallback(d){
        if(d.data == null)
          toastr.warning("Servicio no disponible", 'Advertencia');
        else{
          toastr.error(d.data.mensaje, 'Error');
        }
      });
},

$scope.numeroPacientes = function(){
  $log.debug("numero pacientes");

  var idUsuario =  $cookies.get("idUsuario")

  $log.debug("idUsuario : " + idUsuario);

   pacientesService.recuperarNumeroPacientes(idUsuario).then(
      function successCallback(d) {
	  $log.debug("d.length : " + d.length);
         $scope.numero=d.length;
      },
    
      function errorCallback(d) {
        if(d.data == null)
          toastr.warning("Servicio no disponible", 'Advertencia');
        else{
          toastr.error(d.data.mensaje, 'Error');
        }
      });
        
    },
	
/**
* recuperaPaciente  - Seleccionar la infor de un paciente un paciente
*/
$scope.recuperaPaciente = function(){
  console.log("$scope.usuario.id : " + $scope.usuario.id);
  $scope.currentPaciente = $filter('filter')($scope.pacientes, {'id_USUARIO':$scope.usuario.id});
  console.log("$scope.currentPaciente : " + JSON.stringify($scope.currentPaciente) );
},

/**
* verHistorial  - Recuperar la informacion del historial clinico del paciente seleccionado
*/
$scope.verHistorial = function(){
  var idCurrentPaciente =  $cookies.get("idCurrentPaciente");
  pacientesService.recuperarListaHistorialClinico(idCurrentPaciente).then(
    function successCallback(d) {
      if(d.length == 0){
        toastr.warning("El paciente aún no tiene historial clínico.", 'Atención');
      }//else{
        $scope.listhistorialclinico = d;
        $log.debug("JSON.stringify(d)" + JSON.stringify(d));
      //}
      
      angular.element(document).ready(function() {  
          dTable = $('#dataTable-historial-clinico')  
          dTable.DataTable({"language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
          }});  
        });
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
* setCurrentPaciente  - 
*/
$scope.setCurrentPaciente = function(){
  $cookies.put("idCurrentPaciente",$scope.usuario.id);
  $state.transitionTo('index.historialClinico');
},

/**
* guardarHistorialClinico  - Guardar la informacion del historial clinico del paciente seleccionado
*/
$scope.guardarHistorialClinico = function(){
  console.log("guardarHistorialClinico()");
  var date = new Date();
    console.log("date : " + date);

  pacientesService.guardarInfoHistorialClinico($scope.usuario.id,$scope.historial.peso,$scope.historial.talla,$scope.historial.estatura,$scope.historial.imc,$scope.historial.lipidos,$scope.historial.carbohidratos,$scope.historial.proteinas,$scope.historial.azucar).then(
    
    function successCallback(d) {
      angular.element('#modal-paciente').modal('hide');
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
  var date = new Date();
    console.log("date : " + date);

  $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
  $scope.series = ['Series A'];
  $scope.data = [
    [65, 59, 80, 81, 56, 55, 40]
  ];
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

/**
* calculateAge  - Calcular la edad de los pacientes con base en su fecha de nacimiento
*/
$scope.calculateAge = function calculateAge(birthday) { // birthday is a date
  var birthDate = new Date(birthday);
  var ageDifMs = Date.now() - birthDate.getTime();
  var ageDate = new Date(ageDifMs); // miliseconds from epoch
  return Math.abs(ageDate.getUTCFullYear() - 1970);
}

});


