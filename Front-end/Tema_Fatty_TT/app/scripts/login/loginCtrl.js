/**
* loginCtrl - Controlador ligado a la vista login.html, se usa para iniciar sesión o registrar un nuevo usuario
*             se comunica con el modulo 'Login' del BackEnd
*/
angular.module('trabajoTerminal')
.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr,$cookies,blockUI,$stateParams){

	$scope.titulo = "Bienvenido a CERES";
	$scope.usuarioObject={
    email:'',
    password:''
  };
  $scope.datosU={
    nombre:'',
    apellidoPaterno:'',
    apellidoMaterno:'',
    idSexo:'',
    email:'',
    keyword:'',
    rol:'',
    cedula:'',
    fechaNac:'',
    codigoM:''
  };

  $scope.emailRestorePassword = '';

/**
* limpiaInfo  - Función que se ejecuta cada que se carga la pagina del login, su función principal es eliminar la información de la sesión del usuario
*/
$scope.limpiaInfo = function(){
  $cookies.put("auth","false");
  $cookies.remove("rol");
    //TODO revisar si se elimina correctamente la informacion de las cookies
    $cookies.remove("nombre");
    $cookies.remove("idUsuario");
    $cookies.remove("idCurrentPaciente");
    $cookies.remove("historialDetalle");
    $cookies.remove("valoresNutrimentales");
    $cookies.remove("sexo");
    $cookies.remove("idDieta");
    $cookies.remove("edadCurrentUsuario"); 
  }

/**
* iniciarSesion - Función que se comunica con el service iniciaSesionService() enviando el email y el password que el usuario proporcione. 
*/
$scope.iniciarSesion = function(){
  loginService.iniciaSesionService($scope.usuarioObject.email,$scope.usuarioObject.password).then(
   function successCallback(d) {
    blockUI.start();

    $cookies.put("auth","true");
    $cookies.put("idUsuario",d.idUsuario);
    $cookies.put("idCurrentPaciente",d.idUsuario);
    toastr.success(d.mensaje,'Ok');
    loginService.recuperarInformacionUsuario(d.idUsuario).then( 
      function successCallback(informacionUsuario){
        var nombreCompleto = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
        
        $cookies.put("rol",informacionUsuario.idRol);
        $cookies.put("nombre",nombreCompleto);  
        $cookies.put("sexo",informacionUsuario.sexo);
        $cookies.put("edadCurrentUsuario",informacionUsuario.edad);                  
        $state.transitionTo('index.main');

      });
    blockUI.stop();

  },
  function errorCallback(d) {
   if(d.data == null)
    toastr.warning("Servicio no disponible", 'Advertencia');
  else{
    $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
    toastr.error(d.data.mensaje, 'Error');
  }
}); 
};

/**
* validaForm  - Funcion que verifica si todos los campos del formulario para registrar un nuevo usuario han sido llenados,
*               Si todos los campos han sido llenados se manda a llamar a la funcion validaPasword.
*/
$scope.validaForm =function(){
  if($scope.formRegistro.$valid)
    return  $scope.validaPasword();
  else 
    return false;
}

$scope.validaResetPasswordForm =function(){
  if($scope.formResetPassword.$valid)
    return  $scope.validaPasword();
  else 
    return false;
}

/**
* validaPasword  -  Funcion que verifica que el contenido de los campos contraseña y confirmar contraseña sean iguales
*/
$scope.validaPasword = function(){
  return  ($scope.datosU.keyword === $scope.datosU.confirmpass ? true : false); 
}

/**
* registrarUsuario  -  Funcion que envia los datos para registrar un nuevo usuario al servicio guardarUsuario() 
*/
$scope.registrarUsuario = function(){
  loginService.guardarUsuario($scope.datosU.nombre,$scope.datosU.apellidoPaterno,$scope.datosU.apellidoMaterno,$scope.datosU.email,$scope.datosU.keyword,$scope.datosU.fechaNac,$scope.datosU.idSexo,$scope.datosU.rol,$scope.datosU.cedula,$scope.datosU.codigoM).then(
    function successCallback(d) {
      $log.debug("d" + JSON.stringify(d));
      toastr.success(d, 'Ok');
      angular.element('#modal-form').modal('hide');
      $scope.datosU={};

    },
    function errorCallback(d) {
      if(d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else{
        $log.debug("JSON.stringify(d.data.mensaje)" + JSON.stringify(d.data.mensaje));
        toastr.error(d.data.mensaje, 'Error');
      }
    });
};

$scope.enviarCorreoReestablecePassword = function(){
  loginService.enviarCorreoRestorePassword($scope.usuarioObject.email).then(
    function successCallback(d) {
      toastr.success(d, 'Ok');
      $state.transitionTo('login');
    },
    function errorCallback(d) {
      if(d.respuesta == null){
        toastr.warning("Servicio no disponible", 'Advertencia');
        $state.transitionTo('login');
      }
      else{

        $state.transitionTo('login');
        toastr.error(d.mensaje, 'Error');
      }
    });
};

$scope.cambiarPassword = function(){
  loginService.reestablecePassword($scope.idUsuario,$scope.datosU.keyword).then(
    function successCallback(d) {
      toastr.success(d, 'Ok');
      $state.transitionTo('login');
    },
    function errorCallback(d) {
      if(d.respuesta == null){
        toastr.warning("Servicio no disponible", 'Advertencia');
        $state.transitionTo('login');
      }
      else{

        //$state.transitionTo('login');
        toastr.error(d.mensaje, 'Error');
      }
    });
};

$scope.validateToken = function(){
  $scope.token = $stateParams.token;
  console.log("token : " + $scope.token);
  loginService.validateToken($scope.token).then(
    function successCallback(d) {
      console.log(JSON.stringify(d));
      $scope.emailUser = d.mensaje;
      $scope.idUsuario = d.idUsuario;
    },
    function errorCallback(d) {
        console.log("d : " + JSON.stringify(d))
        //toastr.warning(d.mensaje, 'Advertencia');
        $cookies.put("error",d.mensaje);
        $state.transitionTo('error');
     
    });
};

$scope.mostrarError = function(){
  $scope.descripcionError = $cookies.get("error");
     
};

});
