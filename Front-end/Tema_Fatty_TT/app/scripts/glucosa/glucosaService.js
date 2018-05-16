//Login Service
angular.module('trabajoTerminal')

.service("glucosaService", function($log, $http, $q,$filter,SweetAlert){

 return {
/**
* guardarRegistroGlucosa  - Funcion que envia a la API los datos necesarios para registrar los niveles de glucosa
*/
guardarRegistroGlucosa: function(idPaciente,azucar,fechaRegistro) {

  console.log("fechaRegistro : " + fechaRegistro);

    //fechaRegistro = $filter('date')(new Date(fechaRegistro), 'yyyy-MM-dd HH:mm:ss');
    //var date = new Date(fechaRegistro);

    console.log("date : " + fechaRegistro);

    //var fechaRegistro = $filter('date')(new Date(fechaRegistro), 'yyyy-MM-dd HH:mm:ss');

    var data = {
      azucar: azucar,
      fechaRegistro: fechaRegistro
    };

    var config = {
      headers : {'Content-Type': 'application/json'}
    }

    var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/registroglucosa';
    //var url = 'http://localhost:8080/tt-escom-diabetes/session/usuarios';

    console.log("data : " + JSON.stringify(data));
    return $http.post(url,data,config).then(function successCallback(response) {

      if(response.data.respuesta === "OK"){
        console.log("response OK : " + JSON.stringify(response.data.mensaje));
        return response.data.mensaje;     
      }
      else{
        console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
        return $q.reject(response);
      }

    },function errorCallback(response) {
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);
    });
  },

/**
* recuperarRegistrosGlucosaService - Funcion que recupera los registros de glucosa de un paciente 
*/
recuperarRegistrosGlucosaService: function(idUsuario) {    

  var data = $.param({idUsuario: idUsuario});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa';

  //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa';  

  return $http.get(url,data,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }

  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
},

/**
* recuperarNRegistrosGlucosaService - Funcion que recupera N registros de glucosa de un paciente 
*/
recuperarNRegistrosGlucosaService: function(idUsuario, limiteRegistro) {    

  var data = $.param({idUsuario: idUsuario, limiteRegistro: limiteRegistro});
  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/limiteRegistro/'+limiteRegistro;  
  
  //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/limiteRegistro/'+limiteRegistro;

  return $http.get(url,data,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }
    
  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
},

recuperarRegistrosGlucosaPorFiltros: function(idUsuario, fechaInicio,fechaFin) {    

  var config = {
    headers : {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}
  }

  var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/fechaInicio/'+fechaInicio+'/fechaFin/'+fechaFin;  

  //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/'+idUsuario+'/registroglucosa/fechaInicio/'+fechaInicio+'/fechaFin/'+fechaFin;  

  return $http.get(url,config)
  .then(function successCallback(response) {
    if(response.data.respuesta === "ERROR"){
      console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
      return $q.reject(response);   
    }
    else{
      return response.data;   
    }
    
  },function errorCallback(response) {
    console.log("response ERROR : " + JSON.stringify(response.data));
    return $q.reject(response);
  });
},

recuperarRegistro : function(elements,e,d,instance, labels,data){


  var pos = Chart.helpers.getRelativePosition(e, instance);
  var intersect = elements.find(function(element) {
    return element.inRange(pos.x, pos.y);
  });

  if(intersect){
    for(i in d) {
      if(d[i].fechaRegistro==labels[intersect._index] && d[i].azucar==data[intersect._datasetIndex][intersect._index]){ 
        console.log(JSON.stringify(d[i]));

        var idPaciente = d[i].idPaciente;
        var idRegistroGlucosa = d[i].idRegistroGlucosa;

        SweetAlert
        .input('Editar registro', 'Fecha : ' + d[i].fechaRegistro + '\x0A Nivel de glucosa : ' + d[i].azucar,{showCancelButton: true,
          cancelButtonText : "Cancelar",
          confirmButtonText: "Actualizar registro"})        
        .then(function (response) {
          
          if(response){
            var data = {"azucar": response}
            return fetch(
              "http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/registroglucosa/"+idRegistroGlucosa,{
                method : "PUT",
                headers : {'Content-Type': 'application/json'},
                body : JSON.stringify(data)
            });
          }
          else{
            console.log("CERAR");
            SweetAlert.close()
          }
           
        })
        .then(function (fetchResponse) {
          return fetchResponse.json();
        })
        .then(function (json) {
          
          if (json.respuesta == "ERROR") {
            return SweetAlert.error("Error", json.mensaje)
          }
          SweetAlert.show({
            title : json.respuesta,
            text : json.mensaje,
            type: 'success',
          })
          .then(function (response) {
              location.reload();
          });
        });

      }
    }
  }
}


  }
})