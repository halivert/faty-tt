function config($stateProvider,$urlRouterProvider){$urlRouterProvider.otherwise("/login"),$stateProvider.state("login",{url:"/login",templateUrl:"views/login/login.html",authenticate:!1,"class":"gray-bg",data:{pageTitle:"Inicio sesion",bodyClass:"gray-bg"}}).state("index",{"abstract":!0,url:"/index",templateUrl:"views/common/content.html",authenticate:!0}).state("index.main",{url:"/main",templateUrl:"views/main.html",authenticate:!0,data:{pageTitle:"Inicial"}}).state("index.imc",{url:"/imc",templateUrl:"views/imc/imc.html",authenticate:!0,data:{pageTitle:"Calcular IMC"}}).state("index.glucosa",{url:"/glucosa",templateUrl:"views/glucosa/glucosa.html",authenticate:!0,data:{pageTitle:"Registrar glucosa"}}).state("index.pacientes",{url:"/pacientes",templateUrl:"views/pacientes/pacientes.html",authenticate:!0,data:{pageTitle:"Pacientes"}}).state("index.historialClinico",{url:"/historialClinico",templateUrl:"views/pacientes/historial_clinico.html",authenticate:!0,data:{pageTitle:"Historial Clinico"}}).state("index.informacionGeneral",{url:"/informacionGeneral",templateUrl:"views/pacientes/informacion_general.html",authenticate:!0,data:{pageTitle:"Información Paciente"}}).state("index.dieta",{url:"/dieta",templateUrl:"views/dieta/dieta.html",authenticate:!0,data:{pageTitle:"Dieta"}}).state("index.dietaRigurosa",{url:"/dietaRigurosa",templateUrl:"views/dieta/dieta_rigurosa.html",authenticate:!0,data:{pageTitle:"Generar Dieta"}})}function pageTitle($rootScope,$timeout){return{link:function(scope,element){var listener=function(event,toState,toParams,fromState,fromParams){var title="Trabajo Terminal";toState.data&&toState.data.pageTitle&&(title="Trabajo Terminal | "+toState.data.pageTitle),$timeout(function(){element.text(title)})};$rootScope.$on("$stateChangeStart",listener)}}}function sideNavigation($timeout){return{restrict:"A",link:function(scope,element){$timeout(function(){element.metisMenu()});var menuElement=$('#side-menu a:not([href$="\\#"])');if(menuElement.click(function(){$(window).width()<769&&$("body").toggleClass("mini-navbar")}),$("body").hasClass("fixed-sidebar")){var sidebar=element.parent();sidebar.slimScroll({height:"100%",railOpacity:.9})}}}}function iboxTools($timeout){return{restrict:"A",scope:!0,templateUrl:"views/common/ibox_tools.html",controller:function($scope,$element){$scope.showhide=function(){var ibox=$element.closest("div.ibox"),icon=$element.find("i:first"),content=ibox.children(".ibox-content");content.slideToggle(200),icon.toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"),ibox.toggleClass("").toggleClass("border-bottom"),$timeout(function(){ibox.resize(),ibox.find("[id^=map-]").resize()},50)},$scope.closebox=function(){var ibox=$element.closest("div.ibox");ibox.remove()}}}}function minimalizaSidebar($timeout){return{restrict:"A",template:'<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="" ng-click="minimalize()"><i class="fa fa-bars"></i></a>',controller:function($scope,$element){$scope.minimalize=function(){$("body").toggleClass("mini-navbar"),!$("body").hasClass("mini-navbar")||$("body").hasClass("body-small")?($("#side-menu").hide(),setTimeout(function(){$("#side-menu").fadeIn(400)},200)):$("body").hasClass("fixed-sidebar")?($("#side-menu").hide(),setTimeout(function(){$("#side-menu").fadeIn(400)},100)):$("#side-menu").removeAttr("style")}}}}function iboxToolsFullScreen($timeout){return{restrict:"A",scope:!0,templateUrl:"views/common/ibox_tools_full_screen.html",controller:function($scope,$element){$scope.showhide=function(){var ibox=$element.closest("div.ibox"),icon=$element.find("i:first"),content=ibox.children(".ibox-content");content.slideToggle(200),icon.toggleClass("fa-chevron-up").toggleClass("fa-chevron-down"),ibox.toggleClass("").toggleClass("border-bottom"),$timeout(function(){ibox.resize(),ibox.find("[id^=map-]").resize()},50)},$scope.closebox=function(){var ibox=$element.closest("div.ibox");ibox.remove()},$scope.fullscreen=function(){var ibox=$element.closest("div.ibox"),button=$element.find("i.fa-expand");$("body").toggleClass("fullscreen-ibox-mode"),button.toggleClass("fa-expand").toggleClass("fa-compress"),ibox.toggleClass("fullscreen"),setTimeout(function(){$(window).trigger("resize")},100)}}}}$(document).ready(function(){function fix_height(){var heightWithoutNavbar=$("body > #wrapper").height()-61;$(".sidebar-panel").css("min-height",heightWithoutNavbar+"px");var navbarHeight=$("nav.navbar-default").height(),wrapperHeigh=$("#page-wrapper").height();navbarHeight>wrapperHeigh&&$("#page-wrapper").css("min-height",navbarHeight+"px"),wrapperHeigh>navbarHeight&&$("#page-wrapper").css("min-height",$(window).height()+"px"),$("body").hasClass("fixed-nav")&&(navbarHeight>wrapperHeigh?$("#page-wrapper").css("min-height",navbarHeight+"px"):$("#page-wrapper").css("min-height",$(window).height()-60+"px"))}$(window).bind("load resize scroll",function(){$("body").hasClass("body-small")||fix_height()}),$(window).scroll(function(){$(window).scrollTop()>0&&!$("body").hasClass("fixed-nav")?$("#right-sidebar").addClass("sidebar-top"):$("#right-sidebar").removeClass("sidebar-top")}),setTimeout(function(){fix_height()})}),$(window).bind("load resize",function(){$(document).width()<769?$("body").addClass("body-small"):$("body").removeClass("body-small")}),function(){angular.module("trabajoTerminal",["ui.router","ui.bootstrap","toastr","ngCookies","blockUI","chart.js"])}(),angular.module("trabajoTerminal").config(config).run(function($rootScope,$state,$log,$cookies){$rootScope.$state=$state,$rootScope.$on("$stateChangeStart",function(event,toState,toParams,fromState,fromParams){toState.authenticate&&"true"!=$cookies.get("auth")&&($state.transitionTo("login"),event.preventDefault())})}),angular.module("trabajoTerminal").directive("pageTitle",pageTitle).directive("sideNavigation",sideNavigation).directive("iboxTools",iboxTools).directive("minimalizaSidebar",minimalizaSidebar).directive("iboxToolsFullScreen",iboxToolsFullScreen),angular.module("trabajoTerminal").controller("MainCtrl",function($scope,loginService,$cookies,toastr){$scope.nombrePagina="Página principal",$scope.userName="Nombre del usuario",$scope.helloText="Pagina de inicio",$scope.descriptionText="Descripcion del trabajo terminal",$scope.informacionIndividuo={},$scope.prueba=function(){$scope.descriptionText="Se cambia el texto"};$scope.date=new Date,$scope.fecha=new Date,$scope.obtenerRol=function(){return console.log("obtenerRol()"),"1"==$cookies.get("rol")?"M":"P"}}),angular.module("trabajoTerminal").controller("controladorEjemplo",function($scope){$scope.cadena="cadena"}),angular.module("trabajoTerminal").service("loginService",function($log,$http,$q){return{iniciaSesionService:function(email,password){var data={email:email,keyword:password},config={headers:{"Content-Type":"application/json"}},url="http://35.202.245.109/tt-escom-diabetes/session/login";return $http.post(url,data,config).then(function(response){return"OK"===response.data.respuesta?response.data:(console.log("response ERROR : "+JSON.stringify(response.data)),$q.reject(response))},function(response){return console.log("response ERROR : "+JSON.stringify(response.data)),$q.reject(response)})},guardarUsuario:function(nombre,apellidoPaterno,apellidoMaterno,email,keyword,fechaNacimiento,sexo,idRol,cedulaProfesional,codigoMedico){var data={nombre:nombre,apellidoPaterno:apellidoPaterno,apellidoMaterno:apellidoMaterno,email:email,keyword:keyword,fechaNacimiento:fechaNacimiento,sexo:sexo,idRol:idRol,cedulaProfesional:cedulaProfesional,codigoMedico:codigoMedico},config={headers:{"Content-Type":"application/json"}},url="http://35.202.245.109/tt-escom-diabetes/session/usuarios";return console.log("data : "+JSON.stringify(data)),$http.post(url,data,config).then(function(response){return"OK"===response.data.respuesta?(console.log("response OK : "+JSON.stringify(response.data.mensaje)),response.data.mensaje):(console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response))},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperarInformacionUsuario:function(idUsuario){$log.debug("idUsuario : "+idUsuario);var data=$.param({idUsuario:idUsuario}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/usuarios/"+idUsuario;return $http.get(url,data,config).then(function(response){return console.log("response OK : "+JSON.stringify(response.data)),response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data)),$q.reject(response)})}}}),angular.module("trabajoTerminal").controller("loginCtrl",function($scope,$location,$log,$state,loginService,toastr,$cookies,blockUI){$scope.titulo="Bienvenido a CERES",$scope.usuarioObject={email:"",password:""},$scope.datosU={nombre:"",apellidoPaterno:"",apellidoMaterno:"",idSexo:"",email:"",keyword:"",rol:"",cedula:"",fechaNac:"",codigoM:""},$scope.limpiaInfo=function(){$cookies.put("auth","false"),$cookies.remove("rol"),$cookies.remove("nombre",""),$cookies.remove("idUsuario",""),$cookies.remove("idCurrentPaciente","")},$scope.iniciarSesion=function(){loginService.iniciaSesionService($scope.usuarioObject.email,$scope.usuarioObject.password).then(function(d){blockUI.start(),$cookies.put("auth","true"),$cookies.put("idUsuario",d.idUsuario),$cookies.put("idCurrentPaciente",d.idUsuario),toastr.success(d.mensaje,"Ok"),loginService.recuperarInformacionUsuario(d.idUsuario).then(function(informacionUsuario){var nombreCompleto=informacionUsuario.nombre+" "+informacionUsuario.apellidoPaterno+" "+informacionUsuario.apellidoMaterno;$log.debug("nombreCompleto : "+nombreCompleto),$log.debug("informacionUsuario.idRol : "+informacionUsuario.idRol),$cookies.put("rol",informacionUsuario.idRol),$cookies.put("nombre",nombreCompleto),$state.transitionTo("index.main")}),blockUI.stop()},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))})},$scope.validaForm=function(){return $scope.formRegistro.$valid?$scope.validaPasword():!1},$scope.validaPasword=function(){return $scope.datosU.keyword===$scope.datosU.confirmpass?!0:!1},$scope.registrarUsuario=function(){loginService.guardarUsuario($scope.datosU.nombre,$scope.datosU.apellidoPaterno,$scope.datosU.apellidoMaterno,$scope.datosU.email,$scope.datosU.keyword,$scope.datosU.fechaNac,$scope.datosU.idSexo,$scope.datosU.rol,$scope.datosU.cedula,$scope.datosU.codigoM).then(function(d){$log.debug("d"+JSON.stringify(d)),toastr.success(d,"Ok"),angular.element("#modal-form").modal("hide"),$scope.datosU={}},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))})}}),angular.module("trabajoTerminal").controller("pacientesCtrl",function($scope,$log,pacientesService,loginService,toastr,blockUI,$cookies,$filter,$state,$cookies,glucosaService){$scope.pacientes=[],$scope.listhistorialclinico=[],$scope.usuario={id:""};var idCurrentPaciente="";$scope.nombreCompletoPaciente,$scope.currentPaciente={},$scope.numero=0,$scope.desactivarBoton=!0,$scope.historialDetalle={},$scope.imc,$scope.historial={peso:"",altura:""},$scope.verPacientes=function(){$log.debug("ver pacientes");var idUsuario=$cookies.get("idUsuario");pacientesService.recuperarListaPacientes(idUsuario).then(function(d){$scope.pacientes=d,angular.element(document).ready(function(){dTable=$("#dataTable-pacientes"),dTable.DataTable({language:{url:"https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}})})},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):toastr.error(d.data.mensaje,"Error")})},$scope.numeroPacientes=function(){$log.debug("numero pacientes");var idUsuario=$cookies.get("idUsuario");$log.debug("idUsuario : "+idUsuario),pacientesService.recuperarNumeroPacientes(idUsuario).then(function(d){$log.debug("d.length : "+d.length),$scope.numero=d.length},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):toastr.error(d.data.mensaje,"Error")})},$scope.recuperaPaciente=function(idCurrentPaciente){console.log("idCurrentPaciente : "+idCurrentPaciente),$scope.currentPaciente=$filter("filter")($scope.pacientes,{id_USUARIO:idCurrentPaciente}),console.log("$scope.currentPaciente : "+JSON.stringify($scope.currentPaciente))},$scope.recuperaInfoPaciente=function(idCurrentPaciente){loginService.recuperarInformacionUsuario(idCurrentPaciente).then(function(informacionUsuario){$scope.currentPaciente.nombreCompletoPaciente=informacionUsuario.nombre+" "+informacionUsuario.apellidoPaterno+" "+informacionUsuario.apellidoMaterno,$scope.currentPaciente.fechaNac=informacionUsuario.fechaNacimiento,$scope.currentPaciente.edad=$scope.calculateAge(informacionUsuario.fechaNacimiento),console.log("$scope.currentPaciente.nombreCompletoPaciente "+$scope.currentPaciente.nombreCompletoPaciente)})},$scope.verHistorial=function(){idCurrentPaciente=$cookies.get("idCurrentPaciente"),blockUI.start(),$scope.recuperaInfoPaciente(idCurrentPaciente),pacientesService.recuperarListaHistorialClinico(idCurrentPaciente).then(function(d){0==d.length&&toastr.warning("El paciente aún no tiene historial clínico.","Atención"),$scope.listhistorialclinico=d,console.log("JSON.stringify(d)"+JSON.stringify(d)),angular.element(document).ready(function(){dTable=$("#dataTable-historial-clinico"),dTable.DataTable({language:{url:"https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}})})},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))}),blockUI.stop()},$scope.verUltimoHistorial=function(idCurrentPaciente){$cookies.put("idCurrentPaciente",idCurrentPaciente),$state.transitionTo("index.informacionGeneral")},$scope.mostrarInformacionGeneral=function(){blockUI.start(),pacientesService.recuperaUltimoHistorial($cookies.get("idCurrentPaciente")).then(function(d){$scope.historialDetalle=d},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))}),blockUI.stop()},$scope.setCurrentPaciente=function(){$cookies.put("idCurrentPaciente",$scope.usuario.id),$state.transitionTo("index.historialClinico")},$scope.guardarHistorialClinico=function(){console.log("guardarHistorialClinico()"),pacientesService.guardarInfoHistorialClinico($scope.usuario.id,$scope.historial.peso,$scope.historial.talla,$scope.historial.altura,$scope.historial.imc,$scope.historial.lipidos,$scope.historial.carbohidratos,$scope.historial.proteinas,$scope.historial.azucar).then(function(d){angular.element("#modal-paciente").modal("hide"),toastr.success(d,"Ok")},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))})},$scope.recuperaHistorial=function(idHistorial){console.log("recuperaHistorial()"),console.log("idHistorial : "+idHistorial),blockUI.start(),idCurrentPaciente=$cookies.get("idCurrentPaciente"),pacientesService.recuperarDetallHistorialClinico(idCurrentPaciente,idHistorial).then(function(d){console.log(JSON.stringify(d)),$scope.historialDetalle=d,$scope.activarBoton()},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))}),blockUI.stop()},$scope.recuperarRegistrosGlucosa=function(){console.log("recuperarRegistrosGlucosa()"),blockUI.start();var idPaciente=$cookies.get("idCurrentPaciente");glucosaService.recuperarRegistrosGlucosaService(idPaciente).then(function(d){$scope.series=["Nivel de glucosa"],$scope.labels=[],$scope.data=[[]],d.forEach(function(data){$scope.labels.push(data.fechaRegistro),$scope.data[0].push(data.azucar)}),$scope.onClick=function(points,evt){console.log(points,evt)},$scope.datasetOverride=[{yAxisID:"y-axis-1"}],$scope.options={scales:{yAxes:[{id:"y-axis-1",type:"linear",display:!0,position:"left"}]}}},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))}),blockUI.stop()},$scope.calculateAge=function(birthday){var birthDate=new Date(birthday),ageDifMs=Date.now()-birthDate.getTime(),ageDate=new Date(ageDifMs);return Math.abs(ageDate.getUTCFullYear()-1970)},$scope.activarBoton=function(){$scope.desactivarBoton=!1},$scope.esValido=function(){return $scope.desactivarBoton},$scope.validaForm=function(){return $scope.formHistorial.$valid?(console.log($scope.historial.imc=$scope.historial.peso/Math.pow($scope.historial.altura,2)),!0):($scope.historial.peso&&$scope.historial.altura?$scope.historial.imc=$scope.historial.peso/Math.pow($scope.historial.altura,2):$scope.historial.imc="IMC",!1)}}),angular.module("trabajoTerminal").service("pacientesService",function($log,$http,$q,$filter){return{recuperarListaPacientes:function(idMedico){var data=$.param({idMedico:idMedico}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/medico/"+idMedico+"/pacientes/";return $http.get(url,data,config).then(function(response){return"ERROR"===response.data.respuesta?$q.reject(response):response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperarNumeroPacientes:function(idMedico){var data=$.param({idMedico:idMedico}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/medico/"+idMedico+"/pacientes/";return $http.get(url,data,config).then(function(response){return"ERROR"===response.data.respuesta?$q.reject(response):response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperarListaHistorialClinico:function(idPaciente){var data=$.param({idPaciente:idPaciente}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/historialclinico/";return $http.get(url,data,config).then(function(response){return"ERROR"===response.data.respuesta?(console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)):response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},guardarInfoHistorialClinico:function(idPaciente,peso,talla,estatura,imc,lipidos,carbohidratos,proteinas,azucar){var fechaRegistro=$filter("date")(new Date,"yyyy-MM-dd HH:mm:ss"),data={idPaciente:idPaciente,fecha:fechaRegistro,peso:peso,talla:talla,estatura:estatura,imc:imc,lipidos:lipidos,carbohidratos:carbohidratos,proteinas:proteinas,azucar:azucar},config={headers:{"Content-Type":"application/json"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/historialclinico";return console.log("data : "+JSON.stringify(data)),$http.post(url,data,config).then(function(response){return"OK"===response.data.respuesta?(console.log("response OK : "+JSON.stringify(response.data.mensaje)),response.data.mensaje):(console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response))},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperarDetallHistorialClinico:function(idPaciente,idHistorialClinico){var data=$.param({idPaciente:idPaciente,idHistorialClinico:idHistorialClinico}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/historialclinico/"+idHistorialClinico;return $http.get(url,data,config).then(function(response){return response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperaUltimoHistorial:function(idPaciente){var config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/ultimoHistorialclinico";return $http.get(url,config).then(function(response){return response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})}}}),angular.module("trabajoTerminal").controller("medicoCtrl",function($scope,$cookies,$log,medicoService){$scope.codigo="sdfsd",$scope.algo="algo",$scope.generarCodigo=function(){$log.debug("generar codigo");var idMedico=$cookies.get("idUsuario");medicoService.generaCodigoMedico(idMedico).then(function(d){$log.debug("d.mensaje : "+d.mensaje),$cookies.put("token",d.mensaje)},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):toastr.error(d.data.mensaje,"Error")})},$scope.verToken=function(){return $cookies.get("token")}}),angular.module("trabajoTerminal").service("medicoService",function($log,$http,$q){return{generaCodigoMedico:function(idMedico){var data=$.param({idMedico:idMedico}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/medico/"+idMedico+"/token/";return $http.get(url,data,config).then(function(response){return"ERROR"===response.data.respuesta?$q.reject(response):response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})}}}),angular.module("trabajoTerminal").controller("glucosaCtrl",function($scope,$log,toastr,blockUI,$cookies,$state,$cookies,$filter,glucosaService){$scope.registroGlucosa={azucar:"",fechaReg:""},$scope.guardarRegistroGlucosa=function(){console.log("guardarRegistroGlucosa()");var idPaciente=$cookies.get("idUsuario");glucosaService.guardarRegistroGlucosa(idPaciente,$scope.registroGlucosa.azucar,$scope.registroGlucosa.fechaReg).then(function(d){toastr.success(d,"Ok")},function(d){null==d.data?toastr.warning("Servicio no disponible","Advertencia"):($log.debug("JSON.stringify(d.data.mensaje)"+JSON.stringify(d.data.mensaje)),toastr.error(d.data.mensaje,"Error"))})}}),angular.module("trabajoTerminal").service("glucosaService",function($log,$http,$q,$filter){return{guardarRegistroGlucosa:function(idPaciente,azucar,fechaRegistro){console.log("fechaRegistro : "+fechaRegistro),console.log("date : "+fechaRegistro);var data={azucar:azucar,fechaRegistro:fechaRegistro},config={headers:{"Content-Type":"application/json"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idPaciente+"/registroglucosa";return console.log("data : "+JSON.stringify(data)),$http.post(url,data,config).then(function(response){return"OK"===response.data.respuesta?(console.log("response OK : "+JSON.stringify(response.data.mensaje)),response.data.mensaje):(console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response))},function(response){return console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)})},recuperarRegistrosGlucosaService:function(idUsuario){var data=$.param({idUsuario:idUsuario}),config={headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8;"}},url="http://35.202.245.109/tt-escom-diabetes/ceres/pacientes/"+idUsuario+"/registroglucosa";return $http.get(url,data,config).then(function(response){return"ERROR"===response.data.respuesta?(console.log("response ERROR : "+JSON.stringify(response.data.mensaje)),$q.reject(response)):response.data},function(response){return console.log("response ERROR : "+JSON.stringify(response.data)),$q.reject(response)})}}}),angular.module("trabajoTerminal").controller("indiceMasaCorporalCtrl",function($scope,$log){$scope.nombrePagina="Calcular el IMC",$scope.respuestaObject={peso:"",altura:""},$scope.imc="",$scope.validarPesoAltura=function(){return $log.debug("inicia validarPesoAltura()"),""==$scope.respuestaObject.peso.trim()||0==$scope.respuestaObject.peso.trim()||""==$scope.respuestaObject.altura.trim()||0==$scope.respuestaObject.altura.trim()?!0:!1},$scope.calcularIMC=function(){$log.debug("inicia calcularIMC()"),$scope.imc=$scope.respuestaObject.peso/Math.pow($scope.respuestaObject.altura,2)}}),angular.module("trabajoTerminal").controller("navigationCtrl",function($scope,$cookies,$log){$scope.obtenerNombre=function(){return $cookies.get("nombre")}}),angular.module("trabajoTerminal").controller("dietaCtrl",function($scope,$log){$scope.tiempos=["Desayuno","Colación 1","Comida","Colación 2","Cena"],$scope.dietaRigurosaSlider=function(){$log.debug("dietaRigurosaSlider"),angular.element(document).ready(function(){tiempo=$(".tiempo"),tiempo.slick({dots:!1,infinite:!1}),$(".slick-arrow").click(function(){console.log($scope.tiempos[tiempo.slick("slickCurrentSlide")])}),angular.element(document).ready(function(){dTable=$("#dataTable-alimentos"),dTable.DataTable({language:{url:"https://cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"},paging:!1,info:!1})})})},$scope.iniciaGraficaBarras=function(){$log.debug("iniciaGraficaBarras"),$scope.labels=["Proteinas","CH","Lipidos","Azucar"],$scope.series=["Descripción Datos"],$scope.data=[[65,59,80,70]],$scope.colours=["#FF4C4C"]}});