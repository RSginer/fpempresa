app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('cols2.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController',
            resolve: {
                metadata: ['metadataEntities', function (metadataEntities) {
                        return metadataEntities.load("Titulado", "titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario,direccion.municipio.provincia");
                    }]
            }
        });
    }]);