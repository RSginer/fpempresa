"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"TituloIdioma",
            htmlBasePath:"views/curriculum"
        });
    }]);