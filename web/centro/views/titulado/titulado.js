"use strict";

app.controller("TituladoSearchController", ['$scope', '$http', 'genericControllerCrudList', 'controllerParams', 'ix3Configuration', 'dialog', function ($scope, $http, genericControllerCrudList, controllerParams, ix3Configuration, dialog) {
        $scope.businessMessages = [];
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.distinct = true;
        $scope.filters.tipoUsuario = "TITULADO";
        $scope.filters['titulado.formacionesAcademicas.centro.idCentro'] = $scope.user.centro.idCentro;
        $scope.search();
        $scope.apiUrl = ix3Configuration.server.api;
        $scope.mostrarCodigosMunicipio = function () {
            dialog.create('mostrarCodigosMunicipio');
        };
        $scope.failImport = function () {
           alert("Ocurrio un problema al importar los titulados");
        };
        $scope.updateList = function () {
            $scope.search();
        };
    }]);



app.controller("TituladoViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'ix3Configuration', 'ageCalculator', function ($scope, genericControllerCrudDetail, controllerParams, ix3Configuration, ageCalculator) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.ix3Configuration = ix3Configuration;
        $scope.ageCalculator = ageCalculator;
    }]);

