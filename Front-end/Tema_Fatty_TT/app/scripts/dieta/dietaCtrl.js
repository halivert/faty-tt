/**
 * dietaCtrl - Controlador ligado a la vista dieta_rigurosa.html
 */
 angular.module('trabajoTerminal')
 .controller('dietaCtrl', function($scope, $log,$state,toastr,alimentoService, $cookies, NgTableParams, $filter, pacientesService, dietaService,FileSaver, Blob) {


  $scope.alimentos = [];
  $scope.dieta = {"Desayuno": [],"C1": [],"Comida": [],"C2": [],"Cena": []};
  $scope.ArrayAlimentos = [{name: "Verdura"},{name: "Fruta"},{name: "Cereales con grasa"},{name: "Cereales sin grasa"},{name: "Leguminosas"}, {name: "AOAMBG"}, {name: "AOABG"}, {name: "AOAMG"},{name: "AOAAG"}];
  var currentTiempo = "";
  $scope.tiempos = [{label: 'Desayuno'},{label: 'Colación 1'},{label: 'Comida'},{label: 'Colación 2'},{label: 'Cena'}];
  $scope.numberLoaded = true;

  $scope.idAlimento = "";
  $scope.descripcion = "";

  $scope.dataProteinas = 0 ;
  $scope.dataCh = 0;
  $scope.dataLipidos = 0;

  $scope.valoresNutrimentales = {};
  $scope.infoGralUsuario = {};

  $scope.caloriasDesayuno = 0;
  $scope.caloriasC1 = 0;
  $scope.caloriasComida = 0;
  $scope.caloriasC2 = 0;
  $scope.caloriasCena = 0;
  $scope.isDisabled = true;

/**
 * slickConfig - Propiedades para el slider, setea en la variable currentTiempo, el tiempo en que se estan agregando los alimentos
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
 * searchAlimento - Funcion que invoca a recuperarListaAlimentos() cada que se selecciona un tipo de alimento
 */
 $scope.searchAlimento = function(index) {
  recuperarListaAlimentos(index.name);
}

/**
 * recuperarListaAlimentos  - Funcion llama al service para recuperar una lista de alimentos
 */
var recuperarListaAlimentos = function(tipoAlimento) {
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
  });
} 

/**
* agregarAlimento - Funcion que agrega un alimento al Array $scope.dieta dependiendo del tiempo seleccionado
*/
$scope.agregarAlimento = function(idAlimento) {
  var currentAlimento = {};
  currentAlimento = $filter('filter')($scope.alimentos, {
    'idAlimento': idAlimento
  }, true)[0];

  var lipidos = currentAlimento.lipidos * 9;
  var proteinas = currentAlimento.proteina * 4;
  var carbohidratos = currentAlimento.carbohidratos * 4;

  var plus = parseFloat(proteinas) + parseFloat(lipidos) + parseFloat(carbohidratos);
  var plusFixed = 0;
  plusFixed = parseFloat(plus.toFixed(2));
  
  switch (currentTiempo) {
    case 'Desayuno':
    $scope.dieta.Desayuno.push(currentAlimento);
    if($scope.caloriasDesayuno == undefined){
      $scope.caloriasDesayuno = 0;
    }
    $scope.caloriasDesayuno += plusFixed;
    if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
    }
    break;
    case 'Colación 1':
    $scope.dieta.C1.push(currentAlimento);
    if($scope.caloriasC1 == undefined){
      $scope.caloriasC1 = 0;
    }
    $scope.caloriasC1 += plusFixed;
    if($scope.caloriasC1 < 0.5){
      $scope.caloriasC1 = 0;
    }
    break;
    case 'Comida':
    $scope.dieta.Comida.push(currentAlimento);
    if($scope.caloriasComida == undefined){
      $scope.caloriasComida = 0;
    }
    $scope.caloriasComida += plusFixed;
    if($scope.caloriasComida < 0.5){
      $scope.caloriasComida = 0;
    }
    break;
    case 'Colación 2':
    $scope.dieta.C2.push(currentAlimento);
    if($scope.caloriasC2 == undefined){
      $scope.caloriasC2 = 0;  
    }
    $scope.caloriasC2 += plusFixed;
    if($scope.caloriasC2 < 0.5){
      $scope.caloriasC2 = 0;
    }
    break;
    case 'Cena':
    $scope.dieta.Cena.push(currentAlimento);
    if($scope.caloriasCena == undefined){
      $scope.caloriasCena = 0;
    }
    $scope.caloriasCena += plusFixed;
    if($scope.caloriasCena < 0.5){
      $scope.caloriasCena = 0;
    }
    break;
    default:
    $scope.dieta.Desayuno.push(currentAlimento);
    if($scope.caloriasDesayuno == undefined){
      $scope.caloriasDesayuno = 0;
    }
    $scope.caloriasDesayuno += plusFixed;
    if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
    }
  }
  $scope.iniciaGraficaBarras(proteinas,carbohidratos,lipidos,"plus");
}

/**
* Version de la funcion eliminarAlimento pero lo elimina de la vista "Ver dieta"
*/
$scope.eliminaAlimentoDieta = function(idAlimento,array,tiempo) {
  var currentAlimento = {};
  var index = 0;
  console.log("array : " + array);

  currentAlimento = $filter('filter')(array, {
  'idAlimento': idAlimento
  }, true)[0];    

  var lipidos = currentAlimento.lipidos * 9;
  var proteinas = currentAlimento.proteina * 4;
  var carbohidratos = currentAlimento.carbohidratos * 4;

  var less = parseFloat(proteinas) + parseFloat(lipidos) + parseFloat(carbohidratos);
  var lessFixed = 0;
  lessFixed = parseFloat(less.toFixed(2));

  switch (tiempo) {
    case 'Desayuno':
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if(index != undefined){
      $scope.dieta.Desayuno.splice(index,1);
      $scope.caloriasDesayuno -= lessFixed;
      if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
      }
    }
    break;
    case 'C1':
    index = recuperaIndexPorIdAlimento($scope.dieta.C1,idAlimento);
    if(index != undefined){
      $scope.dieta.C1.splice(index,1);
      $scope.caloriasC1 -= lessFixed;   
      if($scope.caloriasC1 < 0.5){
      $scope.caloriasC1 = 0;
      }   
    }
    break;
    case 'Comida':
    index = recuperaIndexPorIdAlimento($scope.dieta.Comida,idAlimento);
    if(index != undefined){
      $scope.dieta.Comida.splice(index,1); 
      $scope.caloriasComida -= lessFixed;
      if($scope.caloriasComida < 0.5){
      $scope.caloriasComida = 0;
      }                
    }
    break;
    case 'C2':
    index = recuperaIndexPorIdAlimento($scope.dieta.C2,idAlimento);
    if(index != undefined){
      $scope.dieta.C2.splice(index,1);
      $scope.caloriasC2 -= lessFixed;
      if($scope.caloriasC2 < 0.5){
      $scope.caloriasC2 = 0;
      }
    }
    break;
    case 'Cena':
    index = recuperaIndexPorIdAlimento($scope.dieta.Cena,idAlimento);
    if(index != undefined){
      $scope.dieta.Cena.splice(index,1);
      $scope.caloriasCena -= lessFixed;  
      if($scope.caloriasCena < 0.5){
      $scope.caloriasCena = 0;
      }                
    }
    break;
    default:
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if (index != undefined){
      $scope.dieta.Desayuno.splice(index,1);  
      $scope.caloriasDesayuno -= lessFixed;  
      if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
      }
    }

  }
  //console.log("$scope.caloriasDesayuno  LESS: " + $scope.caloriasDesayuno.toFixed(2));
  //fixNumber();
  $scope.iniciaGraficaBarras(proteinas,carbohidratos,lipidos,"less");
}
/**
* 
*/
$scope.eliminarAlimento = function(idAlimento) {
  var currentAlimento = {};
  var index = 0;

  currentAlimento = $filter('filter')($scope.alimentos, {
  'idAlimento': idAlimento
  }, true)[0];    
  

  var lipidos = currentAlimento.lipidos * 9;
  var proteinas = currentAlimento.proteina * 4;
  var carbohidratos = currentAlimento.carbohidratos * 4;

  var less = parseFloat(proteinas) + parseFloat(lipidos) + parseFloat(carbohidratos);
  var lessFixed = 0;
  lessFixed = parseFloat(less.toFixed(2));

  switch (currentTiempo) {
    case 'Desayuno':
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if(index != undefined){
      $scope.dieta.Desayuno.splice(index,1);
      $scope.caloriasDesayuno -= lessFixed;
      if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
      }
    }
    break;
    case 'Colación 1':
    index = recuperaIndexPorIdAlimento($scope.dieta.C1,idAlimento);
    if(index != undefined){
      $scope.dieta.C1.splice(index,1);
      $scope.caloriasC1 -= lessFixed;   
      if($scope.caloriasC1 < 0.5){
      $scope.caloriasC1 = 0;
      }   
    }
    break;
    case 'Comida':
    index = recuperaIndexPorIdAlimento($scope.dieta.Comida,idAlimento);
    if(index != undefined){
      $scope.dieta.Comida.splice(index,1); 
      $scope.caloriasComida -= lessFixed;
      if($scope.caloriasComida < 0.5){
      $scope.caloriasComida = 0;
      }                
    }
    break;
    case 'Colación 2':
    index = recuperaIndexPorIdAlimento($scope.dieta.C2,idAlimento);
    if(index != undefined){
      $scope.dieta.C2.splice(index,1);
      $scope.caloriasC2 -= lessFixed;
      if($scope.caloriasC2 < 0.5){
      $scope.caloriasC2 = 0;
      }
    }
    break;
    case 'Cena':
    index = recuperaIndexPorIdAlimento($scope.dieta.Cena,idAlimento);
    if(index != undefined){
      $scope.dieta.Cena.splice(index,1);
      $scope.caloriasCena -= lessFixed;  
      if($scope.caloriasCena < 0.5){
      $scope.caloriasCena = 0;
      }                
    }
    break;
    default:
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if (index != undefined){
      $scope.dieta.Desayuno.splice(index,1);  
      $scope.caloriasDesayuno -= lessFixed;  
      if($scope.caloriasDesayuno < 0.5){
      $scope.caloriasDesayuno = 0;
      }
    }

  }
  //console.log("$scope.caloriasDesayuno  LESS: " + $scope.caloriasDesayuno.toFixed(2));
  //fixNumber();
  $scope.iniciaGraficaBarras(proteinas,carbohidratos,lipidos,"less");
}

$scope.asignarDietaArmada = function() {
  var idMedico = "";
  var rangoError = $scope.valoresNutrimentales.get * .1;
  var rangoLess = parseFloat($scope.valoresNutrimentales.get) - rangoError;
  var rangoPlus = parseFloat($scope.valoresNutrimentales.get) + rangoError; 
  console.log("rangoLess : " + rangoLess);
  console.log("rangoPlus : " + rangoPlus);
  if($scope.caloriasTotales >= rangoLess && $scope.caloriasTotales <= rangoPlus ){
    dietaService.crearDieta($cookies.get("idCurrentPaciente"),$cookies.get("idUsuario"),$scope.descripcion,$scope.dieta,$scope.caloriasDesayuno,$scope.caloriasC1,$scope.caloriasComida,$scope.caloriasC2,$scope.caloriasCena).then(
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
    });    
  }
  else{
      toastr.error("No se puede asignar la dieta porque las calorías totales no están dentro del rango permitido para el paciente.","Error");
  }
}//Termina dietaRigurosaSlider

/**
 * iniciaGraficaBarras - Funcion que inicia una grafica de barras
 */
 $scope.iniciaGraficaBarras = function(proteinas,ch,lipidos,flag) {

  $scope.labels = ['Proteinas', 'CH', 'Lipidos'];
  $scope.series = ['Calorías'];

  
  if(flag === "plus"){
    if(proteinas==undefined){
      proteinas = 0;
    }else{
      $scope.dataProteinas += parseFloat(proteinas.toFixed(2)); 
    }
    if(ch==undefined){
      ch=0;
    }else{
      $scope.dataCh += parseFloat(ch.toFixed(2));
    }
    if(lipidos == undefined){
      lipidos=0;
    }else{
      $scope.dataLipidos += parseFloat(lipidos.toFixed(2));
    }
  }else if(flag === "less"){
    if(proteinas==undefined){
      proteinas = 0;
    }else{
      $scope.dataProteinas -= parseFloat(proteinas.toFixed(2)); 
    }
    if(ch==undefined){
      ch=0;
    }else{
      $scope.dataCh -= parseFloat(ch.toFixed(2));
    }
    if(lipidos == undefined){
      lipidos=0;
    }else{
      $scope.dataLipidos -= parseFloat(lipidos.toFixed(2));
    }
  }
  $scope.caloriasTotales = $scope.dataProteinas + $scope.dataCh + $scope.dataLipidos;

  if($scope.dataProteinas > $scope.valoresNutrimentales.kproteinas){
    toastr.error("Ha excedido el límite de proteinas", 'Atención');
    $scope.isDisabled = true;
  }
  if($scope.dataCh > $scope.valoresNutrimentales.kcarbohidratos){
    toastr.error("Ha excedido el límite de carbohidratos", 'Atención');
    $scope.isDisabled = true;
  }
  if($scope.dataLipidos > $scope.valoresNutrimentales.klipidos){
    toastr.error("Ha excedido el límite de lípidos", 'Atención');
    $scope.isDisabled = true;
  }

  if($scope.dataProteinas < .5){
    $scope.dataProteinas = 0;
  }
  if($scope.dataCh < .5){
    $scope.dataCh = 0;
  }
  if($scope.dataLipidos < .5){
    $scope.dataLipidos = 0;
  }

  $scope.data = [[$scope.dataProteinas.toFixed(2), $scope.dataCh.toFixed(2), $scope.dataLipidos.toFixed(2)]];
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
  var idUsuario = $cookies.get("idCurrentPaciente");
  pacientesService.recuperaUltimoHistorial(idUsuario).then(
    function successCallback(d) {
      $scope.pesoCurrentUser = d.peso;
      $scope.estaturaCurrentUser = d.estatura;
      $scope.nombreComppleto = d.nombreComppleto;
      $scope.peso = d.peso;
      $scope.fechaNacimiento = d.fechaNacimiento;
      $scope.carbohidratos = d.carbohidratos;
      $scope.lipidos = d.lipidos;
      $scope.proteinas = d.proteinas;
            //console.log("d : " + JSON.stringify(d));
    }
    );
  
  dietaService.recuperarDieta(idUsuario, $cookies.get("idDieta")).then(
    function successCallback(d) {
      //console.log("d : " + JSON.stringify(d));
      $scope.descripcion = d.descripcion;
      $scope.dieta = angular.fromJson(d.alimentosDisponibles);
      $scope.caloriasDesayuno = d.caloriasDesayuno.toFixed(2);
      $scope.caloriasComida = d.caloriasComida.toFixed(2);
      $scope.caloriasC1 = d.caloriasC1.toFixed(2);
      $scope.caloriasC2 = d.caloriasC2.toFixed(2);
      $scope.caloriasCena = d.caloriasCena.toFixed(2);
      $scope.caloriasTotales = d.caloriasDesayuno + d.caloriasComida + d.caloriasC1 + d.caloriasC2 + d.caloriasCena;

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

/**
* Generar la dieta en formato PDF
*/    
$scope.generarDietaEnPDF = function() {
  var nombre = $cookies.get("nombre");
  /**var edad = $cookies.get("edadCurrentUsuario");
  dietaService.crearPDF(nombre,$scope.caloriasTotales,$scope.pesoCurrentUser,$scope.estaturaCurrentUser,edad).then(
    function successCallback(d) {
      console.log(d);
      var file = new Blob([d],{type : 'application/pdf'});
      FileSaver.saveAs(file, 'dieta.pdf');

    },
    function errorCallback(d) {
      if (d.data == null)
        toastr.warning("Servicio no disponible", 'Advertencia');
      else {
        toastr.error(d.data.mensaje, 'Error');
      }
    });*/
    var elem = $('#pdfContainer');
    html2canvas(elem, {
      onrendered: function (canvas) {
        elem.show();
        var data = canvas.toDataURL();
        var docDefinition = {
          content: [{
                        image: data,
                        width: 500,
                    }]
        };
        pdfMake.createPdf(docDefinition).download(nombre + "Dieta"+nombre+new Date()+".pdf");
        elem.hide();
      }
      });
  }
/**
* mostrarDietasPaciente - Funcion que llama al service que recupera una lista de deitas asociadas a un paciente
*/
$scope.recuperarDietasPaciente = function() {
  if($cookies.get("historialDetalle") != undefined){
      $scope.infoGralUsuario = JSON.parse($cookies.get("historialDetalle"));    
  }else{
    $scope.infoGralUsuario.nombreComppleto = $cookies.get("nombrePaciente");
  }

  dietaService.recuperarDietasPacienteService($cookies.get("idCurrentPaciente")).then(
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

/**
* recuperarInformacionNutrimental - Recuperar la informacion nutrimental asociada a una dieta
*/
$scope.recuperarInformacionNutrimental = function(){
  if($cookies.get("historialDetalle") != undefined && $cookies.get("valoresNutrimentales") != undefined){
    $scope.infoGralUsuario = JSON.parse($cookies.get("historialDetalle"));
    $scope.valoresNutrimentales = JSON.parse($cookies.get("valoresNutrimentales"));
  }
}


/*$scope.seleccionarAlimento = function(idAlimento) {   
  $scope.idAlimento = idAlimento;
}*/

$scope.modificarCantidad = function(){
  console.log("$scope.idAlimento");

}
/**
* Recupera el indice en el que se encuentra un alimento dentro de un array de dietas (desayuno, c1, comida, c2, cena)
*/
var recuperaIndexPorIdAlimento = function(dieta, idAlimento){
  for(i in dieta) {
    if(dieta[i].idAlimento==idAlimento) 
        return parseInt(i); 
    }
}  

var fixNumber = function(){
  $scope.caloriasDesayuno.toFixed(2);
  $scope.caloriasC1.toFixed(2);
  $scope.caloriasComida.toFixed(2);
  $scope.caloriasC2.toFixed(2);
  $scope.caloriasCena.toFixed(2);
}


/**
* doesExist - Funcion que verifica si un alimento ya se agrego, en caso que exista habilita el boton para poder retirarlo de la dieta
*/
$scope.doesExist = function(idAlimento) {
  switch (currentTiempo) {
    case 'Desayuno':
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }
    break;
    case 'Colación 1':
    index = recuperaIndexPorIdAlimento($scope.dieta.C1,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }
    break;
    case 'Comida':
    index = recuperaIndexPorIdAlimento($scope.dieta.Comida,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }
    break;
    case 'Colación 2':
    index = recuperaIndexPorIdAlimento($scope.dieta.C2,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }
    break;
    case 'Cena':
    index = recuperaIndexPorIdAlimento($scope.dieta.Cena,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }
    break;
    default:
    index = recuperaIndexPorIdAlimento($scope.dieta.Desayuno,idAlimento);
    if(index != undefined){
      return false;
    }else{
      return true;
    }

  }
}

});
