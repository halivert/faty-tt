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
        templateUrl: "views/pacientes/hisotorial_clinico.html",
        authenticate: true,
        data: { pageTitle: 'Historial Clinico' }
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
