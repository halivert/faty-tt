/**
 * Módulo principal del proyecto
 * en éste se inyectan dependencias 'externas' del proyecto
 * serán usadas por los controllers o services.
 */
 (function () {
 	angular.module('trabajoTerminal', [
        'ui.router',                    // Routing
        'ui.bootstrap',                 // Bootstrap
        'toastr',
        'ngCookies',
        'blockUI',
        'chart.js',
        'ngTable',
        'slickCarousel'
        ])
 })();