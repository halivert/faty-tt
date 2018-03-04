/**
* pacientesCtrl - Controlador ligado a la vista pacientes.html
*/
angular.module('trabajoTerminal')
.controller('pacientesCtrl', function($scope,$log,pacientesService,loginService,toastr,blockUI,$cookies,$filter,$state,$cookies,glucosaService){

  $scope.pacientes = [];
  $scope.listhistorialclinico = [];
  $scope.usuario = {
    id:''
  };
  var idHistorial = "";
  var idCurrentPaciente = "";
  $scope.nombreCompletoPaciente;
  $scope.currentPaciente={};
  $scope.numero=0;
  $scope.desactivarBoton=true;
  $scope.historialDetalle={};
  $scope.imc;
  $scope.historial={
    peso:'',
    altura:''
  }
  

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
* recuperaPaciente  - Seleccionar la info de un paciente 
*/
$scope.recuperaPaciente = function(idCurrentPaciente){
  console.log("idCurrentPaciente : " + idCurrentPaciente);
  $scope.currentPaciente = $filter('filter')($scope.pacientes, {'id_USUARIO':idCurrentPaciente});
  console.log("$scope.currentPaciente : " + JSON.stringify($scope.currentPaciente) );
},

$scope.recuperaInfoPaciente = function(idCurrentPaciente){

  var nombre = loginService.recuperarInformacionUsuario(idCurrentPaciente).then( 
    function successCallback(informacionUsuario){
      $scope.currentPaciente.nombreCompletoPaciente = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
      $scope.currentPaciente.fechaNac = informacionUsuario.fechaNacimiento;
      $scope.currentPaciente.edad = $scope.calculateAge(informacionUsuario.fechaNacimiento); 
      console.log("$scope.currentPaciente.nombreCompletoPaciente " + $scope.currentPaciente.nombreCompletoPaciente); 
    });
}
/**
* verHistorial  - Recuperar la informacion del historial clinico del paciente seleccionado
*/
$scope.verHistorial = function(){
  idCurrentPaciente =  $cookies.get("idCurrentPaciente");
  //Se llama a la funcion $scope.recuperaPaciente, para tener los datos del paciente
  blockUI.start();
  $scope.recuperaInfoPaciente(idCurrentPaciente);
  

  pacientesService.recuperarListaHistorialClinico(idCurrentPaciente).then(
    function successCallback(d) {
      if(d.length == 0){
        toastr.warning("El paciente aún no tiene historial clínico.", 'Atención');
      }//else{
        $scope.listhistorialclinico = d;
        //$log.debug("JSON.stringify(d)" + JSON.stringify(d));
        console.log("JSON.stringify(d)" + JSON.stringify(d));
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
  blockUI.stop();
},

/**
* verUltimoHistorial - Establece el idCurrentPaciente y despues redirecciona a la pagina informacion_general
*/
$scope.verUltimoHistorial = function(idCurrentPaciente){

  $cookies.put("idCurrentPaciente",idCurrentPaciente);
  $state.transitionTo('index.informacionGeneral');
},
/**
* mostrarInformacionGeneral - Funcion que se ejecuta cuando carga la pagina informacion_general, la info que se mostrara es el ultimo historial clinico del paciente
*/
$scope.mostrarInformacionGeneral = function(){
  blockUI.start();
  pacientesService.recuperaUltimoHistorial($cookies.get("idCurrentPaciente")).then(

    function successCallback(d) {
      $scope.historialDetalle = d;
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
},

/**
* setCurrentPaciente  - Funcion que guarda el id del paciente en una cookie y hace la transicion para la vista historialClinico 
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

  pacientesService.guardarInfoHistorialClinico($scope.usuario.id,$scope.historial.peso,$scope.historial.talla,$scope.historial.altura,$scope.historial.imc,$scope.historial.lipidos,$scope.historial.carbohidratos,$scope.historial.proteinas,$scope.historial.azucar).then(

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
* recuperaHistorial  - Recuperar la informacion del historial clinico del paciente seleccionado
*/
$scope.recuperaHistorial = function(idHistorial){
  console.log("recuperaHistorial()");
  console.log("idHistorial : " + idHistorial);
  blockUI.start();
  idCurrentPaciente = $cookies.get("idCurrentPaciente")

  pacientesService.recuperarDetallHistorialClinico(idCurrentPaciente,idHistorial).then(

    function successCallback(d) {
      console.log(JSON.stringify(d));
      $scope.historialDetalle = d;
      $scope.activarBoton();
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

},  

/**
* calculateAge  - Calcular la edad de los pacientes con base en su fecha de nacimiento
*/
$scope.calculateAge = function calculateAge(birthday) { // birthday is a date
  var birthDate = new Date(birthday);
  var ageDifMs = Date.now() - birthDate.getTime();
  var ageDate = new Date(ageDifMs); // miliseconds from epoch
  return Math.abs(ageDate.getUTCFullYear() - 1970);
},

/**
* activarBoton  - 
*/
$scope.activarBoton = function activarBoton() { 
  $scope.desactivarBoton=false;
},

/**
* activarBoton  - 
*/
$scope.esValido = function esValido() { 
  return $scope.desactivarBoton;
},

/**
* validaForm  - Funcion que valida el formulario del modal para agregar un nuevo historial clinico 
*/
$scope.validaForm =function(){
  if($scope.formHistorial.$valid){
    console.log($scope.historial.imc = ($scope.historial.peso)/Math.pow($scope.historial.altura,2));
    return  true;
  }
  else{
    if($scope.historial.peso && $scope.historial.altura){
      $scope.historial.imc=  ($scope.historial.peso)/Math.pow($scope.historial.altura,2);
    }else{
      $scope.historial.imc = "IMC"
    }
    return false   
  }
}


});


