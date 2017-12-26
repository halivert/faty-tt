/**
* loginCtrl - Controlador ligado a la vista login.html, se usa para iniciar sesión o registrar un nuevo usuario
*             se comunica con el modulo 'Login' del BackEnd
*/
angular.module('trabajoTerminal')
.controller('loginCtrl', function($scope,$location,$log,$state,loginService,toastr,$cookies,blockUI){

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

/**
* limpiaInfo  - Función que se ejecuta cada que se carga la pagina del login, su función principal es eliminar la información de la sesión del usuario
*/
  $scope.limpiaInfo = function(){
    $cookies.put("auth","false");
    $cookies.remove("rol");
    //TODO revisar si se elimina correctamente la informacion de las cookies
    $cookies.remove("nombre","");
    $cookies.remove("idUsuario","");
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
      toastr.success(d.mensaje,'Ok');
              loginService.recuperarInformacionUsuario(d.idUsuario).then( 
                function successCallback(informacionUsuario){
                  var nombreCompleto = informacionUsuario.nombre + " " + informacionUsuario.apellidoPaterno + " " + informacionUsuario.apellidoMaterno;
                  $log.debug("nombreCompleto : " + nombreCompleto);
                  $log.debug("informacionUsuario.idRol : " + informacionUsuario.idRol);
                  $cookies.put("rol",informacionUsuario.idRol);
                  $cookies.put("nombre",nombreCompleto);  
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
    //console.log("JSON.stringify($scope.datosU)" + JSON.stringify($scope.datosU));
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
  });
