/**
 * Trabajo terminal - CERES
 *
 * Custom scripts
 */

 $(document).ready(function () {


    // Full height of sidebar
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebar-panel").css("min-height", heightWithoutNavbar + "px");

        var navbarHeight = $('nav.navbar-default').height();
        var wrapperHeigh = $('#page-wrapper').height();

        if(navbarHeight > wrapperHeigh){
            $('#page-wrapper').css("min-height", navbarHeight + "px");
        }

        if(navbarHeight < wrapperHeigh){
            $('#page-wrapper').css("min-height", $(window).height()  + "px");
        }

        if ($('body').hasClass('fixed-nav')) {
            if (navbarHeight > wrapperHeigh) {
                $('#page-wrapper').css("min-height", navbarHeight + "px");
            } else {
                $('#page-wrapper').css("min-height", $(window).height() - 60 + "px");
            }
        }

    }

    $(window).bind("load resize scroll", function() {
        if(!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    // Move right sidebar top after scroll
    $(window).scroll(function(){
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav') ) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    setTimeout(function(){
        fix_height();
    });

//Script para mostrar el calendario en el form de nuevo usuario
    $(document).ready(function(){
         $('#data_2 .input-group.date').datepicker({
            startView: 1,
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            format: "dd/mm/yyyy",
            language: 'es'
        });   
    });    

});

// Minimalize menu when screen is less than 768px
$(window).bind("load resize", function () {
    if ($(document).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }
});

