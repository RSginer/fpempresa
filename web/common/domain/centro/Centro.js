"use strict";

angular.module("common").run(['richDomain','metadataEntities', function (richDomain,metadataEntities) {

        function getEstadoCentroDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath+".estadoCentro").getValueDescription(this.estadoCentro);
        };
       
        

        richDomain.addEntityTransformer("Centro",function (className, object) {
            object['getEstadoCentroDescription']=getEstadoCentroDescription;
        });

        
    }]);

