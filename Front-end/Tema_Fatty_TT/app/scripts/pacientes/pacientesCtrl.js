/**
* pacientesCtrl - Controlador ligado a la vista pacientes.html
*/
angular.module('trabajoTerminal')
.controller('pacientesCtrl', function($scope,$log,pacientesService,loginService,toastr,blockUI,$cookies,$filter,$state,$cookies,glucosaService,dietaService,$timeout){

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
  $scope.valoresNutrimentales={};
  $scope.imc;
  $scope.historial={
    peso:'',
    altura:''
  }
  $scope.esEditable = true;

  var edadPaciente = 0;
  $scope.edadPaciente;
  
  $scope.limiteRegistros = [{lim: "5"},{lim: "10"},{lim: "15"},{lim: "20"},{lim: "25"}, {lim: "30"}];
  $scope.Array = [{name: "Muy leve", value:"0"},{name: "Leve", value:"1"},{name: "Moderado", value:"2"},{name: "Intenso", value:"3"},{name: "Excepcional", value:"4"}];

  $scope.clickOptionFun = function(index) {
    $scope.historial.af = index.value;
  }

/**
* verPacientes  - Funcion que se ejecuta cuando se carga la vista de pacientes.html, lista los pacientes asociados a un medico
*/
$scope.verPacientes = function(){

  //Se limpian cookies en caso de ser necesario
  $cookies.remove("historialDetalle");
  $cookies.remove("valoresNutrimentales");
  var idUsuario =  $cookies.get("idUsuario")
  pacientesService.recuperarListaPacientes(idUsuario).then(
    function successCallback(d) {
      $scope.pacientes=d;
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

/**
* numeroPacientes - Recupera el numero de pacientes asociados a un medico
*/
$scope.numeroPacientes = function(){

  var idUsuario =  $cookies.get("idUsuario")

  pacientesService.recuperarNumeroPacientes(idUsuario).then(
    function successCallback(d) {
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
* redirectToPatiens - Redir4ecciona a la lista de pacientes
*/
$scope.redirectToPatiens = function(){
  $state.transitionTo('index.pacientes');
},

/**
* recuperaPaciente  - Seleccionar la info de un paciente 
*/
$scope.recuperaPaciente = function(idCurrentPaciente){
  $scope.currentPaciente = $filter('filter')($scope.pacientes, {'id_USUARIO':idCurrentPaciente});
},

/**
* recuperaInfoPaciente - Funcion que recupera datos generales del paciente que selecciono el medico 
*/
$scope.recuperaInfoPaciente = function(idCurrentPaciente){
  blockUI.start();
  var nombre = loginService.recuperarInformacionUsuario(idCurrentPaciente).then( 
    function successCallback(informacionUsuario){
      $scope.currentPaciente.nombreCompletoPaciente = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
      $scope.currentPaciente.fechaNac = informacionUsuario.fechaNacimiento;

      $scope.currentPaciente.edad = informacionUsuario.edad; 

    });
  blockUI.stop();
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
      }
      $scope.listhistorialclinico = d;
      
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
  var idPaciente = $cookies.get("idCurrentPaciente");
  
  //$scope.recuperaInfoPaciente(idPaciente);

  loginService.recuperarInformacionUsuario(idPaciente).then( 
    function successCallback(informacionUsuario){
      $scope.currentPaciente.nombreCompletoPaciente = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
      $scope.currentPaciente.fechaNac = informacionUsuario.fechaNacimiento;

      $scope.currentPaciente.edad = informacionUsuario.edad;

      pacientesService.recuperaUltimoHistorial(idPaciente).then(
    function successCallback(d) {

      if(d.respuesta != "ERROR"){
        $scope.historialDetalle = d;

        var index = d.actividadFisica
        $scope.obj = $filter('filter')($scope.Array, {name: d.actividadFisica}, true)[0];

        dietaService.obtenerValoresNutrimentales(d.idPaciente,$scope.currentPaciente.edad,d.peso,d.estatura,$scope.obj.value,d.sexo,d.lipidos,d.carbohidratos,d.proteinas).then(
          function successCallback(d1) {
            $scope.valoresNutrimentales = d1;
            $cookies.put("historialDetalle",JSON.stringify($scope.historialDetalle));
            $cookies.put("valoresNutrimentales",JSON.stringify(d1));
            
            $scope.desactivarBoton = false;
          },
          function errorCallback(d) {
            if(d.data == null)
              toastr.warning("Servicio no disponible", 'Advertencia');
            else{
              console.log("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
              toastr.error(d.data.mensaje, 'Error');
            }
          });
      }else{
        $cookies.put("nombrePaciente",$scope.currentPaciente.nombreCompletoPaciente);
        toastr.warning("El paciente no cuenta con historial clínico", 'Advertencia');
        $scope.desactivarBoton = true;
      }
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
        toastr.error(d.data.mensaje, 'Error');
      }
    });

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
  
  var macronutrientes = parseInt($scope.historial.lipidos) + parseInt($scope.historial.carbohidratos) + parseInt($scope.historial.proteinas);
  
  if(macronutrientes > 100){
    toastr.error("La suma de macronutrientes supera el 100%","Error");
  }
  else if(macronutrientes < 100){
    toastr.error("La suma de macronutrientes no alcanza el 100%","Error");    
  }
  else{
  pacientesService.guardarInfoHistorialClinico($scope.usuario.id,$scope.historial.peso,$scope.historial.talla,$scope.historial.altura,$scope.historial.imc,$scope.historial.lipidos,$scope.historial.carbohidratos,$scope.historial.proteinas,$scope.historial.azucar,$scope.historial.af).then(
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
}
},

/**
* actualizaHistorialClinico - Funcion que invoca al service para editar un historial clinico
*/
$scope.actualizaHistorialClinico = function(){
  var idPaciente = $cookies.get("idCurrentPaciente");

  var macronutrientes = parseInt($scope.historialDetalle.lipidos) + parseInt($scope.historialDetalle.carbohidratos) + parseInt($scope.historialDetalle.proteinas);
console.log("macronutrientes : " + macronutrientes)
if(macronutrientes > 100){
    toastr.error("La suma de macronutrientes supera el 100%","Error");
  }
  else if(macronutrientes < 100){
    toastr.error("La suma de macronutrientes no alcanza el 100%","Error");    
  }
  else{

  var idHistorialClinico = $scope.historialDetalle.idHistorialClinico;
  pacientesService.editarInfoHistorialClinico(idPaciente,idHistorialClinico,$scope.historialDetalle.peso,$scope.historialDetalle.talla,$scope.historialDetalle.estatura,$scope.historialDetalle.imc,$scope.historialDetalle.lipidos,$scope.historialDetalle.carbohidratos,$scope.historialDetalle.proteinas,$scope.historialDetalle.azucar,$scope.historial.af,$scope.historialDetalle.observaciones).then(

    function successCallback(d) {
      toastr.success(d, 'Ok');
      $state.transitionTo('index.informacionGeneral');
      //$state.transitionTo('login');
      location.reload();
    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
        toastr.error(d.data.mensaje, 'Error');
      }
    });
}

},

/**
* recuperaHistorial  - Recuperar la informacion del historial clinico del paciente seleccionado
*/
$scope.recuperaHistorial = function(idHistorial){
  blockUI.start();
  var idCurrentPaciente = $cookies.get("idCurrentPaciente")
  pacientesService.recuperarDetallHistorialClinico(idCurrentPaciente,idHistorial).then(
    function successCallback(d) {
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
* calculateAge  - Calcular la edad de los pacientes con base en su fecha de nacimiento
*/
$scope.calculateAge = function calculateAge(birthday) { // birthday is a date with yyyy/MM/dd format
  var birthDate = new Date(birthday);
  var ageDifMs = Date.now() - birthDate.getTime();
  var ageDate = new Date(ageDifMs); // miliseconds from epoch
  return Math.abs(ageDate.getUTCFullYear() - 1970);
},

/**
* formatAndCalculateAge - Formatear la fecha para que se pueda calcular la edad con calculateAge
*/
formatAndCalculateAge = function formatAndCalculateAge(birthday) {// birthday is a date with dd/MM/yyyy format
  var formatedDate = birthday.split("/").reverse().join("-");
  return  $scope.calculateAge(formatedDate);
},
/**
* activarBoton  - Desactivar el boton para guardar alguna modificacion en el historial clinico
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
},

/**
* validaInfo - Validar la informacion cuando se actualice un historial clinico
*/
$scope.validaInfo =function(){
  if($scope.esEditable == false){
    if($scope.formInfoGral.$valid){
      $scope.historialDetalle.imc=  ($scope.historialDetalle.peso)/Math.pow($scope.historialDetalle.estatura,2);
      return  true;
    }
    else{
      if($scope.historialDetalle.peso && $scope.historialDetalle.estatura){
        $scope.historialDetalle.imc=  ($scope.historialDetalle.peso)/Math.pow($scope.historialDetalle.estatura,2);
      }else{
        $scope.historialDetalle.imc = "IMC"
      }
      return false;   
    }    
  }
  else{
    return false;
  }

},

$scope.editarInfo=function(){
  $scope.esEditable = false;
},

/**
* limpiarInfo  - Limpiar la informacion del historial clinico, la funcion se invoca cuando el modal del historial C se cierra
*/
$scope.limpiarInfo=function(){
  $scope.historial = {};
},


$scope.clickOptionSelected = function(index) {
  $scope.filtraNumRegistro(index.lim);
}

/**
*
*/
$scope.filtraNumRegistro = function(limite){
  /*Cookie que se recupera del inicio de sesion*/
  var idPaciente =  $cookies.get("idUsuario")
  
  if (limite == undefined) {
    limite  = "5";
  }

  glucosaService.recuperarNRegistrosGlucosaService(idPaciente, limite).then(

    function successCallback(d) {
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

        glucosaService.recuperarRegistro(elements,e,d,$scope.chart,$scope.labels, $scope.data);

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
}




});


