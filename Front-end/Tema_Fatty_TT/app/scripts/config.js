/**
 * Se usa AngularUI Router para administrar las rutas y las vistas
 * cada vista es definida con un 'state'
 *
 */
 function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");

    $stateProvider

    .state('login', {
        url: "/login",
        templateUrl: "views/login/login.html",
        authenticate: false,
        class : "gray-bg",
        data: { pageTitle: 'Inicio sesion',
        bodyClass: 'gray-bg' }
    })
    .state('restorePassword', {
        url: "/session/reestablecePassword/:token",
        templateUrl: "views/login/reestablecerpassword_2.html",
        authenticate: false,
        class : "gray-bg",
        data: { pageTitle: 'Reestablecer contraseña',bodyClass: 'gray-bg' }
    })
    .state('error', {
        url: "/session/error",
        templateUrl: "views/common/error.html",
        authenticate: false,
        class : "gray-bg",
        data: { pageTitle: 'Ocurrió un error',bodyClass: 'gray-bg' }
    })
    .state('enviarRestorPassword', {
        url: "/session/identidad",
        templateUrl: "views/login/reestablecerpassword_1.html",
        authenticate: false,
        class : "gray-bg",
        data: { pageTitle: '¿Olvidaste tu contraseña?',bodyClass: 'gray-bg' }
    })  
    .state('index', {
        abstract: true,
        url: "/index",
        templateUrl: "views/common/content.html",
        authenticate: true
    })
    .state('index.main', {
        url: "/main",
        templateUrl: "views/main.html",
        authenticate: true,
        data: { pageTitle: 'Inicial' }
    })
    .state('index.imc', {
        url: "/imc",
        templateUrl: "views/imc/imc.html",
        authenticate: true,
        data: { pageTitle: 'Calcular IMC' }
    })
    .state('index.glucosa', {
        url: "/glucosa",
        templateUrl: "views/glucosa/glucosa.html",
        authenticate: true,
        data: { pageTitle: 'Registrar glucosa' }
    })
    .state('index.pacientes', {
        url: "/pacientes",
        templateUrl: "views/pacientes/pacientes.html",
        authenticate: true,
        data: { pageTitle: 'Pacientes' }
    })
    .state('index.historialClinico', {
        url: "/historialClinico",
        templateUrl: "views/pacientes/historial_clinico.html",
        authenticate: true,
        data: { pageTitle: 'Historial Clinico' }
    })
    .state('index.informacionGeneral', {
        url: "/informacionGeneral",
        templateUrl: "views/pacientes/informacion_general.html",
        authenticate: true,
        data: { pageTitle: 'Información Paciente' }
    })
    .state('index.dieta', {
        url: "/dieta",
        templateUrl: "views/dieta/dieta.html",
        authenticate: true,
        data: { pageTitle: 'Dieta' }
    })
    .state('index.dietaRigurosa', {
        url: "/dietaRigurosa",
        templateUrl: "views/dieta/dieta_rigurosa.html",
        authenticate: true,
        data: { pageTitle: 'Generar Dieta' }
    })
    .state('index.codigoMedico', {
        url: "/codigoMedico",
        templateUrl: "views/medico/medico_token.html",
        authenticate: true,
        data: { pageTitle: 'Generar código' }
    })
    .state('index.dietasPaciente', {
        url: "/dietasPaciente",
        templateUrl: "views/dieta/dietas_paciente.html",
        authenticate: true,
        data: { pageTitle: 'Mis dietas' }
    })
    .state('index.perfil', {
        url: "/perfil",
        templateUrl: "views/perfil/perfil.html",
        authenticate: true,
        data: { pageTitle: 'Mi perfil' }
    })

}
/**
* Angular config    - Método para registrar el trabajo que debe realizarse en la carga del módulo.
* Angular run       - Método para registrar el trabajo que se debe realizar cuando el inyector termina de cargar todos los módulos.    
*/
angular.module('trabajoTerminal')
.config(config)
.run(function($rootScope, $state,$log,$cookies) {
    $rootScope.$state = $state;


    $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){

        //Si el usuario no está autenticado
        if (toState.authenticate && $cookies.get("auth")!="true"){
            $state.transitionTo("login");
            event.preventDefault(); 
        }
    });
});
