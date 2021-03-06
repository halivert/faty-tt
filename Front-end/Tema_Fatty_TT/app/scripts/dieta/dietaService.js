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
        //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/' + idPaciente + '/dietas/' + idDieta;        
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
      * recuperarDietasPacienteService - Funcion que invoca al controller de la API para recuperar las idetas asociadas a un paciente
      */
      recuperarDietasPacienteService: function(idPaciente) {
        var config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/' + idPaciente + '/dietas';
        //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/pacientes/' + idPaciente + '/dietas';        
        return $http.get(url, config)
          .then(function successCallback(response) {
            
            if (response.data.respuesta === "ERROR") {
              return $q.reject(response);
            }else{
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
      crearDieta: function(idPaciente,idMedico,descripcion,dieta,caloriasDesayuno,caloriasC1,caloriasComida,caloriasC2,caloriasCena) {
        var jsonDieta = JSON.stringify(dieta);
        var data = {
          descripcion: descripcion,
          alimentosDisponibles: jsonDieta,
          caloriasDesayuno : caloriasDesayuno,
          caloriasC1 : caloriasC1,
          caloriasComida : caloriasComida,
          caloriasC2 : caloriasC2,
          caloriasCena : caloriasCena
        };
        var config = {
          headers : {'Content-Type': 'application/json'}
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/'+idPaciente+'/dietas';
        //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/medico/'+idMedico+'/pacientes/'+idPaciente+'/dietas';        
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
      },

      crearPDF: function(nombre,caloriasTotales,peso,estatura,edad) {
        var data = {
          nombrePaciente: nombre,
          edadPaciente: edad,
          estaturaPaciente: estatura,
          pesoPaciente: peso,
          gastoET: caloriasTotales        
        };
        var config = {
          headers : {'Content-Type': 'application/json'},
          responseType : 'arraybuffer'  
        }

        //var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/usuarios/dietas/pdf';
        var url = 'http://localhost:8080/tt-escom-diabetes/ceres/usuarios/dietas/pdf'
        return $http.post(url,data,config)
          .then(function successCallback(response) {
            console.log("response SUCCESS");            
            return response.data;

          }, function errorCallback(response) {
            console.log("response ERROR");
            
          });
      },

      obtenerValoresNutrimentales: function(idPaciente,edad,peso,estatura,actividad,sexo,porcentajeLipidos,porcentajeCarbohidratos,porcentajeProteinas) {
        
        var intSexo = 0;
        if(sexo === "Femenino"){
          intSexo = 0;
        }
        else if (sexo == "Masculino"){
          intSexo = 1;
        }
        var data = {
          edad: edad,
          peso: peso,
          estatura: estatura,
          actividad: actividad,
          sexo: intSexo,
          porcentajeLipidos: porcentajeLipidos,
          porcentajeCarbohidratos: porcentajeCarbohidratos,
          porcentajeProteinas: porcentajeProteinas
        };
        var config = {
          headers : {'Content-Type': 'application/json'}
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/'+idPaciente+'/valoresnutrimentales';
        return $http.post(url,data,config)
          .then(function successCallback(response) {
            if(response.data.respuesta === "ERROR"){
              console.log("response ERROR : " + JSON.stringify(response.data));
              return $q.reject(response); 
            }
            else{
              return response.data;
            }
          }, function errorCallback(response) {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response);
          });
      }

    }

  })