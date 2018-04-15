//Login Service
angular.module('trabajoTerminal')

  .service("dietaService", function($log, $http, $q, $filter) {

    return {
      /**
       * recuperarDieta - Funcion que invoca al controller de la API para recuperar una dieta por idDieta
       */
      recuperarDieta: function(idPaciente,idDieta) {
        var config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/' + idPaciente + '/dietas/' + idDieta;
        return $http.get(url, config)
          .then(function successCallback(response) {
            if (response.data.respuesta === "ERROR") {
              console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
              return $q.reject(response);
            } else {
              return response.data;
            }

          }, function errorCallback(response) {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response);
          });
      },

      /**
      * recuperarDietasPaciente - Funcion que invoca al controller de la API para recuperar las idetas asociadas a un paciente
      */
      recuperarDietasPaciente: function(idPaciente) {
        var config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/' + idPaciente + '/dietas';
        return $http.get(url, config)
          .then(function successCallback(response) {
            
            if (response.data.respuesta === "ERROR") {
              console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
              return $q.reject(response);
            } else {
              console.log("response  : " + JSON.stringify(response.data));
              return response.data;
            }

          }, function errorCallback(response) {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response);
          });
      }, 

      /**
       * crearDieta - Funcion que invoca al controller de la API para crear una nueva dieta  
       */
      crearDieta: function(idPaciente,idMedico,descripcion,dieta) {
        //console.log("descripcion : " + descripcion);
        //console.log("dieta : " + JSON.stringify(dieta));
        var jsonDieta = JSON.stringify(dieta);
        var data = {
          descripcion: descripcion,
          alimentosDisponibles: jsonDieta
        };
        var config = {
          headers : {'Content-Type': 'application/json'}
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/'+idPaciente+'/dietas';
        return $http.post(url,data,config)
          .then(function successCallback(response) {
            if(response.data.respuesta === "OK"){
              return response.data;  
            }
            else{
              console.log("response ERROR : " + JSON.stringify(response.data));
              return $q.reject(response);
            }
          }, function errorCallback(response) {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response);
          });
      }

    }

  })