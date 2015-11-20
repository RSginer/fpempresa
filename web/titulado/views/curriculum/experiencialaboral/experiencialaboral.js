"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"ExperienciaLaboral",
            htmlBasePath:"views/curriculum"
        });
    }]);

app.controller("ExperienciaLaboralSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);

        $scope.orderby=[
            {fieldName:"fechaInicio",orderDirection:"ASC"}
        ];

        $scope.search();
    }]);

app.controller("ExperienciaLaboralNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("ExperienciaLaboralViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("ExperienciaLaboralDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);