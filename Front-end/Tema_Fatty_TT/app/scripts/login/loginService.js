//Login Service
angular.module('trabajoTerminal')

.service("loginService", function($log, $http, $q) {

  return {

      /**
       * iniciaSesionService - Funcion que envia al API el email y password para iniciar sesion 
       */
       iniciaSesionService: function(email, password) {

        var data = {
          email: email,
          keyword: password
        };
        var config = {
          headers: {
            'Content-Type': 'application/json'
          }
        }
        var url = 'http://35.202.245.109/tt-escom-diabetes/session/login';
        //var url = 'http://localhost:8080/tt-escom-diabetes/session/login';

        return $http.post(url, data, config)
        .then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data;
          } else {
            return $q.reject(response);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });
      },

      /**
       * guardarUsuario  - Funcion que envia a la API los datos necesarios para registrar un nuevo usuario
       */
       guardarUsuario: function(nombre, apellidoPaterno, apellidoMaterno, email, keyword, fechaNacimiento, sexo, idRol, cedulaProfesional, codigoMedico) {

        var data = {
          nombre: nombre,
          apellidoPaterno: apellidoPaterno,
          apellidoMaterno: apellidoMaterno,
          email: email,
          keyword: keyword,
          fechaNacimiento: fechaNacimiento,
          sexo: sexo,
          idRol: idRol,
          cedulaProfesional: cedulaProfesional,
          codigoMedico: codigoMedico
        };

        var config = {
          headers: {
            'Content-Type': 'application/json'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/session/usuarios';
        //var url = 'http://localhost:8080/tt-escom-diabetes/session/usuarios';

        return $http.post(url, data, config).then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data.mensaje;
          } else {
            return $q.reject(response);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data.mensaje));
          return $q.reject(response);
        });
      },

      /**
       * recuperarInformacionUsuario - Funcion que recupera la informacion de un usuario con base en su Id de usuario
       */
       recuperarInformacionUsuario: function(idUsuario) {

        $log.debug("idUsuario : " + idUsuario);
        var data = $.param({
          idUsuario: idUsuario
        });
        var config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/usuarios/' + idUsuario;
        //var url = 'http://localhost:8080/tt-escom-diabetes/ceres/usuarios/'+idUsuario;

        return $http.get(url, data, config)
        .then(function successCallback(response) {
          return response.data;
        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });
      },

      /**
       * enviarCorreoRestorePassword - Funcion que envia un correo al usuario para reestanlecer su password
       */
       enviarCorreoRestorePassword: function(email) {

        var data = {
          email: email
        };       
       var config = {
          headers: {
            'Content-Type': 'application/json'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/session/restorePassword';
        //var url = 'http://localhost:8080/tt-escom-diabetes/session/restorePassword';

        return $http.post(url, data, config)
        .then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data.respuesta;
          } else {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response.data);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });

      },

      reestablecerPassword: function(email) {

        var data = {
          email: email
        };       
       var config = {
          headers: {
            'Content-Type': 'application/json'
          }
        }

        var url = 'http://35.202.245.109/tt-escom-diabetes/session/restorePassword';
        //var url = 'http://localhost:8080/tt-escom-diabetes/session/restorePassword';

        return $http.post(url, data, config)
        .then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data.respuesta;
          } else {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response.data);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });

      },

      reestablecePassword: function(idUsuario,password) {

        var data = {
          idUsuario: idUsuario,
          keyword: password
        };       
       var config = {
          headers: {
            'Content-Type': 'application/json'
          }
        }

        //var url = 'http://35.202.245.109/tt-escom-diabetes/ceres/usuarios/'+idUsuario+'/password';
        var url = 'http://localhost:8080/tt-escom-diabetes/ceres/usuarios/'+idUsuario+'/password';

        return $http.put(url, data, config)
        .then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data.mensaje;
          } else {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response.data);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });

      },

      validateToken: function(token) {
     
       var config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
          }
        }

        //var url = 'http://35.202.245.109/tt-escom-diabetes/session/verificaToken/'+token;
        var url = 'http://localhost:8080/tt-escom-diabetes/session/verificaToken/'+token;

        return $http.get(url, config)
        .then(function successCallback(response) {

          if (response.data.respuesta === "OK") {
            return response.data;
          } else {
            console.log("response ERROR : " + JSON.stringify(response.data));
            return $q.reject(response.data);
          }

        }, function errorCallback(response) {
          console.log("response ERROR : " + JSON.stringify(response.data));
          return $q.reject(response);
        });

      }


    }
  })