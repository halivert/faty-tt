/**
 * dietaCtrl - Controlador ligado a la vista dieta_rigurosa.html
 */
angular.module('trabajoTerminal')
  .controller('dietaCtrl', function($scope, $log,$state,toastr,alimentoService, $cookies, NgTableParams, $filter, pacientesService, dietaService) {


    $scope.alimentos = [];
    $scope.dieta = {"Desayuno": [],"C1": [],"Comida": [],"C2": [],"Cena": []};
    $scope.Array = [{name: "Verdura"},{name: "Fruta"},{name: "Cereales con grasa"},{name: "Cereales sin grasa"},{name: "Leguminosas"}, {name: "AOAMBG"}, {name: "AOABG"}, {name: "AOAMG"},{name: "AOAAG"}];
    var currentTiempo = "";
    $scope.tiempos = [{label: 'Desayuno'},{label: 'Colación 1'},{label: 'Comida'},{label: 'Colación 2'},{label: 'Cena'}];
    $scope.numberLoaded = true;
   
    $scope.idAlimento = "";
    $scope.descripcion = "";


    /**
     * slickConfig - Propiedades para el slider
     */
    $scope.slickConfig = {
      enabled: true,
      autoplay: false,
      draggable: false,
      method: {},
      event: {
        beforeChange: function(event, slick, currentSlide, nextSlide) {
        },
        afterChange: function(event, slick, currentSlide, nextSlide) {
          currentTiempo = $scope.tiempos[currentSlide].label;
        }
      }
    };


    /**
     * clickOptionFun - Funcion que invoca a recuperarListaAlimentos() cada que se selecciona un tipo de alimento
     */
    $scope.clickOptionFun = function(index) {
      $scope.recuperarListaAlimentos(index.name);

    }

    /**
     * recuperarListaAlimentos  - Funcion llama al service para recuperar una lista de alimentos
     */
    $scope.recuperarListaAlimentos = function(tipoAlimento) {

      if (tipoAlimento == undefined) {
        tipoAlimento = "Verdura";
      }
      alimentoService.recuperarAlimentos(tipoAlimento).then(
        function successCallback(d) {
          $scope.alimentos = d;
          $scope.tableParams = new NgTableParams({}, {
            dataset: d
          });
        },
        function errorCallback(d) {
          if (d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else {
            toastr.error(d.data.mensaje, 'Error');
          }
        }
      );

    } //Termina dietaRigurosaSlider

    /**
    * agregarAlimento - Funcion que agrega un alimento al Array $scope.dieta dependiendo del tiempo seleccionado
    */
    $scope.agregarAlimento = function(idAlimento) {
      var currentAlimento = {};
      currentAlimento = $filter('filter')($scope.alimentos, {
        'idAlimento': idAlimento
      }, true)[0];

      switch (currentTiempo) {
        case 'Desayuno':
          $scope.dieta.Desayuno.push(currentAlimento);
          break;
        case 'Colación 1':
          $scope.dieta.C1.push(currentAlimento);
          break;
        case 'Comida':
          $scope.dieta.Comida.push(currentAlimento);
          break;
        case 'Colación 2':
          $scope.dieta.C2.push(currentAlimento);
          break;
        case 'Cena':
          $scope.dieta.Cena.push(currentAlimento);
          break;
        default:
          $scope.dieta.Desayuno.push(currentAlimento);
      }
      //console.log("DIETA : " + JSON.stringify($scope.dieta));
    }

    $scope.eliminarAlimento = function(idAlimento) {
      var currentAlimento = {};
      currentAlimento = $filter('filter')($scope.alimentos, {
        'idAlimento': idAlimento
      }, true)[0];

      var index = 0;
      switch (currentTiempo) {
        case 'Desayuno':
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
          if(index != undefined){
            $scope.dieta.Desayuno.splice(index,1);
          }
          break;
        case 'Colación 1':
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.C1,idAlimento);
          if(index != undefined){
          $scope.dieta.C1.splice(index,1);
          }
          break;
        case 'Comida':
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.Comida,idAlimento);
          if(index != undefined){
          $scope.dieta.Comida.splice(index,1);            
        }
          break;
        case 'Colación 2':
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.C2,idAlimento);
          if(index != undefined){
          $scope.dieta.C2.splice(index,1);            
        }
          break;
        case 'Cena':
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.Cena,idAlimento);
          if(index != undefined){
          $scope.dieta.Cena.splice(index,1);            
        }
          break;
        default:
          index = $scope.recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
          if (index != undefined){
          $scope.dieta.Desayuno.splice(index,1);            
          }

      }
    }

    $scope.asignarDietaArmada = function() {

      var idMedico = "";
      dietaService.crearDieta($cookies.get("idCurrentPaciente"),$cookies.get("idUsuario"),$scope.descripcion,$scope.dieta).then(
        function successCallback(d) {
            toastr.success(d.mensaje,d.respuesta);
            $state.transitionTo('index.informacionGeneral');
        },
        function errorCallback(d) {
          if (d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else {
            toastr.error(d.data.mensaje, 'Error');
          }
        }
      );

    }//Termina dietaRigurosaSlider

    /**
     * iniciaGraficaBarras - Funcion que inicia una grafica de barras
     */
    $scope.iniciaGraficaBarras = function() {
      $log.debug("iniciaGraficaBarras");

      $scope.labels = ['Proteinas', 'CH', 'Lipidos', 'Azucar'];
      $scope.series = ['Descripción Datos'];

      $scope.data = [
        [65, 59, 80, 70]
      ];

      $scope.colours = ['#FF4C4C'];

    }

    $scope.seleccionarDieta = function(idDieta) {
      $cookies.put("idDieta",idDieta);
      $state.transitionTo('index.dieta');
    }

    /**
    * mostrarDieta  - Funcion que llama al service que recupera una dieta por medio del idDieta
    */
    $scope.mostrarDieta = function() {
      dietaService.recuperarDieta($cookies.get("idUsuario"), $cookies.get("idDieta")).then(
        function successCallback(d) {
          $scope.dieta = angular.fromJson(d.alimentosDisponibles);
        },
        function errorCallback(d) {
          if (d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else {
            toastr.error(d.data.mensaje, 'Error');
          }
        }
      );
    }

   /* $scope.mostrarValoresNutrimentales = function() {
      dietaService.obtenerValoresNutrimentales($cookies.get("idUsuario"), $cookies.get("idDieta")).then(
        function successCallback(d) {
          $scope.dieta = angular.fromJson(d.alimentosDisponibles);
        },
        function errorCallback(d) {
          if (d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else {
            toastr.error(d.data.mensaje, 'Error');
          }
        }
      );
    }*/

    /**
    * mostrarDietasPaciente - Funcion que llama al service que recupera una lista de deitas asociadas a un paciente
    */
    $scope.recuperarDietasPaciente = function() {
      
      dietaService.recuperarDietasPacienteService($cookies.get("idUsuario")).then(
        function successCallback(d) {
          if(d.length == 0){
            toastr.warning("No se tienen dietas asignadas", 'Advertencia');
          }else{
            $scope.dietas = d;            
          }
   
        },
        function errorCallback(d) {
          if (d.data == null)
            toastr.warning("Servicio no disponible", 'Advertencia');
          else {
            toastr.error(d.data.mensaje, 'Error');
          }
        }
      );
    }

    $scope.seleccionarAlimento = function(idAlimento) {   
      $scope.idAlimento = idAlimento;
    }

    $scope.modificarCantidad = function(){
      console.log("$scope.idAlimento");

    }

    $scope.recuperaIndexPorIdAlimento = function(dieta, idAlimento){
      for(i in dieta) {
        if(dieta[i].idAlimento==idAlimento) 
        return parseInt(i); // print index
      }
    }

  });