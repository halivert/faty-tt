angular
.module('trabajoTerminal')

.controller('perfilCtrl', function($scope,$cookies,loginService){


	$scope.mostrarInformacionPerfil = function(){
      loginService.recuperarInformacionUsuario($cookies.get("idUsuario")).then(
        function successCallback(d) {
        	$scope.infoPerfil = d;
        	console.log(JSON.stringify(d));
        	$scope.nombre = $cookies.get("nombre");  
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
