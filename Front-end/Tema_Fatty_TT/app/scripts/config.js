/**
 * INSPINIA - Responsive Admin Theme
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written stat for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");

    $stateProvider

        .state('login', {
            url: "/login",
            templateUrl: "views/login/login.html",
            authenticate: false,
            data: { pageTitle: 'Inicio sesion' }
        })
        .state('index', {
            abstract: true,
            url: "/index",
            templateUrl: "views/common/content.html",
            authenticate: false
        })
        .state('index.main', {
            url: "/main",
            templateUrl: "views/main.html",
            authenticate: false,
            data: { pageTitle: 'Inicial' }
        })
        .state('index.imc', {
            url: "/imc",
            templateUrl: "views/imc/imc.html",
            authenticate: false,
            data: { pageTitle: 'Calcular IMC' }
        })
        .state('index.glucosa', {
            url: "/glucosa",
            templateUrl: "views/glucosa/glucosa.html",
            authenticate: false,
            data: { pageTitle: 'Registrar glucosa' }
        })
        .state('index.pacientes', {
            url: "/pacientes",
            templateUrl: "views/pacientes/pacientes.html",
            authenticate: false,
            data: { pageTitle: 'Pacientes' }
        })
        
}
angular
    .module('trabajoTerminal')
    .config(config)
    .run(function($rootScope, $state,$log,$cookies) {
        $rootScope.$state = $state;

        $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
      
        if (toState.authenticate && $cookies.get("auth")!="true"){
            // User isn’t authenticated
            $state.transitionTo("login");
            event.preventDefault(); 
        }
    });
    });
